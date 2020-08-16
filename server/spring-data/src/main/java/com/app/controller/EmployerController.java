package com.app.controller;

import com.app.contstants.ApiConstants;
import com.app.models.Employer;
import com.app.services.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/employers")
public class EmployerController {
    @Autowired
    private EmployerService employerService;

    @GetMapping
    public List<Employer> getAll(){
        return employerService.findAll();
    }
    @DeleteMapping
    public void deleteEmployers(@RequestBody List<Employer> employers){
        employerService.deleteAll(employers);
    }
    @GetMapping("/{id}")
    public Employer getEmployerById(@PathVariable("id") long id, HttpServletResponse response){
        Employer employer = employerService.getOne(id);
        if(employer == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return employer;
    }
    @PutMapping("/{id}")
    public Employer updateEmployerById(@PathVariable("id") long id,
                                       @RequestBody Employer employer,
                                       HttpServletResponse response){
        Employer employerSaved = employerService.update(id, employer);
        if(employerSaved == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return employerSaved;
    }
    @DeleteMapping("/{id}")
    public void deleteEmployer(@PathVariable("id") long id,
                                       HttpServletResponse response){
        boolean isDelete = employerService.delete(id);
        if(isDelete == false) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

}
