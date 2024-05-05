package com.example.aspireloanapi.repository;

import com.example.aspireloanapi.enums.RepaymentStatus;
import com.example.aspireloanapi.model.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
    List<Repayment> findByLoanId(String loanId);

    Repayment findFirstByLoanIdAndStatusOrderByDateAsc(String loanId, RepaymentStatus status);


}
