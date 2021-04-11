package br.com.tiagofeliz.company.service;

import br.com.tiagofeliz.company.config.exception.ResourceNotFoundException;
import br.com.tiagofeliz.company.model.dto.EmployeeDto;
import br.com.tiagofeliz.company.model.dto.ProjectDto;
import br.com.tiagofeliz.company.model.entity.Address;
import br.com.tiagofeliz.company.model.entity.Employee;
import br.com.tiagofeliz.company.model.entity.Gender;
import br.com.tiagofeliz.company.model.entity.Project;
import br.com.tiagofeliz.company.repository.EmployeeRepository;
import br.com.tiagofeliz.company.repository.ProjectRepository;
import br.com.tiagofeliz.company.service.impl.AddressServiceImpl;
import br.com.tiagofeliz.company.service.impl.EmployeeServiceImpl;
import org.junit.Before;
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
public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AddressServiceImpl addressService;

    private Address address;

    private Employee employee;

    @Before
    public void setup() {
        this.address = new Address(
                "Country",
                "AC",
                "City name",
                "Street name",
                "00000-000");

        this.employee = new Employee(
                "Employee name",
                "000.000.000-00",
                LocalDate.of(1997, 8, 15),
                1000.0,
                Gender.MALE,
                this.address,
                null);
    }

    @Test
    public void shouldReturnAEmptyListWhenThereNoAreRecords(){
        List<Employee> employeeList = new ArrayList<>();
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        List<EmployeeDto> employees = employeeService.findAll();

        assertTrue(employees.isEmpty());
    }

    @Test
    public void shouldReturnAListOfEmployeesWhenThereAreRecords(){
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(this.employee);

        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        List<EmployeeDto> employees = employeeService.findAll();

        assertEquals(1, employees.size());
        assertEquals("Employee name", employees.get(0).getName());
    }

    @Test
    public void shouldReturnAEmployee(){
        this.employee.setId(1L);

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(this.employee));

        Optional<EmployeeDto> newEmployee = employeeService.findById(1L);

        assertEquals(this.employee.getName(), newEmployee.get().getName());
        assertEquals(this.employee.getGender(), newEmployee.get().getGender());
        assertEquals(this.employee.getBirthDate(), newEmployee.get().getBirthDate());
        assertEquals(this.employee.getCpf(), newEmployee.get().getCpf());
        assertEquals(this.employee.getSalary(), newEmployee.get().getSalary(), 0.000001);
    }

    @Test
    public void shouldReturnEmptyIfTheIdIsInvalid(){
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<EmployeeDto> employee = employeeService.findById(1L);

        assertEquals(Optional.empty(), employee);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAExecptionWhenSupervisorIdIsInvalid(){
        Mockito.when(employeeRepository.existsById(1L)).thenReturn(false);

        Employee supervisor = new Employee();
        supervisor.setId(1L);

        this.employee.setSupervisor(supervisor);

        employeeService.create(this.employee);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAExecptionWhenEmployeesOnUpdateIdIsInvalid(){
        Mockito.when(employeeRepository.existsById(1L)).thenReturn(false);

        employeeService.update(this.employee, 1L);
    }

    @Test
    public void shouldUpdateAEmployee(){
        this.employee.setId(1L);

        Employee updateTo = new Employee(
                "New employee name",
                "000.000.000-00",
                LocalDate.of(1997, 8, 15),
                1000.0,
                Gender.MALE,
                this.address,
                null);

        Mockito.when(employeeRepository.existsById(1L)).thenReturn(true);
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(this.employee));

        EmployeeDto updatedEmployee = employeeService.update(updateTo, 1L);

        assertEquals(updateTo.getName(), updatedEmployee.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAExecptionWhenEmployeesOnShowIdIsInvalid(){
        Mockito.when(employeeRepository.existsById(1L)).thenReturn(false);

        employeeService.delete(1L);
    }

    @Test
    public void shouldReturnAListOfSupervisedEmployees(){
        Employee supervisor = new Employee(
                "Employee name",
                "000.000.000-00",
                LocalDate.of(1997, 8, 15),
                1000.0,
                Gender.MALE,
                this.address,
                null);
        supervisor.setId(1L);
        this.employee.setSupervisor(supervisor);

        List<Employee> employees = new ArrayList<>();
        employees.add(this.employee);

        Mockito.when(employeeRepository.findBySupervisor_Id(1L)).thenReturn(employees);

        List<EmployeeDto> supervisedEmployees = employeeService.supervisedEmployees(1L);

        assertEquals(1, supervisedEmployees.size());
        assertEquals(this.employee.getName(), employees.get(0).getName());
        assertEquals(this.employee.getCpf(), employees.get(0).getCpf());
        assertEquals(this.employee.getBirthDate(), employees.get(0).getBirthDate());
        assertEquals(this.employee.getSalary(), employees.get(0).getSalary(), 0.000001);
        assertEquals(supervisor.getId(), employees.get(0).getSupervisor().getId(), 0.000001);
        assertEquals(this.address, employees.get(0).getAddress());
    }

    @Test
    public void shouldReturnAListOfProjectsWhereTheEmployeeWorks(){
        Project project = new Project();
        project.setId(1L);

        this.employee.setId(1L);

        List<Employee> employees = new ArrayList<>();
        employees.add(this.employee);

        project.setEmployees(employees);

        Mockito.when(employeeRepository.existsById(1L)).thenReturn(true);
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(this.employee));

        List<Project> projectList = new ArrayList<>();
        projectList.add(project);

        Mockito.when(projectRepository.findByEmployees_Id(1L)).thenReturn(projectList);

        List<ProjectDto> employeeProjects = employeeService.projects(1L);

        assertEquals(1, employeeProjects.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAnExceptionWhenEmployeesIdIsInvalid(){
        Project project = new Project();
        project.setId(1L);

        this.employee.setId(1L);

        List<Employee> employees = new ArrayList<>();
        employees.add(this.employee);

        project.setEmployees(employees);

        Mockito.when(employeeRepository.existsById(1L)).thenReturn(false);

        employeeService.projects(1L);
    }

    @Test
    public void shouldReturnAListOfEmployeesByName(){

        List<Employee> employeeList = Arrays.asList(this.employee);
        String employeesName = "Employee name";
        Mockito.when(employeeRepository.findByName(employeesName)).thenReturn(employeeList);

        List<EmployeeDto> employeeDtoList = employeeService.findByName(employeesName);

        assertEquals(1, employeeDtoList.size());
        assertEquals(this.employee.getName(), employeeDtoList.get(0).getName());
    }

}
