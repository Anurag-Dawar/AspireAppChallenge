package com.example.aspireloanapi.controller;

import com.example.aspireloanapi.dto.LoanRequestDto;
import com.example.aspireloanapi.dto.LoanResponseDto;
import com.example.aspireloanapi.enums.LoanStatus;
import com.example.aspireloanapi.model.Loan;
import com.example.aspireloanapi.repository.LoanRepository;
import com.example.aspireloanapi.service.LoanService;
import com.example.aspireloanapi.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @Autowired
    private LoanRepository loanRepository;


    public void testCreateLoan() {
        // Given
        LoanRequestDto loanRequestDto = new LoanRequestDto();
        loanRequestDto.setAmount(new BigDecimal("3000"));
        loanRequestDto.setLoanTermInWeeks(3);

        // Mock loanRepository.save() to return a mock Loan instance with expected values
        Loan expectedLoan = new Loan();
        expectedLoan.setId(1L);
        expectedLoan.setLoanId(UUID.randomUUID().toString());
        expectedLoan.setAmount(new BigDecimal("3000"));
        expectedLoan.setDisbursementDate(System.currentTimeMillis());
        expectedLoan.setLoanTermInWeeks(3);
        expectedLoan.setUserId("testUser123");
        expectedLoan.setLoanStatus(LoanStatus.PENDING);

        when(loanRepository.save(any(Loan.class))).thenReturn(expectedLoan);

        // Set up the expected response (expectedResponse) with expected values
        LoanResponseDto expectedResponse = new LoanResponseDto();
        expectedResponse.setLoanId(expectedLoan.getLoanId());
        expectedResponse.setAmount(expectedLoan.getAmount());
        expectedResponse.setDisbursementDate(DateUtils.convertTimestampToDate(System.currentTimeMillis()));
        expectedResponse.setLoanTermInWeeks(expectedLoan.getLoanTermInWeeks());
        expectedResponse.setLoanStatus(expectedLoan.getLoanStatus());

        // When
        LoanResponseDto responseDto = loanService.createLoan(loanRequestDto);

        // Then
        // Assert that the returned LoanResponseDto (responseDto) is not null
        assertNotNull(responseDto);

        // Add more assertions based on the expected behavior of createLoan()
        assertEquals(expectedResponse.getLoanId(), responseDto.getLoanId());
        assertEquals(expectedResponse.getAmount(), responseDto.getAmount());
        assertEquals(expectedResponse.getDisbursementDate(), responseDto.getDisbursementDate());
        assertEquals(expectedResponse.getLoanTermInWeeks(), responseDto.getLoanTermInWeeks());
        assertEquals(expectedResponse.getLoanStatus(), responseDto.getLoanStatus());
    }

    @Test
    public void testGetLoanApplicationById() {
        // Given
        String loanId = "123";
        LoanResponseDto expectedResponse = new LoanResponseDto();
        // Set up expectedResponse properties
        expectedResponse.setLoanId("123"); // Set up at least one property to avoid null

        when(loanService.getLoanDetailsByLoanIdAndUserId(loanId)).thenReturn(expectedResponse);

        // When
        ResponseEntity<LoanResponseDto> responseEntity = loanController.getLoanApplicationById(loanId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    public void testApproveLoan() {
        // Given
        String loanId = "123";
        LoanResponseDto expectedResponse = new LoanResponseDto();
        // Set up expectedResponse properties

        when(loanService.approveLoan(loanId)).thenReturn(expectedResponse);

        // When
        ResponseEntity<LoanResponseDto> responseEntity = loanController.approveLoan(loanId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    public void testApplyForLoanWithRepayment() {
        // Given
        String loanId = "123";
        BigDecimal amount = new BigDecimal("500");
        LoanResponseDto expectedResponse = new LoanResponseDto();
        // Set up expectedResponse properties

        when(loanService.addRepayment(loanId, amount)).thenReturn(expectedResponse);

        // When
        ResponseEntity<LoanResponseDto> responseEntity = loanController.applyForLoan(loanId, amount);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}
