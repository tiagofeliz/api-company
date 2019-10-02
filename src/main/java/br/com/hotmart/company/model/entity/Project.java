package br.com.hotmart.company.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToMany
    @JoinTable(name = "employee_project",
            joinColumns = { @JoinColumn(name = "project_id") },
            inverseJoinColumns = { @JoinColumn(name = "employee_id") })
    private List<Employee> employees;
    @ManyToOne
    @JsonIgnore
    private Department department;

}
