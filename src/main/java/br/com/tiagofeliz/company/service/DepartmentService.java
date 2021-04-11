package br.com.tiagofeliz.company.service;

import br.com.tiagofeliz.company.model.dto.BudgetStatusDto;
import br.com.tiagofeliz.company.model.dto.DepartmentDto;
import br.com.tiagofeliz.company.model.dto.EmployeeDto;
import br.com.tiagofeliz.company.model.dto.ProjectDto;
import br.com.tiagofeliz.company.model.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    List<DepartmentDto> findAll();
    Optional<DepartmentDto> findById(Long id);
    DepartmentDto create(Department department);
    DepartmentDto update(Department department, Long id);
    void delete(Long id);
    List<EmployeeDto> employees(Long id);
    List<BudgetStatusDto> budgetStatus(Long id);
    List<ProjectDto> projects(Long id);
}
