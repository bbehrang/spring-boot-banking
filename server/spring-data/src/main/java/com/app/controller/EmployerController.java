package com.app.controller;

import com.app.contstants.ApiConstants;
import com.app.dto.reponse.EmployerResponseDto;
import com.app.dto.request.EmployerRequestDto;
import com.app.facade.EmployerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/employers")
public class EmployerController {
    @Autowired
    private EmployerFacade employerFacade;

    @GetMapping
    public Set<EmployerResponseDto> getAll(){
        return employerFacade.findAll();
    }
    @DeleteMapping
    public void deleteEmployers(@RequestBody Set<EmployerRequestDto> employers){
        employerFacade.deleteAll(employers);
    }
    @GetMapping("/{id}")
    public EmployerResponseDto getEmployerById(@PathVariable("id") long id, HttpServletResponse response){
        EmployerResponseDto employer = employerFacade.findById(id);
        if(employer == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return employer;
    }
    @PutMapping("/{id}")
    public EmployerResponseDto updateEmployerById(@PathVariable("id") long id,
                                       @RequestBody EmployerRequestDto employer,
                                       HttpServletResponse response){
        EmployerResponseDto employerSaved = employerFacade.updateById(id, employer);
        if(employerSaved == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return employerSaved;
    }
    @DeleteMapping("/{id}")
    public void deleteEmployer(@PathVariable("id") Long id,
                                       HttpServletResponse response){
        employerFacade.deleteById(id);
    }

}
