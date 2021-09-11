package com.akhil.w.customerservices.repo;

import com.akhil.w.customerservices.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * akhil - 10/2/2020 - 11:43 AM
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
