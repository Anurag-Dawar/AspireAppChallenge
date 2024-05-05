package com.example.aspireloanapi.service;

import com.example.aspireloanapi.dto.LoanRequestDto;
import com.example.aspireloanapi.dto.LoanResponseDto;

;import java.math.BigDecimal;

public interface LoanService {

    LoanResponseDto createLoan(LoanRequestDto loanRequestDto);

    LoanResponseDto getLoanDetailsByLoanId(String loanId);

    LoanResponseDto approveLoan(String loanId);

    LoanResponseDto addRepayment(String loanId, BigDecimal amount);

    LoanResponseDto getLoanDetailsByLoanIdAndUserId(String loanId);
}
