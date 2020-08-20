package com.app.repository;

import com.app.models.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface EmployerDao extends CrudRepository<Employer, Long> {

}
