package com.vie.starter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.common.Result;
import com.vie.service.dto.TransactionQueryDTO;
import com.vie.service.dto.WithdrawDTO;
import com.vie.service.service.SellerAccountService;
import com.vie.service.vo.SellerAccountVO;
import com.vie.service.vo.TransactionRecordVO;
import com.vie.starter.annotation.RequireLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商家账户控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/seller/account")
public class SellerAccountController {

    @Autowired
    private SellerAccountService sellerAccountService;

    /**
     * 获取商家账户信息
     */
    @RequireLogin
    @GetMapping
    public Result<SellerAccountVO> getAccount(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        SellerAccountVO account = sellerAccountService.getAccount(userId);
        return Result.success(account);
    }

    /**
     * 商家提现
     */
    @RequireLogin
    @PostMapping("/withdraw")
    public Result<Void> withdraw(@Valid @RequestBody WithdrawDTO dto,
                                  HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        sellerAccountService.withdraw(userId, dto.getAmount());
        return Result.success("提现成功", null);
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
        IPage<TransactionRecordVO> records = sellerAccountService.getTransactionRecords(userId, queryDTO);
        return Result.success(records);
    }
}
