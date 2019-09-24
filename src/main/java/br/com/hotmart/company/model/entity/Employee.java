package br.com.hotmart.company.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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
    @OneToOne
    private Address address;
    @ManyToOne
    private Employee supervisor;

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
