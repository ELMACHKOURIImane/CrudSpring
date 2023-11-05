package org.fst.patientmvc;

import org.fst.patientmvc.entities.Patient;
import org.fst.patientmvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.xml.crypto.Data;
import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);
    }
//@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args->{
            patientRepository.findAll().forEach(p->{
               System.out.println(p.getName());
            });
        };
    }
}
