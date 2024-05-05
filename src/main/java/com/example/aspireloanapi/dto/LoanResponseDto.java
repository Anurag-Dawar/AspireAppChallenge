package com.example.aspireloanapi.dto;

import com.example.aspireloanapi.enums.LoanStatus;

import java.math.BigDecimal;
import java.util.List;

public class LoanResponseDto {

    private String loanId;

    private BigDecimal amount;

    private String disbursementDate;

    private int loanTermInWeeks;

    private LoanStatus loanStatus;

    private List<RepaymentResponseDto> repayments;

    public LoanResponseDto() {
    }

    public LoanResponseDto(String loanId, BigDecimal amount, String disbursementDate,
                           int loanTermInWeeks, LoanStatus loanStatus, List<RepaymentResponseDto> repayments) {
        this.loanId = loanId;
        this.amount = amount;
        this.disbursementDate = disbursementDate;
        this.loanTermInWeeks = loanTermInWeeks;
        this.loanStatus = loanStatus;
        this.repayments = repayments;
    }


    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(String disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public int getLoanTermInWeeks() {
        return loanTermInWeeks;
    }

    public void setLoanTermInWeeks(int loanTermInWeeks) {
        this.loanTermInWeeks = loanTermInWeeks;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public List<RepaymentResponseDto> getRepayments() {
        return repayments;
    }

    public void setRepayments(List<RepaymentResponseDto> repayments) {
        this.repayments = repayments;
    }

    public interface LoanIdStep {
        AmountStep withLoanId(String loanId);
    }

    public interface AmountStep {
        DisbursementDateStep withAmount(BigDecimal amount);
    }

    public interface DisbursementDateStep {
        LoanTermInWeeksStep withDisbursementDate(String disbursementDate);
    }

    public interface LoanTermInWeeksStep {
        LoanStatusStep withLoanTermInWeeks(int loanTermInWeeks);
    }

    public interface LoanStatusStep {
        RepaymentsStep withLoanStatus(LoanStatus loanStatus);
    }

    public interface RepaymentsStep {
        BuildStep withRepayments(List<RepaymentResponseDto> repayments);
    }

    public interface BuildStep {
        LoanResponseDto build();
    }


    public static class Builder implements LoanIdStep, AmountStep, DisbursementDateStep, LoanTermInWeeksStep, LoanStatusStep, RepaymentsStep, BuildStep {
        private String loanId;
        private BigDecimal amount;
        private String disbursementDate;
        private int loanTermInWeeks;
        private LoanStatus loanStatus;
        private List<RepaymentResponseDto> repayments;

        private Builder() {
        }

        public static LoanIdStep loanResponseDto() {
            return new Builder();
        }

        @Override
        public AmountStep withLoanId(String loanId) {
            this.loanId = loanId;
            return this;
        }

        @Override
        public DisbursementDateStep withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        @Override
        public LoanTermInWeeksStep withDisbursementDate(String disbursementDate) {
            this.disbursementDate = disbursementDate;
            return this;
        }

        @Override
        public LoanStatusStep withLoanTermInWeeks(int loanTermInWeeks) {
            this.loanTermInWeeks = loanTermInWeeks;
            return this;
        }

        @Override
        public RepaymentsStep withLoanStatus(LoanStatus loanStatus) {
            this.loanStatus = loanStatus;
            return this;
        }

        @Override
        public BuildStep withRepayments(List<RepaymentResponseDto> repayments) {
            this.repayments = repayments;
            return this;
        }

        @Override
        public LoanResponseDto build() {
            return new LoanResponseDto(
                    this.loanId,
                    this.amount,
                    this.disbursementDate,
                    this.loanTermInWeeks,
                    this.loanStatus,
                    this.repayments
            );
        }
    }
}
