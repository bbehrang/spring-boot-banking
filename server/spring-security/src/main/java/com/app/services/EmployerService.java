package com.app.services;


import com.app.models.Employer;
import com.app.repository.AccountDao;
import com.app.repository.CustomerDao;
import com.app.repository.EmployerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class EmployerService {
    @Autowired
    private EmployerDao employerDao;

    @Transactional(readOnly = true)
    public List<Employer> findAll(){
        return (List<Employer>) employerDao.findAll();
    }
    @Transactional(readOnly = true)
    public Employer findById(Long id){
        return employerDao
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employer not found for id={"+id+"}"));
    }

    public void delete(Employer employer){
        employerDao.delete(employer);
    }
    public void deleteById(Long id){
        employerDao.deleteById(id);
    }
    public void deleteAll(Set<Employer> employers){
        employerDao.deleteAll(employers);
    }

    public Employer save(Employer employer){
        return employerDao.save(employer);
    }
    public Employer updateById(Long id, Employer employerCandidate){
        Employer employer = employerDao
                .findById(employerCandidate.getId())
                .orElseThrow(() -> new EntityNotFoundException("Employer not found for id={"+id+"}"));
        employer.setAddress(employerCandidate.getAddress());
        employer.setNumber(employerCandidate.getNumber());
        return employerDao.save(employer);
    }
}
