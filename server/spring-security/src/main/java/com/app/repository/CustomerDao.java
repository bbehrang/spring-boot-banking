package com.app.repository;

import com.app.models.Account;
import com.app.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerDao extends PagingAndSortingRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

}
