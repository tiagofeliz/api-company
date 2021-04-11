package br.com.tiagofeliz.company.service;

import br.com.tiagofeliz.company.model.dto.EmployeeDto;
import br.com.tiagofeliz.company.model.dto.ProjectDto;
import br.com.tiagofeliz.company.model.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeDto> findAll();
    List<EmployeeDto> findByName(String name);
    Optional<EmployeeDto> findById(Long id);
    EmployeeDto create(Employee employee);
    EmployeeDto update(Employee employee, Long id);
    void delete(Long id);
    List<EmployeeDto> supervisedEmployees(Long idSupervisor);
    List<ProjectDto> projects(Long id);
}
