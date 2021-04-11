package br.com.tiagofeliz.company.model.form;

import br.com.tiagofeliz.company.model.entity.Employee;
import br.com.tiagofeliz.company.model.entity.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EmployeeForm {

    @NotNull @NotEmpty
    private String name;
    @NotNull @NotEmpty
    private String cpf;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private double salary;
    @NotNull
    private Gender gender;
    private Long idSupervisor;

    private AddressForm address;
    private Employee supervisor;

    public Employee toEntity() {
        Employee employee = new Employee();
        employee.setName(this.getName());
        employee.setCpf(this.getCpf());
        employee.setBirthDate(this.getBirthDate());
        employee.setSalary(this.getSalary());
        employee.setGender(this.getGender());

        if(this.address == null){
            throw new IllegalArgumentException("Employee's address is required");
        }

        employee.setAddress(this.address.toEntity());

        if(this.idSupervisor != null) {
            Employee supervisor = new Employee();
            supervisor.setId(this.idSupervisor);
            employee.setSupervisor(supervisor);
        }
        return employee;
    }
}
