package br.com.tiagofeliz.company.service;

import br.com.tiagofeliz.company.model.dto.EmployeeDto;
import br.com.tiagofeliz.company.model.dto.ProjectDto;
import br.com.tiagofeliz.company.model.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<ProjectDto> findAll();
    Optional<ProjectDto> findById(Long id);
    ProjectDto create(Project project, Long departmentId);
    ProjectDto update(Project project, Long id);
    void delete(Long id);
    List<EmployeeDto> registerEmployee(Long projectId, Long employeeId);
    List<EmployeeDto> unregisterEmployee(Long projectId, Long employeeId);
}
