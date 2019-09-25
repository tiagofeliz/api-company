package br.com.hotmart.company.service.impl;

import br.com.hotmart.company.model.dto.EmployeeDto;
import br.com.hotmart.company.model.entity.Employee;
import br.com.hotmart.company.repository.EmployeeRepository;
import br.com.hotmart.company.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressServiceImpl addressService;

    @Override
    public List<EmployeeDto> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeDto::new).collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeDto> findById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(EmployeeDto::new);
    }

    @Override
    public EmployeeDto create(Employee employee) {
        setEmployeeSupervisor(employee);
        addressService.create(employee.getAddress());
        return new EmployeeDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDto update(Employee employee, Long id) {
        Optional<Employee> employeeOptional = findBy(id);
        setEmployeeSupervisor(employee);
        save(employeeOptional.get(), employee);
        addressService.save(employeeOptional.get().getAddress(), employee.getAddress());
        return new EmployeeDto(employeeOptional.get());
    }

    private void setEmployeeSupervisor(Employee employee) {
        if(employee.getSupervisor() != null){
            Optional<Employee> supervisor = employeeRepository.findById(employee.getSupervisor().getId());
            if(supervisor.isPresent()){
                employee.setSupervisor(supervisor.get());
            }else{
                throw new RuntimeException("Employee's supervisor not found");
            }
        }
    }

    private void save(Employee currentEmployee, Employee toUpdateEmployee) {
        currentEmployee.setName(toUpdateEmployee.getName());
        currentEmployee.setCpf(toUpdateEmployee.getCpf());
        currentEmployee.setSalary(toUpdateEmployee.getSalary());
        currentEmployee.setGender(toUpdateEmployee.getGender());
        currentEmployee.setBirthDate(toUpdateEmployee.getBirthDate());
        currentEmployee.setSupervisor(toUpdateEmployee.getSupervisor());
    }

    @Override
    public void delete(Long id) {
        Optional<Employee> employee = findBy(id);
        employeeRepository.delete(employee.get());
    }

    @Override
    public List<EmployeeDto> supervisedEmployees(Long idSupervisor) {
        List<Employee> employees = employeeRepository.findBySupervisor_Id(idSupervisor);
        return employees.stream().map(EmployeeDto::new).collect(Collectors.toList());
    }

    public Optional<Employee> findBy(Long id){ // TODO refactor public method to private
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent()){
            throw new RuntimeException("Employee not found");
        }
        return employee;
    }
}
