package com.example.aspireloanapi.impl;

import com.example.aspireloanapi.dto.LoanRequestDto;
import com.example.aspireloanapi.dto.LoanResponseDto;
import com.example.aspireloanapi.dto.RepaymentResponseDto;
import com.example.aspireloanapi.enums.LoanStatus;
import com.example.aspireloanapi.model.Loan;
import com.example.aspireloanapi.model.Repayment;
import com.example.aspireloanapi.repository.LoanRepository;
import com.example.aspireloanapi.service.LoanService;
import com.example.aspireloanapi.service.RepaymentService;
import com.example.aspireloanapi.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    public LoanRepository loanRepository;

    @Autowired
    private RepaymentService repaymentService;

    @Override
    public LoanResponseDto createLoan(LoanRequestDto loanRequestDto) {

        String userId = getUserIdFromContext();

        if(loanRequestDto.getAmount() == null || loanRequestDto.getAmount().intValue() <= 0){
            throw new RuntimeException("Invalid loan amount");
        }

        //create and save loan in db
        Loan loan = Loan.Builder
                .loan()
                .withLoanId(UUID.randomUUID().toString())
                .withAmount(loanRequestDto.getAmount())
                .withDisbursementDate(System.currentTimeMillis())
                .withLoanTermInWeeks(loanRequestDto.getLoanTermInWeeks())
                .withUserId(userId)
                .withLoanStatus(LoanStatus.PENDING)
                .build();
        loanRepository.save(loan);

        //create repayment schedule for this loan using repayment service
        repaymentService.createRepaymentsForLoan(loan);

        //return created loan details
        return getLoanDetailsByLoanId(loan.getLoanId());
    }

    @Override
    public LoanResponseDto getLoanDetailsByLoanId(String loanId) {

        //fetch loan details from db
        Loan loan = loanRepository.findByLoanId(loanId);

        //check if loan exists with this loanId
        if(loan == null || ObjectUtils.isEmpty(loan)){
            throw new RuntimeException("No loan found with this loanId " + loanId);
        }

        //return loan details
        return loanToLoanResponseDtoConverter(loan);
    }


    public LoanResponseDto approveLoan(String loanId) {
        String userId = getUserIdFromContext();
        if(!userId.contains("ADMIN")){
            throw new RuntimeException("User not allowed to approve loan");
        }

        //fetch loan details from db
        Loan loan = loanRepository.findByLoanId(loanId);

        //check if loan exists with this loanId
        if(loan == null || ObjectUtils.isEmpty(loan)){
            throw new RuntimeException("No loan found with this loanId " + loanId);
        }

        //check if loan is already approved or not
        if(LoanStatus.APPROVED == loan.getLoanStatus()){
            throw new RuntimeException("Loan is already in approved state " + loanId);
        }

        //set loan status to approved in db
        loan.setLoanStatus(LoanStatus.APPROVED);
        loanRepository.save(loan);

        //return updated loan details
        return getLoanDetailsByLoanId(loanId);
    }

    @Override
    public LoanResponseDto addRepayment(String loanId, BigDecimal amount) {

        String userId = getUserIdFromContext();

        //fetch loan details from db
        Loan loan = loanRepository.findByLoanIdAndUserId(loanId, userId);

        //check if loan exists with this loanId
        if(loan == null || ObjectUtils.isEmpty(loan)){
            throw new RuntimeException("No loan found with this loanId " + loanId);
        }

        //check if loan is already paid
        if (LoanStatus.PAID == loan.getLoanStatus()){
           throw new RuntimeException("No pending repayment for this loanId " + loanId);
        }

        //call repayment service to update repayment details
        repaymentService.makeRepayment(amount,loanId);

        //if all repayments are paid, set loan to paid as well
        Repayment nextPendingRepayment = repaymentService.getNextPendingRepayment(loanId);
        if(nextPendingRepayment == null){
            loan.setLoanStatus(LoanStatus.PAID);
            loanRepository.save(loan);
        }

        //return updated loan details
        return loanToLoanResponseDtoConverter(loan);
    }

    @Override
    public LoanResponseDto getLoanDetailsByLoanIdAndUserId(String loanId) {

        String userId = getUserIdFromContext();

        //fetch loan details from db for loanId and userId
        Loan loan = loanRepository.findByLoanIdAndUserId(loanId, userId);

        //check if loan exists with this loanId
        if(loan == null || ObjectUtils.isEmpty(loan)){
            throw new RuntimeException("No loan found with this loanId " + loanId);
        }

        return loanToLoanResponseDtoConverter(loan);

    }

    private LoanResponseDto loanToLoanResponseDtoConverter(Loan loan){

        //get repayment details for this loan
        List<Repayment> repayments = repaymentService.getRepaymentsByLoanId(loan.getLoanId());

        //create repayment response list
        List<RepaymentResponseDto> repaymentResponseDtoList = new ArrayList<>();
        for(Repayment repayment : repayments){
            RepaymentResponseDto repaymentResponseDto = RepaymentResponseDto
                    .Builder
                    .repaymentResponseDto()
                    .withAmount(repayment.getAmount())
                    .withDate(DateUtils.convertTimestampToDate(repayment.getDate()))
                    .withRepaymentStatus(repayment.getStatus())
                    .build();
            repaymentResponseDtoList.add(repaymentResponseDto);
        }

        //create and return loan output response
        return LoanResponseDto.Builder
                .loanResponseDto()
                .withLoanId(loan.getLoanId())
                .withAmount(loan.getAmount())
                .withDisbursementDate(DateUtils.convertTimestampToDate(loan.getDisbursementDate()))
                .withLoanTermInWeeks(loan.getLoanTermInWeeks())
                .withLoanStatus(loan.getLoanStatus())
                .withRepayments(repaymentResponseDtoList)
                .build();
    }

    private String getUserIdFromContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //username is unique userId in our application
        return authentication.getName();
    }
}
