package com.app.controller;

import com.app.contstants.ApiConstants;
import com.app.dto.reponse.EmployerResponseDto;
import com.app.dto.request.EmployerRequestDto;
import com.app.facade.EmployerFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/employers")
public class EmployerController {
    @Autowired
    private EmployerFacade employerFacade;

    @GetMapping
    public List<EmployerResponseDto> getAll(){
        return employerFacade.findAll();
    }
    @DeleteMapping
    public void deleteEmployers(@Valid @RequestBody Set<EmployerRequestDto> employers){
        log.info("deleting all employers");
        employerFacade.deleteAll(employers);
    }
    @GetMapping("/{id}")
    public EmployerResponseDto getEmployerById(@PathVariable("id") long id){
        log.info("finding employer with id {}", id);
        return employerFacade.findById(id);
    }
    @PutMapping("/{id}")
    public EmployerResponseDto updateEmployerById(@PathVariable("id") long id,
                                       @Valid @RequestBody EmployerRequestDto employer){
        log.info("updating employer with id {}", id);
        return employerFacade.updateById(id, employer);
    }
    @DeleteMapping("/{id}")
    public void deleteEmployer(@PathVariable("id") Long id){
        log.info("deleting employer with id {}", id);
        employerFacade.deleteById(id);
    }

}
