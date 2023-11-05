package org.fst.patientmvc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Entity
@Data  @AllArgsConstructor @NoArgsConstructor
public class Patient {
    @Id @GeneratedValue
    private  Long id ;
    @NotEmpty
    @Size(max = 5 , min = 2)
    private  String name ;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private Date dateNaissance ;
    private boolean malade ;
    private int score ;

}
