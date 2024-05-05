package com.example.aspireloanapi.impl;

import com.example.aspireloanapi.enums.RepaymentStatus;
import com.example.aspireloanapi.model.Loan;
import com.example.aspireloanapi.model.Repayment;
import com.example.aspireloanapi.repository.RepaymentRepository;
import com.example.aspireloanapi.service.RepaymentService;
import com.example.aspireloanapi.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RepaymentServiceImpl implements RepaymentService {

    @Autowired
    private RepaymentRepository repaymentRepository;

    @Override
    public List<Repayment> getRepaymentsByLoanId(String loanId) {
        return repaymentRepository.findByLoanId(loanId);
    }

    @Override
    public void createRepaymentsForLoan(Loan loan) {
        //to schedule weekly repayments, divide loan amount by tenure in weeks
        int numOfRepayments = loan.getLoanTermInWeeks();

        //get each emi amount
        BigDecimal emiAmount = BigDecimal.valueOf(loan.getAmount().intValue() / numOfRepayments);

        //set first repayment date
        long loanDisbursementDate = loan.getDisbursementDate();
        long repaymentDate = DateUtils.addWeekToTimeStamp(loanDisbursementDate);

        //add all weekly repayments in db
        for(int emi = 1; emi <= numOfRepayments; emi++){
            Repayment repayment = Repayment.Builder
                    .repayment()
                    .withLoanId(loan.getLoanId())
                    .withAmount(emiAmount)
                    .withDate(repaymentDate)
                    .withStatus(RepaymentStatus.PENDING)
                    .build();
            repaymentRepository.save(repayment);
            repaymentDate = DateUtils.addWeekToTimeStamp(repaymentDate);
        }
    }

    @Override
    public void makeRepayment(BigDecimal amount, String loanId) {
        //find the next pending repayment and mark it as paid if
        //amount is equal or greater than repayment amount
        Repayment nextPendingRepayment = getNextPendingRepayment(loanId);
        if(nextPendingRepayment == null){
            throw new RuntimeException("No pending repayment found for this loan with loanId " + loanId);
        }
        // check if amount entered in not less then the next pending payment amount
        if(amount.compareTo(nextPendingRepayment.getAmount()) < 0 ){
            throw new RuntimeException("Amount entered is less than next pending repayment amount");
        }

        //set repayment to paid in db
        nextPendingRepayment.setStatus(RepaymentStatus.PAID);
        repaymentRepository.save(nextPendingRepayment);
    }

    @Override
    public Repayment getNextPendingRepayment(String loanId){
        //get next pending repayment using orderBy date and status pending for a loanId
        return repaymentRepository.findFirstByLoanIdAndStatusOrderByDateAsc(loanId, RepaymentStatus.PENDING);
    }


}
