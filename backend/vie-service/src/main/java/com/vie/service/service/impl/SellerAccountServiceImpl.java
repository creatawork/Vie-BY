package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.SellerAccount;
import com.vie.db.entity.TransactionRecord;
import com.vie.db.mapper.SellerAccountMapper;
import com.vie.db.mapper.TransactionRecordMapper;
import com.vie.service.dto.TransactionQueryDTO;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.SellerAccountService;
import com.vie.service.vo.SellerAccountVO;
import com.vie.service.vo.TransactionRecordVO;
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
 * 商家账户服务实现类
 */
@Slf4j
@Service
public class SellerAccountServiceImpl implements SellerAccountService {

    @Autowired
    private SellerAccountMapper sellerAccountMapper;

    @Autowired
    private TransactionRecordMapper transactionRecordMapper;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public SellerAccountVO getAccount(Long sellerId) {
        SellerAccount account = sellerAccountMapper.selectBySellerId(sellerId);
        if (account == null) {
            throw new BusinessException(2005, "商家账户不存在");
        }
        return SellerAccountVO.builder()
                .id(account.getId())
                .sellerId(account.getSellerId())
                .balance(account.getBalance())
                .frozenAmount(account.getFrozenAmount())
                .totalIncome(account.getTotalIncome())
                .totalWithdraw(account.getTotalWithdraw())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createAccount(Long sellerId) {
        // 检查是否已存在
        SellerAccount existing = sellerAccountMapper.selectBySellerId(sellerId);
        if (existing != null) {
            return;
        }

        SellerAccount account = SellerAccount.builder()
                .sellerId(sellerId)
                .balance(BigDecimal.ZERO)
                .frozenAmount(BigDecimal.ZERO)
                .totalIncome(BigDecimal.ZERO)
                .totalWithdraw(BigDecimal.ZERO)
                .build();
        sellerAccountMapper.insert(account);
        log.info("为商家 {} 创建账户", sellerId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void freeze(Long sellerId, BigDecimal amount, String orderNo) {
        // 检查账户是否存在，不存在则自动创建
        SellerAccount account = sellerAccountMapper.selectBySellerId(sellerId);
        if (account == null) {
            createAccount(sellerId);
            account = sellerAccountMapper.selectBySellerId(sellerId);
        }

        // 增加冻结金额
        sellerAccountMapper.increaseFrozenAmount(sellerId, amount);

        // 获取更新后的账户
        account = sellerAccountMapper.selectBySellerId(sellerId);

        // 创建交易记录
        createTransactionRecord(sellerId, TransactionRecord.ACCOUNT_TYPE_SELLER_ACCOUNT,
                TransactionRecord.TYPE_FREEZE, amount, account.getBalance(),
                orderNo, "订单支付冻结");

        log.info("商家 {} 冻结 {} 元，订单号: {}", sellerId, amount, orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void settle(Long sellerId, BigDecimal amount, String orderNo) {
        // 检查账户是否存在
        SellerAccount account = sellerAccountMapper.selectBySellerId(sellerId);
        if (account == null) {
            throw new BusinessException(2005, "商家账户不存在");
        }

        // 结算：冻结金额转为可用余额
        int rows = sellerAccountMapper.settle(sellerId, amount);
        if (rows == 0) {
            throw new BusinessException(2002, "冻结金额不足");
        }

        // 增加累计收入
        sellerAccountMapper.increaseTotalIncome(sellerId, amount);

        // 获取更新后的账户
        account = sellerAccountMapper.selectBySellerId(sellerId);

        // 创建交易记录
        createTransactionRecord(sellerId, TransactionRecord.ACCOUNT_TYPE_SELLER_ACCOUNT,
                TransactionRecord.TYPE_SETTLEMENT, amount, account.getBalance(),
                orderNo, "订单结算");

        log.info("商家 {} 结算 {} 元，订单号: {}", sellerId, amount, orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unfreeze(Long sellerId, BigDecimal amount, String orderNo) {
        // 检查账户是否存在
        SellerAccount account = sellerAccountMapper.selectBySellerId(sellerId);
        if (account == null) {
            throw new BusinessException(2005, "商家账户不存在");
        }

        // 减少冻结金额
        int rows = sellerAccountMapper.decreaseFrozenAmount(sellerId, amount);
        if (rows == 0) {
            throw new BusinessException(2002, "冻结金额不足");
        }

        // 获取更新后的账户
        account = sellerAccountMapper.selectBySellerId(sellerId);

        // 创建交易记录
        createTransactionRecord(sellerId, TransactionRecord.ACCOUNT_TYPE_SELLER_ACCOUNT,
                TransactionRecord.TYPE_REFUND, amount.negate(), account.getBalance(),
                orderNo, "订单退款解冻");

        log.info("商家 {} 解冻 {} 元，订单号: {}", sellerId, amount, orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdraw(Long sellerId, BigDecimal amount) {
        // 验证金额
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(2004, "提现金额无效");
        }

        // 检查账户是否存在
        SellerAccount account = sellerAccountMapper.selectBySellerId(sellerId);
        if (account == null) {
            throw new BusinessException(2005, "商家账户不存在");
        }

        // 检查余额
        if (account.getBalance().compareTo(amount) < 0) {
            throw new BusinessException(2002, "余额不足");
        }

        // 扣减余额
        int rows = sellerAccountMapper.decreaseBalance(sellerId, amount);
        if (rows == 0) {
            throw new BusinessException(2002, "余额不足");
        }

        // 增加累计提现
        sellerAccountMapper.increaseTotalWithdraw(sellerId, amount);

        // 获取更新后的账户
        account = sellerAccountMapper.selectBySellerId(sellerId);

        // 创建交易记录
        createTransactionRecord(sellerId, TransactionRecord.ACCOUNT_TYPE_SELLER_ACCOUNT,
                TransactionRecord.TYPE_WITHDRAW, amount.negate(), account.getBalance(),
                null, "商家提现");

        log.info("商家 {} 提现 {} 元成功", sellerId, amount);
    }


    @Override
    public IPage<TransactionRecordVO> getTransactionRecords(Long sellerId, TransactionQueryDTO queryDTO) {
        Page<Map<String, Object>> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        IPage<Map<String, Object>> recordPage = transactionRecordMapper.selectPageByUserId(
                page, sellerId, TransactionRecord.ACCOUNT_TYPE_SELLER_ACCOUNT,
                queryDTO.getType(), queryDTO.getStartTime(), queryDTO.getEndTime());

        return recordPage.convert(this::buildTransactionRecordVO);
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
