package br.com.tiagofeliz.company.service.impl;

import br.com.tiagofeliz.company.config.exception.ResourceNotFoundException;
import br.com.tiagofeliz.company.config.exception.UnprocessableEntityException;
import br.com.tiagofeliz.company.model.dto.DepartmentDto;
import br.com.tiagofeliz.company.model.dto.EmployeeDto;
import br.com.tiagofeliz.company.model.dto.ProjectDto;
import br.com.tiagofeliz.company.model.entity.Department;
import br.com.tiagofeliz.company.model.entity.Employee;
import br.com.tiagofeliz.company.model.entity.Project;
import br.com.tiagofeliz.company.repository.ProjectRepository;
import br.com.tiagofeliz.company.service.ProjectService;
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
            throw new ResourceNotFoundException("Project's department not found");
        }
        return departmentDto.get().toEntity();
    }

    @Override
    public ProjectDto update(Project updateTo, Long id) {
        if(!exists(id)){
            throw new ResourceNotFoundException("Project not found");
        }
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
        if(!exists(id)){
            throw new ResourceNotFoundException("Project not found");
        }
        Project project = findBy(id);
        projectRepository.delete(project);
    }

    @Override
    public List<EmployeeDto> registerEmployee(Long projectId, Long employeeId) {
        if(!exists(projectId)){
            throw new ResourceNotFoundException("Project not found");
        }
        Project project = findBy(projectId);
        Employee employee = getEmployee(employeeId);
        register(project, employee);
        return EmployeeDto.asList(project.getEmployees());
    }

    private Employee getEmployee(Long employeeId) {
        if(!employeeService.exists(employeeId)){
            throw new ResourceNotFoundException("Employee not found");
        }
        return employeeService.findBy(employeeId);
    }

    @Override
    public List<EmployeeDto> unregisterEmployee(Long projectId, Long employeeId) {
        if(!exists(projectId)){
            throw new ResourceNotFoundException("Project not found");
        }
        Project project = findBy(projectId);
        Employee employee = employeeService.findBy(employeeId);
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
                throw new UnprocessableEntityException("The employee alredy works on this project");
            }
            projectEmployees.add(employee);
        }
    }

    private void unregister(Project project, Employee employee) {
        if(project.getEmployees().isEmpty()){
            throw new UnprocessableEntityException("This project have no employees to unregister");
        }else{
            List<Employee> projectEmployees = project.getEmployees();
            if(!employeeWorksOnTheProject(projectEmployees, employee.getId())){
                throw new UnprocessableEntityException("The employee dont works on this project");
            }
            List<Employee> employeeList = projectEmployees.stream().filter(filteredEmployee -> Objects.equals(filteredEmployee.getId(), employee.getId())).collect(Collectors.toList());
            projectEmployees.remove(employeeList.get(0));
        }
    }

    public boolean exists(Long id){
        return projectRepository.existsById(id);
    }

    public Project findBy(Long id){
        return projectRepository.findById(id).get();
    }

    private boolean employeeWorksOnTheProject(List<Employee> projectEmployees, Long employeeId){
        final List<Employee> employeeInList = projectEmployees.stream().filter(employee -> Objects.equals(employee.getId(), employeeId)).collect(Collectors.toList());
        return !employeeInList.isEmpty();
    }
}
