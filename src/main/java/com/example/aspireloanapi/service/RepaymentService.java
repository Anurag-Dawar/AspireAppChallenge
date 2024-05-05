package com.example.aspireloanapi.service;

import com.example.aspireloanapi.model.Loan;
import com.example.aspireloanapi.model.Repayment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface RepaymentService {

    List<Repayment> getRepaymentsByLoanId(String loanId);

    void createRepaymentsForLoan(Loan loan);

    void makeRepayment(BigDecimal amount, String loanId);

    Repayment getNextPendingRepayment(String loanId);



}
