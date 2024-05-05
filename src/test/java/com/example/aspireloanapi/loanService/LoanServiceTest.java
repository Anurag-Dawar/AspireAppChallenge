package com.example.aspireloanapi.loanService;

import com.example.aspireloanapi.dto.LoanRequestDto;
import com.example.aspireloanapi.dto.LoanResponseDto;
import com.example.aspireloanapi.enums.LoanStatus;
import com.example.aspireloanapi.impl.LoanServiceImpl;
import com.example.aspireloanapi.model.Loan;
import com.example.aspireloanapi.repository.LoanRepository;
import com.example.aspireloanapi.service.RepaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private RepaymentService repaymentService;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
    public void testCreateLoan() {
        // Given
        LoanRequestDto loanRequestDto = new LoanRequestDto();
        loanRequestDto.setAmount(new BigDecimal("3000"));
        loanRequestDto.setLoanTermInWeeks(3);

        // Set up loan properties
        loanService.createLoan(loanRequestDto);

        // Set up expectedResponse properties
        LoanResponseDto expectedResponse = new LoanResponseDto();

        // Mocking loanRepository.save()

        // When
        LoanResponseDto responseDto = loanService.createLoan(loanRequestDto);

        // Then
        assertEquals(expectedResponse, responseDto);
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
        // Set up loan properties

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
        Loan loan = new Loan();
        // Set up loan properties

        when(loanRepository.findByLoanId(loanId)).thenReturn(loan);

        // When
        LoanResponseDto responseDto = loanService.approveLoan(loanId);

        // Then
        assertNotNull(responseDto);
    }

    @Test
    public void testApproveLoanByNonAdminUser() {
        // Given
        String loanId = "123";
        // Mocking getUserIdFromContext() to return non-admin user

        // When & Then
        assertThrows(RuntimeException.class, () -> loanService.approveLoan(loanId));
    }

    @Test
    public void testAddRepayment() {
        // Given
        String loanId = "123";
        BigDecimal amount = new BigDecimal("500");
        Loan loan = new Loan();
        // Set up loan properties

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

        when(loanRepository.findByLoanIdAndUserId(anyString(), anyString())).thenReturn(loan);

        // When & Then
        assertThrows(RuntimeException.class, () -> loanService.addRepayment(loanId, amount));
    }
}

