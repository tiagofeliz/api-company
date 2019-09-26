package br.com.hotmart.company.service;

import br.com.hotmart.company.model.dto.DepartmentDto;
import br.com.hotmart.company.model.dto.EmployeeDto;
import br.com.hotmart.company.model.dto.ProjectDto;
import br.com.hotmart.company.model.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    List<DepartmentDto> findAll();
    Optional<DepartmentDto> findById(Long id);
    DepartmentDto create(Department department);
    DepartmentDto update(Department department, Long id);
    void delete(Long id);
    List<EmployeeDto> employees(Long id);
//    List<ProjectDto> registerProject(Long departmentId, Long projectId);
}
