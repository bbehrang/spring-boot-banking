package com.app.controller;

import com.app.contstants.ApiConstants;
import com.app.models.Employer;
import com.app.services.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/employers")
public class EmployerController {
    @Autowired
    private EmployerService employerService;

    @GetMapping
    public Set<Employer> getAll(){
        return employerService.findAll();
    }
    @DeleteMapping
    public void deleteEmployers(@RequestBody Set<Employer> employers){
        employerService.deleteAll(employers);
    }
    @GetMapping("/{id}")
    public Employer getEmployerById(@PathVariable("id") long id, HttpServletResponse response){
        Employer employer = employerService.findById(id);
        if(employer == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return employer;
    }
    @PutMapping("/{id}")
    public Employer updateEmployerById(@PathVariable("id") long id,
                                       @RequestBody Employer employer,
                                       HttpServletResponse response){
        Employer employerSaved = employerService.updateById(id, employer);
        if(employerSaved == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return employerSaved;
    }
    @DeleteMapping("/{id}")
    public void deleteEmployer(@PathVariable("id") Long id,
                                       HttpServletResponse response){
        employerService.deleteById(id);
    }

}
