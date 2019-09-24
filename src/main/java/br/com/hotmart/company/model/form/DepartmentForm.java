package br.com.hotmart.company.model.form;

import br.com.hotmart.company.model.entity.Budget;
import br.com.hotmart.company.model.entity.Department;
import br.com.hotmart.company.model.entity.Project;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class DepartmentForm {

    private Long id;
    @NotNull @NotEmpty
    private String name;
    private List<Project> projects;
    private List<Budget> budgets;

    public Department toEntity(){
        Department department = new Department();
        department.setName(this.name);
        department.setBudgets(this.budgets);
        department.setProjects(this.projects);
        return department;
    }

}
