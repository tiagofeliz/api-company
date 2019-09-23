package br.com.hotmart.company.model.dto;

import br.com.hotmart.company.model.entity.Address;
import br.com.hotmart.company.model.entity.Employee;
import br.com.hotmart.company.model.entity.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EmployeeDto {

    private Long id;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private double salary;
    private Gender gender;
    private AddressDto address;
    private Employee supervisor;

    public EmployeeDto(Employee employee){
        this.id = employee.getId();
        this.name = employee.getName();
        this.cpf = employee.getCpf();
        this.birthDate = employee.getBirthDate();
        this.salary = employee.getSalary();
        this.gender = employee.getGender();
        this.address = new AddressDto(employee.getAddress());
        this.supervisor = employee.getSupervisor();
    }

}
