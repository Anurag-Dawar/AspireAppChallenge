package com.example.aspireloanapi.dto;

import com.example.aspireloanapi.enums.RepaymentStatus;

import java.math.BigDecimal;

public class RepaymentResponseDto {

    private BigDecimal amount;

    private String date;

    private RepaymentStatus repaymentStatus;

    public RepaymentResponseDto() {
    }

    public RepaymentResponseDto(BigDecimal amount, String date, RepaymentStatus repaymentStatus) {
        this.amount = amount;
        this.date = date;
        this.repaymentStatus = repaymentStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RepaymentStatus getRepaymentStatus() {
        return repaymentStatus;
    }

    public void setRepaymentStatus(RepaymentStatus repaymentStatus) {
        this.repaymentStatus = repaymentStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public interface AmountStep {
        DateStep withAmount(BigDecimal amount);
    }

    public interface DateStep {
        RepaymentStatusStep withDate(String date);
    }

    public interface RepaymentStatusStep {
        BuildStep withRepaymentStatus(RepaymentStatus repaymentStatus);
    }

    public interface BuildStep {
        RepaymentResponseDto build();
    }


    public static class Builder implements AmountStep, DateStep, RepaymentStatusStep, BuildStep {
        private BigDecimal amount;
        private String date;
        private RepaymentStatus repaymentStatus;

        private Builder() {
        }

        public static AmountStep repaymentResponseDto() {
            return new Builder();
        }

        @Override
        public DateStep withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        @Override
        public RepaymentStatusStep withDate(String date) {
            this.date = date;
            return this;
        }

        @Override
        public BuildStep withRepaymentStatus(RepaymentStatus repaymentStatus) {
            this.repaymentStatus = repaymentStatus;
            return this;
        }

        @Override
        public RepaymentResponseDto build() {
            return new RepaymentResponseDto(
                    this.amount,
                    this.date,
                    this.repaymentStatus
            );
        }
    }
}
