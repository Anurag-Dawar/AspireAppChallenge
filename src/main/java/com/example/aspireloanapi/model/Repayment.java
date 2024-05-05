package com.example.aspireloanapi.model;

import com.example.aspireloanapi.enums.RepaymentStatus;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@Table(name = "repayments")
@EntityListeners(AuditingEntityListener.class)
public class Repayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "loan_id", nullable = false)
    private String loanId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Long date;

    @Enumerated(EnumType.STRING)
    @Column(name = "repayment_status")
    private RepaymentStatus status;

    public Repayment() {
    }

    public Repayment(String loanId, BigDecimal amount, Long date, RepaymentStatus status) {
        this.loanId = loanId;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public RepaymentStatus getStatus() {
        return status;
    }

    public void setStatus(RepaymentStatus status) {
        this.status = status;
    }

    public interface LoanIdStep {
        AmountStep withLoanId(String loanId);
    }

    public interface AmountStep {
        DateStep withAmount(BigDecimal amount);
    }

    public interface DateStep {
        StatusStep withDate(Long date);
    }

    public interface StatusStep {
        BuildStep withStatus(RepaymentStatus status);
    }

    public interface BuildStep {
        Repayment build();
    }


    public static class Builder implements LoanIdStep, AmountStep, DateStep, StatusStep, BuildStep {
        private String loanId;
        private BigDecimal amount;
        private Long date;
        private RepaymentStatus status;

        private Builder() {
        }

        public static LoanIdStep repayment() {
            return new Builder();
        }

        @Override
        public AmountStep withLoanId(String loanId) {
            this.loanId = loanId;
            return this;
        }

        @Override
        public DateStep withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        @Override
        public StatusStep withDate(Long date) {
            this.date = date;
            return this;
        }

        @Override
        public BuildStep withStatus(RepaymentStatus status) {
            this.status = status;
            return this;
        }

        @Override
        public Repayment build() {
            return new Repayment(
                    this.loanId,
                    this.amount,
                    this.date,
                    this.status
            );
        }
    }
}
