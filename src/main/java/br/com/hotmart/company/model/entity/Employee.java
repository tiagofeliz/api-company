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
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private double salary;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @ManyToOne
    private Employee supervisor;
    @ManyToMany
    @JoinTable(name = "employee_project",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "project_id") })
    @JsonIgnore
    private List<Project> projects;

    public Employee(String name, String cpf, LocalDate birthDate, double salary, Gender gender, Address address, Employee supervisor) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.salary = salary;
        this.gender = gender;
        this.address = address;
        this.supervisor = supervisor;
    }
}
