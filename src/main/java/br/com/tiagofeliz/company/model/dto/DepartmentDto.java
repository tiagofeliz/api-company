package br.com.tiagofeliz.company.model.dto;

import br.com.tiagofeliz.company.model.entity.Budget;
import br.com.tiagofeliz.company.model.entity.Department;
import br.com.tiagofeliz.company.model.entity.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class DepartmentDto {

    private Long id;
    private String name;
    @JsonIgnore
    private List<Project> projects;
    @JsonIgnore
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

    public static List<DepartmentDto> asList(List<Department> departmentList){
        return departmentList.stream().map(DepartmentDto::new).collect(Collectors.toList());
    }
}
