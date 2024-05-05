package com.example.aspireloanapi.controller;


import com.example.aspireloanapi.dto.LoanRequestDto;
import com.example.aspireloanapi.dto.LoanResponseDto;
import com.example.aspireloanapi.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/create")
    public ResponseEntity<LoanResponseDto> applyForLoan(@RequestBody LoanRequestDto loanRequestDto) {
        LoanResponseDto newLoanApplication = loanService.createLoan(loanRequestDto);
        return new ResponseEntity<>(newLoanApplication, HttpStatus.CREATED);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponseDto> getLoanApplicationById(@PathVariable String loanId) {
        LoanResponseDto loanApplication = loanService.getLoanDetailsByLoanIdAndUserId(loanId);
        return new ResponseEntity<>(loanApplication, HttpStatus.OK);
    }


    @PutMapping("/approve/{loanId}")
    public ResponseEntity<LoanResponseDto> approveLoan(@PathVariable String loanId) {

        LoanResponseDto loanApplication = loanService.approveLoan(loanId);
        return new ResponseEntity<>(loanApplication, HttpStatus.OK);
    }

    @PutMapping("/add-repayment")
    public ResponseEntity<LoanResponseDto> applyForLoan(
            @RequestParam String loanId,
            @RequestParam BigDecimal amount) {

        LoanResponseDto newLoanApplication = loanService.addRepayment(loanId, amount);
        return new ResponseEntity<>(newLoanApplication, HttpStatus.CREATED);
    }

}
