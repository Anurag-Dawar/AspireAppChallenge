package com.example.aspireloanapi;

import com.example.aspireloanapi.dto.LoanRequestDto;
import com.example.aspireloanapi.dto.LoanResponseDto;
import com.example.aspireloanapi.enums.LoanStatus;
import com.example.aspireloanapi.impl.LoanServiceImpl;
import com.example.aspireloanapi.model.Loan;
import com.example.aspireloanapi.repository.LoanRepository;
import com.example.aspireloanapi.service.RepaymentService;
import com.example.aspireloanapi.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private RepaymentService repaymentService;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    @Test
    public void testCreateLoanWithInvalidAmount() {
        // Given
        LoanRequestDto loanRequestDto = new LoanRequestDto();
        loanRequestDto.setAmount(BigDecimal.ZERO); // Invalid amount

        // When & Then
        assertThrows(Exception.class, () -> loanService.createLoan(loanRequestDto));
    }

    @Test
    public void testGetLoanDetailsByLoanId() {
        // Given
        String loanId = "123";
        Loan loan = new Loan();

        // Mocking System.currentTimeMillis()
        long currentTimeMillis = System.currentTimeMillis();
        loan.setDisbursementDate(currentTimeMillis);

        when(loanRepository.findByLoanId(loanId)).thenReturn(loan);

        // When
        LoanResponseDto responseDto = loanService.getLoanDetailsByLoanId(loanId);

        // Then
        assertNotNull(responseDto);
    }

    @Test
    public void testApproveLoan() {
        // Given
        String loanId = "123";

        // Then
        assertThrows(Exception.class, () -> loanService.approveLoan(loanId));
    }

    @Test
    public void testAddRepayment() {
        // Given
        String loanId = "123";
        BigDecimal amount = new BigDecimal("500");
        Loan loan = new Loan();
        loan.setDisbursementDate(System.currentTimeMillis());
        when(authentication.getName()).thenReturn("username");
        when(loanRepository.findByLoanIdAndUserId(anyString(), anyString())).thenReturn(loan);

        // When
        LoanResponseDto responseDto = loanService.addRepayment(loanId, amount);

        // Then
        assertNotNull(responseDto);
    }

    @Test
    public void testAddRepaymentWithPaidLoan() {
        // Given
        String loanId = "123";
        BigDecimal amount = new BigDecimal("500");
        Loan loan = new Loan();
        loan.setLoanStatus(LoanStatus.PAID);

        when(authentication.getName()).thenReturn("username");
        when(loanRepository.findByLoanIdAndUserId(anyString(), anyString())).thenReturn(loan);

        // When & Then
        assertThrows(RuntimeException.class, () -> loanService.addRepayment(loanId, amount));
    }
}
