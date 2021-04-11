package br.com.tiagofeliz.company.model.dto;

import br.com.tiagofeliz.company.model.entity.Employee;
import br.com.tiagofeliz.company.model.entity.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<EmployeeDto> asList(List<Employee> employeeList){
        return employeeList.stream().map(EmployeeDto::new).collect(Collectors.toList());
    }

}
