package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.TransactionRecord;
import com.vie.db.entity.UserWallet;
import com.vie.db.mapper.TransactionRecordMapper;
import com.vie.db.mapper.UserWalletMapper;
import com.vie.service.dto.TransactionQueryDTO;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.WalletService;
import com.vie.service.vo.TransactionRecordVO;
import com.vie.service.vo.WalletVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

/**
 * 用户钱包服务实现类
 */
@Slf4j
@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private UserWalletMapper userWalletMapper;

    @Autowired
    private TransactionRecordMapper transactionRecordMapper;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public WalletVO getWallet(Long userId) {
        UserWallet wallet = userWalletMapper.selectByUserId(userId);
        if (wallet == null) {
            throw new BusinessException(2001, "钱包不存在");
        }
        return WalletVO.builder()
                .id(wallet.getId())
                .userId(wallet.getUserId())
                .balance(wallet.getBalance())
                .frozenAmount(wallet.getFrozenAmount())
                .totalRecharge(wallet.getTotalRecharge())
                .totalConsume(wallet.getTotalConsume())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createWallet(Long userId) {
        // 检查是否已存在
        UserWallet existing = userWalletMapper.selectByUserId(userId);
        if (existing != null) {
            return;
        }
        
        UserWallet wallet = UserWallet.builder()
                .userId(userId)
                .balance(BigDecimal.ZERO)
                .frozenAmount(BigDecimal.ZERO)
                .totalRecharge(BigDecimal.ZERO)
                .totalConsume(BigDecimal.ZERO)
                .build();
        userWalletMapper.insert(wallet);
        log.info("为用户 {} 创建钱包", userId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recharge(Long userId, BigDecimal amount) {
        // 验证金额
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(2003, "充值金额无效");
        }

        // 检查钱包是否存在
        UserWallet wallet = userWalletMapper.selectByUserId(userId);
        if (wallet == null) {
            throw new BusinessException(2001, "钱包不存在");
        }

        // 增加余额
        userWalletMapper.increaseBalance(userId, amount);
        userWalletMapper.increaseTotalRecharge(userId, amount);

        // 获取更新后的余额
        wallet = userWalletMapper.selectByUserId(userId);

        // 创建交易记录
        createTransactionRecord(userId, TransactionRecord.ACCOUNT_TYPE_USER_WALLET,
                TransactionRecord.TYPE_RECHARGE, amount, wallet.getBalance(),
                null, "用户充值");

        log.info("用户 {} 充值 {} 元成功", userId, amount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deduct(Long userId, BigDecimal amount, String orderNo) {
        // 检查钱包是否存在
        UserWallet wallet = userWalletMapper.selectByUserId(userId);
        if (wallet == null) {
            throw new BusinessException(2001, "钱包不存在");
        }

        // 检查余额
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new BusinessException(2002, "余额不足");
        }

        // 扣减余额
        int rows = userWalletMapper.decreaseBalance(userId, amount);
        if (rows == 0) {
            throw new BusinessException(2002, "余额不足");
        }
        userWalletMapper.increaseTotalConsume(userId, amount);

        // 获取更新后的余额
        wallet = userWalletMapper.selectByUserId(userId);

        // 创建交易记录
        createTransactionRecord(userId, TransactionRecord.ACCOUNT_TYPE_USER_WALLET,
                TransactionRecord.TYPE_PAYMENT, amount.negate(), wallet.getBalance(),
                orderNo, "订单支付");

        log.info("用户 {} 支付 {} 元成功，订单号: {}", userId, amount, orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refund(Long userId, BigDecimal amount, String orderNo) {
        // 检查钱包是否存在
        UserWallet wallet = userWalletMapper.selectByUserId(userId);
        if (wallet == null) {
            throw new BusinessException(2001, "钱包不存在");
        }

        // 增加余额
        userWalletMapper.increaseBalance(userId, amount);

        // 获取更新后的余额
        wallet = userWalletMapper.selectByUserId(userId);

        // 创建交易记录
        createTransactionRecord(userId, TransactionRecord.ACCOUNT_TYPE_USER_WALLET,
                TransactionRecord.TYPE_REFUND, amount, wallet.getBalance(),
                orderNo, "订单退款");

        log.info("用户 {} 退款 {} 元成功，订单号: {}", userId, amount, orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adminRecharge(Long userId, BigDecimal amount, Long adminId) {
        // 验证金额
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(2003, "充值金额无效");
        }

        // 检查钱包是否存在
        UserWallet wallet = userWalletMapper.selectByUserId(userId);
        if (wallet == null) {
            throw new BusinessException(2001, "钱包不存在");
        }

        // 增加余额
        userWalletMapper.increaseBalance(userId, amount);
        userWalletMapper.increaseTotalRecharge(userId, amount);

        // 获取更新后的余额
        wallet = userWalletMapper.selectByUserId(userId);

        // 创建交易记录
        createTransactionRecord(userId, TransactionRecord.ACCOUNT_TYPE_USER_WALLET,
                TransactionRecord.TYPE_ADMIN_RECHARGE, amount, wallet.getBalance(),
                null, "管理员充值（操作人ID: " + adminId + "）");

        log.info("管理员 {} 为用户 {} 充值 {} 元成功", adminId, userId, amount);
    }


    @Override
    public IPage<TransactionRecordVO> getTransactionRecords(Long userId, TransactionQueryDTO queryDTO) {
        Page<Map<String, Object>> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        IPage<Map<String, Object>> recordPage = transactionRecordMapper.selectPageByUserId(
                page, userId, TransactionRecord.ACCOUNT_TYPE_USER_WALLET,
                queryDTO.getType(), queryDTO.getStartTime(), queryDTO.getEndTime());

        return recordPage.convert(this::buildTransactionRecordVO);
    }

    @Override
    public boolean checkBalance(Long userId, BigDecimal amount) {
        UserWallet wallet = userWalletMapper.selectByUserId(userId);
        if (wallet == null) {
            return false;
        }
        return wallet.getBalance().compareTo(amount) >= 0;
    }

    /**
     * 创建交易记录
     */
    private void createTransactionRecord(Long userId, Integer accountType, String type,
                                          BigDecimal amount, BigDecimal balanceAfter,
                                          String relatedOrderNo, String description) {
        TransactionRecord record = TransactionRecord.builder()
                .transactionNo(generateTransactionNo())
                .userId(userId)
                .accountType(accountType)
                .type(type)
                .amount(amount)
                .balanceAfter(balanceAfter)
                .relatedOrderNo(relatedOrderNo)
                .description(description)
                .build();
        transactionRecordMapper.insert(record);
    }

    /**
     * 生成交易流水号
     */
    private String generateTransactionNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = String.format("%06d", new Random().nextInt(1000000));
        return "TXN" + timestamp + random;
    }

    /**
     * 构建交易记录VO
     */
    private TransactionRecordVO buildTransactionRecordVO(Map<String, Object> map) {
        String type = (String) map.get("type");
        Object createTimeObj = map.get("create_time");
        String createTime = createTimeObj != null ? 
                (createTimeObj instanceof LocalDateTime ? 
                        ((LocalDateTime) createTimeObj).format(FORMATTER) : createTimeObj.toString()) 
                : null;

        return TransactionRecordVO.builder()
                .id(getLongValue(map, "id"))
                .transactionNo((String) map.get("transaction_no"))
                .type(type)
                .typeDesc(TransactionRecordVO.getTypeDesc(type))
                .amount(getBigDecimalValue(map, "amount"))
                .balanceAfter(getBigDecimalValue(map, "balance_after"))
                .relatedOrderNo((String) map.get("related_order_no"))
                .description((String) map.get("description"))
                .createTime(createTime)
                .build();
    }

    private Long getLongValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof Long) return (Long) value;
        if (value instanceof Integer) return ((Integer) value).longValue();
        return Long.parseLong(value.toString());
    }

    private BigDecimal getBigDecimalValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof BigDecimal) return (BigDecimal) value;
        return new BigDecimal(value.toString());
    }
}
