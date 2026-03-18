package com.vie.starter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.common.Result;
import com.vie.service.dto.RechargeDTO;
import com.vie.service.dto.TransactionQueryDTO;
import com.vie.service.service.WalletService;
import com.vie.service.vo.TransactionRecordVO;
import com.vie.service.vo.WalletVO;
import com.vie.starter.annotation.RequireLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户钱包控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    /**
     * 获取钱包信息
     */
    @RequireLogin
    @GetMapping
    public Result<WalletVO> getWallet(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        WalletVO wallet = walletService.getWallet(userId);
        return Result.success(wallet);
    }

    /**
     * 用户充值
     */
    @RequireLogin
    @PostMapping("/recharge")
    public Result<Void> recharge(@Valid @RequestBody RechargeDTO dto,
                                  HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        walletService.recharge(userId, dto.getAmount());
        return Result.success("充值成功", null);
    }

    /**
     * 查询交易记录
     */
    @RequireLogin
    @GetMapping("/transactions")
    public Result<IPage<TransactionRecordVO>> getTransactionRecords(
            TransactionQueryDTO queryDTO,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        IPage<TransactionRecordVO> records = walletService.getTransactionRecords(userId, queryDTO);
        return Result.success(records);
    }
}
