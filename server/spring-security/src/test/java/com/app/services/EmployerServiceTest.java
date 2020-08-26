package com.app.services;

import com.app.models.Employer;
import com.app.repository.EmployerDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EmployerServiceTest {
    @TestConfiguration
    static class EmployerServiceTestConfiguration{
        @Bean
        public EmployerService employerService(){
            return new EmployerService();
        }
    }
    @MockBean
    private EmployerDao employerDao;
    @Autowired
    private EmployerService employerService;


    @Test
    public void save(){
        Employer employer = new Employer("1", "addr", null);
        when(employerDao.save(employer)).then(res -> res.getArgument(0, Employer.class));
        assertThat(employer).isEqualTo(employerService.save(employer));
    }
    @Test
    public void updateById(){
        Employer employer = new Employer("1", "addr", null);
        when(employerDao.findById(any())).thenReturn(java.util.Optional.of(employer));
        when(employerDao.save(employer)).then(res -> res.getArgument(0, Employer.class));
        assertThat(employerService.updateById(1L, employer)).isEqualTo(employer);
    }

}
