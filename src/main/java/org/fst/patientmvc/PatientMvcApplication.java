package org.fst.patientmvc;

import org.fst.patientmvc.entities.Patient;
import org.fst.patientmvc.repositories.PatientRepository;
import org.fst.patientmvc.security.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.xml.crypto.Data;
import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //@Bean
    CommandLineRunner saveUsers(SecurityService securityService)
    {
        return args -> {
            securityService.saveNewUser("imane" , "1234" , "1234");
            securityService.saveNewUser("doha" ,"2345" , "2345");
            securityService.saveNewUser("ayoub" ,"user" , "user");
            securityService.saveNewRole("USER" , "");
            securityService.saveNewRole("ADMIN" , "");
            securityService.addRoleToUser("imane" , "ADMIN");
            securityService.addRoleToUser("doha" , "USER");
            securityService.addRoleToUser("ayoub" , "USER");
        };
    }
}
