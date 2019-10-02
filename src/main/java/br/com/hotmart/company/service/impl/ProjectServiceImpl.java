package br.com.hotmart.company.service.impl;

import br.com.hotmart.company.model.dto.DepartmentDto;
import br.com.hotmart.company.model.dto.EmployeeDto;
import br.com.hotmart.company.model.dto.ProjectDto;
import br.com.hotmart.company.model.entity.Department;
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

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Override
    public List<ProjectDto> findAll() {
        List<Project> projects = projectRepository.findAll();
        return ProjectDto.asList(projects);
    }

    @Override
    public Optional<ProjectDto> findById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(ProjectDto::new);
    }

    @Override
    public ProjectDto create(Project project, Long departmentId) {
        Department department = getDepartment(departmentId);
        project.setDepartment(department);
        projectRepository.save(project);
        return new ProjectDto(project);
    }

    private Department getDepartment(Long id) {
        Optional<DepartmentDto> departmentDto = departmentService.findById(id);
        if(!departmentDto.isPresent()){
            throw new RuntimeException("Project's department not found");
        }
        return departmentDto.get().toEntity();
    }

    @Override
    public ProjectDto update(Project updateTo, Long id) {
        Project project = findBy(id);
        save(project, updateTo);
        return new ProjectDto(project);
    }

    private void save(Project currentProject, Project updateTo) {
        currentProject.setName(updateTo.getName());
        currentProject.setValue(updateTo.getValue());
        currentProject.setStartDate(updateTo.getStartDate());
        currentProject.setEndDate(updateTo.getEndDate());
    }

    @Override
    public void delete(Long id) {
        Project project = findBy(id);
        projectRepository.delete(project);
    }

    @Override
    public List<EmployeeDto> registerEmployee(Long projectId, Long employeeId) {
        Project project = findBy(projectId);
        Employee employee = employeeService.findBy(employeeId); // TODO refactor public method to private
        register(project, employee);
        return EmployeeDto.asList(project.getEmployees());
    }

    @Override
    public List<EmployeeDto> unregisterEmployee(Long projectId, Long employeeId) {
        Project project = findBy(projectId);
        Employee employee = employeeService.findBy(employeeId); // TODO refactor public method to private
        unregister(project, employee);
        return EmployeeDto.asList(project.getEmployees());
    }

    private void register(Project project, Employee employee) {
        if(project.getEmployees() == null){
            List<Employee> projectEmployees = new ArrayList<>();
            projectEmployees.add(employee);
            project.setEmployees(projectEmployees);
        }else{
            List<Employee> projectEmployees = project.getEmployees();
            if(employeeWorksOnTheProject(projectEmployees, employee.getId())){
                throw new RuntimeException("The employee alredy works on this project");
            }
            projectEmployees.add(employee);
        }
    }

    private void unregister(Project project, Employee employee) {
        if(project.getEmployees().isEmpty()){
            throw new RuntimeException("This project have no employees to unregister");
        }else{
            List<Employee> projectEmployees = project.getEmployees();
            if(!employeeWorksOnTheProject(projectEmployees, employee.getId())){
                throw new RuntimeException("The employee dont works on this project");
            }
            List<Employee> employeeList = projectEmployees.stream().filter(filteredEmployee -> Objects.equals(filteredEmployee.getId(), employee.getId())).collect(Collectors.toList());
            projectEmployees.remove(employeeList.get(0));
        }
    }

    public Project findBy(Long id){
        Optional<Project> project = projectRepository.findById(id);
        if(!project.isPresent()){
            throw new RuntimeException("Project not found");
        }
        return project.get();
    }

    private boolean employeeWorksOnTheProject(List<Employee> projectEmployees, Long employeeId){
        final List<Employee> employeeInList = projectEmployees.stream().filter(employee -> Objects.equals(employee.getId(), employeeId)).collect(Collectors.toList());
        return !employeeInList.isEmpty();
    }
}
