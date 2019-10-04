package br.com.hotmart.company.service;

import br.com.hotmart.company.config.exception.ResourceNotFoundException;
import br.com.hotmart.company.config.exception.UnprocessableEntityException;
import br.com.hotmart.company.model.dto.DepartmentDto;
import br.com.hotmart.company.model.dto.EmployeeDto;
import br.com.hotmart.company.model.dto.ProjectDto;
import br.com.hotmart.company.model.entity.*;
import br.com.hotmart.company.repository.ProjectRepository;
import br.com.hotmart.company.service.impl.DepartmentServiceImpl;
import br.com.hotmart.company.service.impl.EmployeeServiceImpl;
import br.com.hotmart.company.service.impl.ProjectServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeServiceImpl employeeService;

    @Mock
    private DepartmentServiceImpl departmentService;

    @Test
    public void shouldReturnAListOfProject(){
        List<Project> projects = new ArrayList<>();
        Project project = new Project();
        project.setName("Revisão de produtos");
        project.setValue(1700.0);
        project.setStartDate(LocalDate.of(2019, 9, 1));
        project.setEndDate(LocalDate.of(2019, 9, 30));

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());

        project.setEmployees(employees);

        projects.add(project);

        Mockito.when(projectRepository.findAll()).thenReturn(projects);

        List<ProjectDto> projectList = projectService.findAll();

        assertEquals(1, projectList.size());
        assertEquals(project.getName(), projectList.get(0).getName());
    }

    @Test
    public void shouldReturnAEmptyListWhenNoAreRecords(){
        List<Project> projects = new ArrayList<>();
        Mockito.when(projectRepository.findAll()).thenReturn(projects);

        List<ProjectDto> projectDtoList = projectService.findAll();

        assertTrue(projectDtoList.isEmpty());
    }

    @Test
    public void shouldReturnAProject(){
        Project project = new Project();
        project.setId(1L);
        project.setName("Revisão de produtos");
        project.setValue(1700.0);
        project.setStartDate(LocalDate.of(2019, 9, 1));
        project.setEndDate(LocalDate.of(2019, 9, 30));

        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Optional<ProjectDto> projectDto = projectService.findById(1L);

        assertEquals(project.getId(), projectDto.get().getId());
        assertEquals(project.getName(), projectDto.get().getName());
    }

    @Test
    public void shouldReturnEmptyWhenIdIsInvalid(){
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ProjectDto> projectDto = projectService.findById(1L);

        assertEquals(Optional.empty(), projectDto);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAnExceptionWhenDepartmentIdIsInvalid(){
        Project project = new Project();

        Mockito.when(departmentService.findById(1L)).thenReturn(Optional.empty());

        projectService.create(project, 1L);
    }

    @Test
    public void shouldCreateANewProject(){
        Department department = new Department();
        department.setId(1L);

        Project project = new Project();
        project.setName("Revisão de produtos");
        project.setValue(1700.0);
        project.setStartDate(LocalDate.of(2019, 9, 1));
        project.setEndDate(LocalDate.of(2019, 9, 30));
        project.setDepartment(department);

        Mockito.when(departmentService.findById(1L)).thenReturn(Optional.of(new DepartmentDto(department)));

        Mockito.when(projectRepository.save(project)).thenReturn(project);

        ProjectDto projectSaved = projectService.create(project, 1L);

        assertEquals(project.getName(), projectSaved.getName());
        assertEquals(project.getValue(), projectSaved.getValue(), 0.00001);
        assertEquals(project.getStartDate(), projectSaved.getStartDate());
        assertEquals(project.getEndDate(), projectSaved.getEndDate());
    }

    @Test
    public void shouldUpdateAProject(){
        Project currentProject = new Project();
        currentProject.setName("Revisão de produtos");

        Project updateTo = new Project();
        updateTo.setName("Similaridade entre textos");

        Mockito.when(projectRepository.existsById(1L)).thenReturn(true);
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(currentProject));

        ProjectDto updatedDepartment = projectService.update(updateTo, 1L);

        assertEquals(updateTo.getName(), updatedDepartment.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAExceptionOnUpdateWhenProjectIdIsInvalid(){
        Mockito.when(projectRepository.existsById(1L)).thenReturn(false);

        projectService.update(new Project(), 1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAExceptionOnDeleteWhenProjectIdIsInvalid(){
        Mockito.when(projectRepository.existsById(1L)).thenReturn(false);

        projectService.delete(1L);
    }

    @Test
    public void shouldRegisterAEmployeeInAProjectWhenProjectEmployeListIsNull(){
        Project project = new Project();
        project.setId(1L);
        project.setName("Revisão de produtos");
        project.setValue(1700.0);
        project.setStartDate(LocalDate.of(2019, 9, 1));
        project.setEndDate(LocalDate.of(2019, 9, 30));

        Address address = new Address(
                "Brasil",
                "MG",
                "BH",
                "Entre Rios",
                "30710-080");

        Employee employee = new Employee(
                "Tiago Feliz",
                "063.620.145-70",
                LocalDate.of(1996, 5, 30),
                1000.0,
                Gender.MALE,
                address,
                null);
        employee.setId(1L);

        Mockito.when(projectRepository.existsById(1L)).thenReturn(true);
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Mockito.when(employeeService.findBy(1L)).thenReturn(employee);
        Mockito.when(employeeService.exists(1L)).thenReturn(true);

        Long projectId = 1L;
        Long employeeId = 1L;
        List<EmployeeDto> employees = projectService.registerEmployee(projectId, employeeId);

        assertEquals(1, employees.size());
        assertEquals(employee.getId(), employees.get(0).getId(), 0.00001);
        assertEquals(employee.getName(), employees.get(0).getName());
        assertEquals(employee.getCpf(), employees.get(0).getCpf());
        assertEquals(employee.getBirthDate(), employees.get(0).getBirthDate());
        assertEquals(employee.getGender(), employees.get(0).getGender());
    }

    @Test
    public void shouldRegisterAEmployeeInAProjectWhenProjectEmployeListIsNotNull(){
        Address address = new Address(
                "Brasil",
                "MG",
                "BH",
                "Entre Rios",
                "30710-080");

        Project project = new Project();
        project.setId(1L);
        project.setName("Revisão de produtos");
        project.setValue(1700.0);
        project.setStartDate(LocalDate.of(2019, 9, 1));
        project.setEndDate(LocalDate.of(2019, 9, 30));

        List<Employee> projectEmployees = new ArrayList<>();
        projectEmployees.add(new Employee(
                "Capitu Capitolina",
                "027.715.512-70",
                LocalDate.of(1996, 5, 30),
                1000.0,
                Gender.FEMALE,
                address,
                null));

        project.setEmployees(projectEmployees);

        Employee employee = new Employee(
                "Tiago Feliz",
                "063.620.145-70",
                LocalDate.of(1996, 5, 30),
                1000.0,
                Gender.MALE,
                address,
                null);
        employee.setId(1L);

        Mockito.when(projectRepository.existsById(1L)).thenReturn(true);
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Mockito.when(employeeService.exists(1L)).thenReturn(true);
        Mockito.when(employeeService.findBy(1L)).thenReturn(employee);

        Long projectId = 1L;
        Long employeeId = 1L;
        List<EmployeeDto> employees = projectService.registerEmployee(projectId, employeeId);

        assertEquals(2, employees.size());
        assertEquals(employee.getId(), employees.get(1).getId(), 0.00001);
        assertEquals(employee.getName(), employees.get(1).getName());
        assertEquals(employee.getCpf(), employees.get(1).getCpf());
        assertEquals(employee.getBirthDate(), employees.get(1).getBirthDate());
        assertEquals(employee.getGender(), employees.get(1).getGender());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAExceptionWhenProjectIdIsInvalid(){
        Mockito.when(projectRepository.existsById(1L)).thenReturn(false);

        Long projectId = 1L;
        Long employeeId = 1L;
        projectService.registerEmployee(projectId, employeeId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAExceptionWhenEmployeeIdIsInvalid(){
        Project project = new Project();
        project.setId(1L);

        Mockito.when(projectRepository.existsById(1L)).thenReturn(true);
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Mockito.when(employeeService.exists(1L)).thenReturn(false);

        Long projectId = 1L;
        Long employeeId = 1L;
        projectService.registerEmployee(projectId, employeeId);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void shouldThrowAnExceptionWhenEmployeeAlredyWorksInTheProject(){
        Project project = new Project();
        project.setId(1L);

        Employee employee = new Employee();
        employee.setId(1L);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        project.setEmployees(employees);

        Mockito.when(projectRepository.existsById(1L)).thenReturn(true);
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Mockito.when(employeeService.exists(1L)).thenReturn(true);
        Mockito.when(employeeService.findBy(1L)).thenReturn(employee);

        Long projectId = 1L;
        Long employeeId = 1L;
        projectService.registerEmployee(projectId, employeeId);
    }

    @Test
    public void shouldUnregisterAEmployeeFromAProject(){
        Project project = new Project();
        project.setId(1L);

        Employee employee = new Employee();
        employee.setId(1L);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        project.setEmployees(employees);

        Mockito.when(projectRepository.existsById(1L)).thenReturn(true);
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Mockito.when(employeeService.findBy(1L)).thenReturn(employee);

        Long projectId = 1L;
        Long employeeId = 1L;
        List<EmployeeDto> projectEmployees = projectService.unregisterEmployee(projectId, employeeId);

        assertTrue(projectEmployees.isEmpty());
    }

    @Test(expected = UnprocessableEntityException.class)
    public void shouldThrowAnExceptionWhenProjectHasNoEmployees(){
        Project project = new Project();
        project.setId(1L);
        project.setEmployees(new ArrayList<>());

        Mockito.when(projectRepository.existsById(1L)).thenReturn(true);
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Long projectId = 1L;
        Long employeeId = 1L;
        projectService.unregisterEmployee(projectId, employeeId);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void shouldThrowAnExceptionWhenEmployeeDontWorksInTheProject(){
        Project project = new Project();
        project.setId(1L);

        Employee employee = new Employee();
        employee.setId(1L);

        Employee employeeToBeFounded = new Employee();
        employee.setId(2L);

        project.setEmployees(Arrays.asList(employee));

        Mockito.when(projectRepository.existsById(1L)).thenReturn(true);
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Mockito.when(employeeService.findBy(1L)).thenReturn(employeeToBeFounded);

        Long projectId = 1L;
        Long employeeId = 1L;
        projectService.unregisterEmployee(projectId, employeeId);
    }

}
