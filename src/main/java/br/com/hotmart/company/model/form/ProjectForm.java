package br.com.hotmart.company.model.form;

import br.com.hotmart.company.model.entity.Employee;
import br.com.hotmart.company.model.entity.Project;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ProjectForm {

    @NotNull @NotEmpty
    private String name;
    private double value;
    private LocalDate startDate;
    private LocalDate endDate;

    public Project toEntity(){
        Project project = new Project();
        project.setName(this.name);
        project.setValue(this.value);
        project.setStartDate(this.startDate);
        project.setEndDate(this.endDate);
        return project;
    }

}
