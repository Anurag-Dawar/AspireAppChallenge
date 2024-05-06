package com.example.aspireloanapi;

import com.example.aspireloanapi.controller.LoanController;
import com.example.aspireloanapi.dto.LoanRequestDto;
import com.example.aspireloanapi.dto.LoanResponseDto;
import com.example.aspireloanapi.enums.LoanStatus;
import com.example.aspireloanapi.service.LoanService;
import com.example.aspireloanapi.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @Test
    public void testCreateLoan() {
        // Given
        LoanRequestDto loanRequestDto = new LoanRequestDto();
        loanRequestDto.setAmount(new BigDecimal("3000"));
        loanRequestDto.setLoanTermInWeeks(3);

        // Mock loanService.createLoan() to return a mock LoanResponseDto
        LoanResponseDto expectedResponse = new LoanResponseDto();
        expectedResponse.setLoanId(UUID.randomUUID().toString());
        expectedResponse.setAmount(new BigDecimal("3000"));
        expectedResponse.setDisbursementDate(DateUtils.convertTimestampToDate(System.currentTimeMillis()));
        expectedResponse.setLoanTermInWeeks(3);
        expectedResponse.setLoanStatus(LoanStatus.PENDING);

        when(loanService.createLoan(loanRequestDto)).thenReturn(expectedResponse);

        // When
        LoanResponseDto responseDto = loanController.applyForLoan(loanRequestDto).getBody();

        // Then
        assertNotNull(responseDto);
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
        expectedResponse.setLoanId("123");

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
