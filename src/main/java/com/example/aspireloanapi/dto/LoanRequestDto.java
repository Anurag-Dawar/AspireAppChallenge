package com.example.aspireloanapi.dto;

import java.math.BigDecimal;
import java.util.Date;

public class LoanRequestDto {

    private BigDecimal amount;

    private int loanTermInWeeks;

    public LoanRequestDto() {
    }

    public LoanRequestDto(BigDecimal amount, int loanTermInWeeks) {
        this.amount = amount;
        this.loanTermInWeeks = loanTermInWeeks;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getLoanTermInWeeks() {
        return loanTermInWeeks;
    }

    public void setLoanTermInWeeks(int loanTermInWeeks) {
        this.loanTermInWeeks = loanTermInWeeks;
    }

    public interface AmountStep {
        LoanTermInWeeksStep withAmount(BigDecimal amount);
    }

    public interface LoanTermInWeeksStep {
        BuildStep withLoanTermInWeeks(int loanTermInWeeks);
    }

    public interface BuildStep {
        LoanRequestDto build();
    }


    public static class Builder implements AmountStep, LoanTermInWeeksStep, BuildStep {
        private BigDecimal amount;
        private int loanTermInWeeks;

        private Builder() {
        }

        public static AmountStep loanRequestDto() {
            return new Builder();
        }

        @Override
        public LoanTermInWeeksStep withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        @Override
        public BuildStep withLoanTermInWeeks(int loanTermInWeeks) {
            this.loanTermInWeeks = loanTermInWeeks;
            return this;
        }

        @Override
        public LoanRequestDto build() {
            return new LoanRequestDto(
                    this.amount,
                    this.loanTermInWeeks
            );
        }
    }
}
