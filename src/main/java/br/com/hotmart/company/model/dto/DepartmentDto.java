package br.com.hotmart.company.model.dto;

import br.com.hotmart.company.model.entity.Budget;
import br.com.hotmart.company.model.entity.Department;
import br.com.hotmart.company.model.entity.Project;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DepartmentDto {

    private Long id;
    private String name;
    private List<Project> projects;
    private List<Budget> budgets;

    public DepartmentDto(Department department){
        this.id = department.getId();
        this.name = department.getName();
        this.budgets = department.getBudgets();
        this.projects = department.getProjects();
    }

    public Department toEntity(){
        Department department = new Department();
        department.setId(this.id);
        department.setName(this.name);
        department.setBudgets(this.budgets);
        department.setProjects(this.projects);
        return department;
    }
}
