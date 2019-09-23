package br.com.hotmart.company.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double value;
    private LocalDate startDate;
    private LocalDate endDate;
    @OneToMany
    private List<Employee> employees;

}
