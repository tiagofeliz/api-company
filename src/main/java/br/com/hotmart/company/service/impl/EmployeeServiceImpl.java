package br.com.hotmart.company.service.impl;

import br.com.hotmart.company.config.exception.ResourceNotFoundException;
import br.com.hotmart.company.model.dto.EmployeeDto;
import br.com.hotmart.company.model.dto.ProjectDto;
import br.com.hotmart.company.model.entity.Employee;
import br.com.hotmart.company.model.entity.Project;
import br.com.hotmart.company.repository.EmployeeRepository;
import br.com.hotmart.company.repository.ProjectRepository;
import br.com.hotmart.company.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressServiceImpl addressService;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<EmployeeDto> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return EmployeeDto.asList(employees);
    }

    @Override
    public List<EmployeeDto> findByName(String name) {
        List<Employee> employees = employeeRepository.findByName(name);
        return EmployeeDto.asList(employees);
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
    public EmployeeDto update(Employee updateTo, Long id) {
        if(!exists(id)){
            throw new ResourceNotFoundException("Department not found");
        }
        Employee employee = findBy(id);
        setEmployeeSupervisor(updateTo);
        save(employee, updateTo);
        addressService.save(employee.getAddress(), updateTo.getAddress());
        return new EmployeeDto(employee);
    }

    private void setEmployeeSupervisor(Employee employee) {
        if(employee.getSupervisor() != null) {
            if (!exists(employee.getSupervisor().getId())) {
                throw new ResourceNotFoundException("Employee's supervisor not found");
            }
            Employee supervisor = findBy(employee.getSupervisor().getId());
            employee.setSupervisor(supervisor);
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
        if(!exists(id)){
            throw new ResourceNotFoundException("Department not found");
        }
        Employee employee = findBy(id);
        employeeRepository.delete(employee);
    }

    @Override
    public List<EmployeeDto> supervisedEmployees(Long idSupervisor) {
        List<Employee> employees = employeeRepository.findBySupervisor_Id(idSupervisor);
        return EmployeeDto.asList(employees);
    }

    @Override
    public List<ProjectDto> projects(Long id) {
        if(!exists(id)){
            throw new ResourceNotFoundException("Department not found");
        }
        Employee employee = findBy(id);
        List<Project> projects = projectRepository.findByEmployees_Id(employee.getId());
        return ProjectDto.asList(projects);
    }

    public boolean exists(Long id){
        return employeeRepository.existsById(id);
    }

    public Employee findBy(Long id){
        return employeeRepository.findById(id).get();
    }
}
