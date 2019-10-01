package br.com.hotmart.company.model.dto;

import br.com.hotmart.company.model.entity.Employee;
import br.com.hotmart.company.model.entity.Project;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProjectDto {

    private Long id;
    private String name;
    private double value;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Employee> employees;

    public ProjectDto(Project project){
        this.id = project.getId();
        this.name = project.getName();
        this.value = project.getValue();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.employees = project.getEmployees();
    }

    public static List<ProjectDto> asList(List<Project> projectList){
        return projectList.stream().map(ProjectDto::new).collect(Collectors.toList());
    }

}
