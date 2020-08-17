package com.app.facade;

import com.app.dto.reponse.EmployerResponseDto;
import com.app.dto.request.EmployerRequestDto;
import com.app.models.Employer;
import com.app.services.EmployerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class EmployerFacade {
    @Autowired
    private EmployerService employerService;
    @Autowired
    private ModelMapper modelMapper;

    public Set<EmployerResponseDto> findAll(){
        return  modelMapper.map(employerService.findAll(), new TypeToken<Set<EmployerResponseDto>>(){}.getType());
    }
    public EmployerResponseDto findById(Long id){
        return modelMapper.map(employerService.findById(id), EmployerResponseDto.class);
    }

    public void delete(EmployerRequestDto employer){
        employerService.delete(modelMapper.map(employer, Employer.class));
    }
    public void deleteById(Long id){
        employerService.deleteById(id);
    }
    public void deleteAll(Set<EmployerRequestDto> employers){
        employerService.deleteAll(modelMapper.map(employers, new TypeToken<Set<Employer>>(){}.getType()));
    }

    public EmployerResponseDto save(EmployerRequestDto employer){
        return modelMapper.map(employerService.save(modelMapper.map(employer, Employer.class)),
                EmployerResponseDto.class);
    }
    public EmployerResponseDto updateById(Long id, EmployerRequestDto employerCandidate){
        return modelMapper.map(employerService.updateById(id, modelMapper.map(employerCandidate, Employer.class)),
                EmployerResponseDto.class);
    }
}
