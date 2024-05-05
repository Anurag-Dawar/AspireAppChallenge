package com.example.aspireloanapi.repository;

import com.example.aspireloanapi.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
    Loan findByLoanId(String loanId);

    Loan findByLoanIdAndUserId(String loanId, String userId);


}
