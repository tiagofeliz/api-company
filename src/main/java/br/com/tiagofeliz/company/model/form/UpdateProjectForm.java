package br.com.tiagofeliz.company.model.form;

import br.com.tiagofeliz.company.model.entity.Project;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UpdateProjectForm {

    @NotNull
    @NotEmpty
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
