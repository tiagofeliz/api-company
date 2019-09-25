package br.com.hotmart.company.service.impl;

import br.com.hotmart.company.model.dto.EmployeeDto;
import br.com.hotmart.company.model.dto.ProjectDto;
import br.com.hotmart.company.model.entity.Employee;
import br.com.hotmart.company.model.entity.Project;
import br.com.hotmart.company.repository.ProjectRepository;
import br.com.hotmart.company.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Override
    public List<ProjectDto> findAll() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(ProjectDto::new).collect(Collectors.toList());
    }

    @Override
    public Optional<ProjectDto> findById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(ProjectDto::new);
    }

    @Override
    public ProjectDto create(Project project) {
        return new ProjectDto(projectRepository.save(project));
    }

    @Override
    public ProjectDto update(Project project, Long id) {
        Optional<Project> projectOptional = findBy(id);
        save(projectOptional.get(), project);
        return new ProjectDto(projectOptional.get());
    }

    private void save(Project currentProject, Project updateTo) {
        currentProject.setName(updateTo.getName());
        currentProject.setValue(updateTo.getValue());
        currentProject.setStartDate(updateTo.getStartDate());
        currentProject.setEndDate(updateTo.getEndDate());
    }

    @Override
    public void delete(Long id) {
        Optional<Project> project = findBy(id);
        projectRepository.delete(project.get());
    }

    @Override
    public List<EmployeeDto> registerEmployee(Long projectId, Long employeeId) {
        Optional<Project> project = findBy(projectId);
        Optional<Employee> employee = employeeService.findBy(employeeId); // TODO refactor public method to private
        register(project, employee);
        return project.get().getEmployees().stream().map(EmployeeDto::new).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> unregisterEmployee(Long projectId, Long employeeId) {
        Optional<Project> project = findBy(projectId);
        Optional<Employee> employee = employeeService.findBy(employeeId); // TODO refactor public method to private
        unregister(project, employee);
        return project.get().getEmployees().stream().map(EmployeeDto::new).collect(Collectors.toList());
    }

    private void register(Optional<Project> project, Optional<Employee> employee) {
        if(project.get().getEmployees() == null){
            List<Employee> projectEmployees = new ArrayList<>();
            projectEmployees.add(employee.get());
            project.get().setEmployees(projectEmployees);
        }else{
            List<Employee> projectEmployees = project.get().getEmployees();
            if(employeeWorksOnTheProject(projectEmployees, employee.get().getId())){
                throw new RuntimeException("The employee alredy works on this project");
            }
            projectEmployees.add(employee.get());
        }
    }

    private void unregister(Optional<Project> project, Optional<Employee> employee) {
        if(project.get().getEmployees() == null){
            throw new RuntimeException("This project have no employees to unregister");
        }else{
            List<Employee> projectEmployees = project.get().getEmployees();
            if(!employeeWorksOnTheProject(projectEmployees, employee.get().getId())){
                throw new RuntimeException("The employee dont works on this project");
            }
            List<Employee> employeeList = projectEmployees.stream().filter(filteredEmployee -> Objects.equals(filteredEmployee.getId(), employee.get().getId())).collect(Collectors.toList());
            projectEmployees.remove(employeeList.get(0));
        }
    }

    private Optional<Project> findBy(Long id){
        Optional<Project> project = projectRepository.findById(id);
        if(!project.isPresent()){
            throw new RuntimeException("Project not found");
        }
        return project;
    }

    private boolean employeeWorksOnTheProject(List<Employee> projectEmployees, Long employeeId){
        final List<Employee> employeeInList = projectEmployees.stream().filter(employee -> Objects.equals(employee.getId(), employeeId)).collect(Collectors.toList());
        return !employeeInList.isEmpty();
    }
}
