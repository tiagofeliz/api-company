package br.com.hotmart.company.service;

import br.com.hotmart.company.model.dto.EmployeeDto;
import br.com.hotmart.company.model.dto.ProjectDto;
import br.com.hotmart.company.model.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<ProjectDto> findAll();
    Optional<ProjectDto> findById(Long id);
    ProjectDto create(Project project);
    ProjectDto update(Project project, Long id);
    void delete(Long id);
    List<EmployeeDto> registerEmployee(Long projectId, Long employeeId);
    List<EmployeeDto> unregisterEmployee(Long projectId, Long employeeId);
}
