package br.com.hotmart.company.service;

import br.com.hotmart.company.model.dto.EmployeeDto;
import br.com.hotmart.company.model.dto.ProjectDto;
import br.com.hotmart.company.model.entity.Address;
import br.com.hotmart.company.model.entity.Employee;
import br.com.hotmart.company.model.entity.Gender;
import br.com.hotmart.company.model.entity.Project;
import br.com.hotmart.company.repository.EmployeeRepository;
import br.com.hotmart.company.repository.ProjectRepository;
import br.com.hotmart.company.service.impl.AddressServiceImpl;
import br.com.hotmart.company.service.impl.EmployeeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AddressServiceImpl addressService;

    @Mock
    private ProjectRepository projectRepository;

    @Test
    public void shouldReturnAEmptyListWhenThereNoAreRecords(){
        List<Employee> employeeList = new ArrayList<>();
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        List<EmployeeDto> employees = employeeService.findAll();

        assertTrue(employees.isEmpty());
    }

    @Test
    public void shouldReturnAListOfEmployeesWhenThereAreRecords(){
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

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        List<EmployeeDto> employees = employeeService.findAll();

        assertEquals(1, employees.size());
        assertEquals("Tiago Feliz", employees.get(0).getName());
    }

    @Test
    public void shouldReturnAEmployee(){
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

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<EmployeeDto> newEmployee = employeeService.findById(1L);

        assertEquals(employee.getName(), newEmployee.get().getName());
    }

    @Test
    public void shouldReturnEmptyIfTheIdIsInvalid(){
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<EmployeeDto> employee = employeeService.findById(1L);

        assertEquals(Optional.empty(), employee);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAExecptionWhenSupervisorIdIsInvalid(){
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Employee supervisor = new Employee();
        supervisor.setId(1L);

        Employee employee = new Employee();
        employee.setSupervisor(supervisor);

        employeeService.create(employee);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAExecptionWhenEmployeesOnUpdateIdIsInvalid(){
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Employee employee = new Employee();

        employeeService.update(employee, 1L);
    }

    @Test
    public void shouldUpdateAEmployee(){
        Address address = new Address(
                "Brasil",
                "MG",
                "BH",
                "Entre Rios",
                "30710-080");

        Employee currentEmployee = new Employee(
                "Tiago Feliz",
                "063.620.145-70",
                LocalDate.of(1996, 5, 30),
                1000.0,
                Gender.MALE,
                address,
                null);
        currentEmployee.setId(1L);

        Employee updateTo = new Employee(
                "Tiago Triste",
                "063.620.145-70",
                LocalDate.of(1996, 5, 30),
                1000.0,
                Gender.MALE,
                address,
                null);

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(currentEmployee));

        EmployeeDto updatedEmployee = employeeService.update(updateTo, 1L);

        assertEquals(updateTo.getName(), updatedEmployee.getName());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAExecptionWhenEmployeesOnShowIdIsInvalid(){
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        employeeService.delete(1L);
    }

    @Test
    public void shouldReturnAListOfSupervisedEmployees(){
        Address address = new Address(
                "Brasil",
                "MG",
                "BH",
                "Entre Rios",
                "30710-080");

        Employee supervisor = new Employee(
                "Tiago Feliz",
                "063.620.145-70",
                LocalDate.of(1996, 5, 30),
                1000.0,
                Gender.MALE,
                address,
                null);
        supervisor.setId(1L);

        Employee employee = new Employee(
                "Tiago Feliz",
                "063.620.145-70",
                LocalDate.of(1996, 5, 30),
                1000.0,
                Gender.MALE,
                address,
                supervisor);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Mockito.when(employeeRepository.findBySupervisor_Id(1L)).thenReturn(employees);

        List<EmployeeDto> supervisedEmployees = employeeService.supervisedEmployees(1L);

        assertEquals(1, supervisedEmployees.size());
        assertEquals(employee.getName(), employees.get(0).getName());
        assertEquals(employee.getCpf(), employees.get(0).getCpf());
        assertEquals(employee.getBirthDate(), employees.get(0).getBirthDate());
        assertEquals(employee.getSalary(), employees.get(0).getSalary(), 0.000001);
        assertEquals(supervisor.getId(), employees.get(0).getSupervisor().getId(), 0.000001);
        assertEquals(address, employees.get(0).getAddress());
    }

    @Test
    public void shouldReturnAListOfProjectsWhereTheEmployeeWorks(){
        Project project = new Project();
        project.setId(1L);

        Employee employee = new Employee();
        employee.setId(1L);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        project.setEmployees(employees);

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        List<Project> projectList = new ArrayList<>();
        projectList.add(project);

        Mockito.when(projectRepository.findByEmployees_Id(1L)).thenReturn(projectList);

        List<ProjectDto> employeeProjects = employeeService.projects(1L);

        assertEquals(1, employeeProjects.size());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAnExceptionWhenEmployeesIdIsInvalid(){
        Project project = new Project();
        project.setId(1L);

        Employee employee = new Employee();
        employee.setId(1L);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        project.setEmployees(employees);

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        employeeService.projects(1L);
    }

}
