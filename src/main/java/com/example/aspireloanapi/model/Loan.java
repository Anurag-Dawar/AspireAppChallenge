package com.example.aspireloanapi.model;

import com.example.aspireloanapi.enums.LoanStatus;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@Table(name = "loans")
@EntityListeners(AuditingEntityListener.class)
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "loan_id", nullable = false, unique = true)
    private String loanId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "disbursement_date", nullable = false)
    private Long disbursementDate;

    @Column(name = "loan_term_in_weeks", nullable = false)
    private int loanTermInWeeks;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "loanStatus", nullable = false)
    private LoanStatus loanStatus;


    public Loan() {
    }

    public Loan(String loanId, BigDecimal amount, Long disbursementDate, int loanTermInWeeks, String userId, LoanStatus loanStatus) {
        this.loanId = loanId;
        this.amount = amount;
        this.disbursementDate = disbursementDate;
        this.loanTermInWeeks = loanTermInWeeks;
        this.userId = userId;
        this.loanStatus = loanStatus;
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

    public int getLoanTermInWeeks() {
        return loanTermInWeeks;
    }

    public void setLoanTermInWeeks(int loanTermInWeeks) {
        this.loanTermInWeeks = loanTermInWeeks;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public Long getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(Long disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public interface LoanIdStep {
        AmountStep withLoanId(String loanId);
    }

    public interface AmountStep {
        DisbursementDateStep withAmount(BigDecimal amount);
    }

    public interface DisbursementDateStep {
        LoanTermInWeeksStep withDisbursementDate(Long disbursementDate);
    }

    public interface LoanTermInWeeksStep {
        UserIdStep withLoanTermInWeeks(int loanTermInWeeks);
    }

    public interface UserIdStep {
        LoanStatusStep withUserId(String userId);
    }

    public interface LoanStatusStep {
        BuildStep withLoanStatus(LoanStatus loanStatus);
    }

    public interface BuildStep {
        Loan build();
    }


    public static class Builder implements LoanIdStep, AmountStep, DisbursementDateStep, LoanTermInWeeksStep, UserIdStep, LoanStatusStep, BuildStep {
        private String loanId;
        private BigDecimal amount;
        private Long disbursementDate;
        private int loanTermInWeeks;
        private String userId;
        private LoanStatus loanStatus;

        private Builder() {
        }

        public static LoanIdStep loan() {
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
        public LoanTermInWeeksStep withDisbursementDate(Long disbursementDate) {
            this.disbursementDate = disbursementDate;
            return this;
        }

        @Override
        public UserIdStep withLoanTermInWeeks(int loanTermInWeeks) {
            this.loanTermInWeeks = loanTermInWeeks;
            return this;
        }

        @Override
        public LoanStatusStep withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        @Override
        public BuildStep withLoanStatus(LoanStatus loanStatus) {
            this.loanStatus = loanStatus;
            return this;
        }

        @Override
        public Loan build() {
            return new Loan(
                    this.loanId,
                    this.amount,
                    this.disbursementDate,
                    this.loanTermInWeeks,
                    this.userId,
                    this.loanStatus
            );
        }
    }
}
