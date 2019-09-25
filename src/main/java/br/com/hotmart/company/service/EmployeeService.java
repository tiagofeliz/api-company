package br.com.hotmart.company.service;

import br.com.hotmart.company.model.dto.EmployeeDto;
import br.com.hotmart.company.model.entity.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeDto> findAll();
    Optional<EmployeeDto> findById(Long id);
    EmployeeDto create(Employee employee);
    EmployeeDto update(Employee employee, Long id);
    void delete(Long id);
    List<EmployeeDto> supervisedEmployees(Long idSupervisor);
}
