package br.com.hotmart.company.service;

import br.com.hotmart.company.config.exception.ResourceNotFoundException;
import br.com.hotmart.company.model.dto.BudgetStatusDto;
import br.com.hotmart.company.model.dto.DepartmentDto;
import br.com.hotmart.company.model.dto.EmployeeDto;
import br.com.hotmart.company.model.dto.ProjectDto;
import br.com.hotmart.company.model.entity.*;
import br.com.hotmart.company.repository.DepartmentRepository;
import br.com.hotmart.company.repository.EmployeeRepository;
import br.com.hotmart.company.service.impl.DepartmentServiceImpl;
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
public class DepartmentServiceImplTest {

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void shouldReturnAListOfDepartment(){
        List<Department> departments = new ArrayList<>();
        Department department = new Department();
        department.setName("Backoffice");
        departments.add(department);

        Mockito.when(departmentRepository.findAll()).thenReturn(departments);

        List<DepartmentDto> departmentList = departmentService.findAll();

        assertEquals(1, departmentList.size());
        assertEquals(department.getName(), departmentList.get(0).getName());
    }

    @Test
    public void shouldReturnAEmptyListWhenNoAreRecords(){
        List<Department> departments = new ArrayList<>();
        Mockito.when(departmentRepository.findAll()).thenReturn(departments);

        List<DepartmentDto> departmentList = departmentService.findAll();

        assertTrue(departmentList.isEmpty());
    }

    @Test
    public void shouldReturnADepartment(){
        Department department = new Department();
        department.setId(1L);
        department.setName("Backoffice");

        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Optional<DepartmentDto> departmentDto = departmentService.findById(1L);
        assertEquals(department.getId(), departmentDto.get().getId());
        assertEquals(department.getName(), departmentDto.get().getName());
    }

    @Test
    public void shouldReturnEmptyWhenIdIsInvalid(){
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<DepartmentDto> departmentDto = departmentService.findById(1L);

        assertEquals(Optional.empty(), departmentDto);
    }

    @Test
    public void shouldCreateANewDepartment(){
        Department department = new Department();
        department.setName("Backoffice");

        Mockito.when(departmentRepository.save(department)).thenReturn(department);

        DepartmentDto departmentSaved = departmentService.create(department);

        assertEquals(department.getName(), departmentSaved.getName());
    }

    @Test
    public void shouldUpdateADepartment(){
        Department currentDepartment = new Department();
        currentDepartment.setId(1L);
        currentDepartment.setName("Backoffice");

        Department updateTo = new Department();
        updateTo.setName("Volcano");

        Mockito.when(departmentRepository.existsById(1L)).thenReturn(true);
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(currentDepartment));

        DepartmentDto updatedDepartment = departmentService.update(updateTo, 1L);

        assertEquals(updateTo.getName(), updatedDepartment.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAExceptionOnUpdateWhenDepartmentIdIsInvalid(){
        Mockito.when(departmentRepository.existsById(1L)).thenReturn(false);

        departmentService.update(new Department(), 1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAExceptionOnDeleteWhenDepartmentIdIsInvalid(){
        Mockito.when(departmentRepository.existsById(1L)).thenReturn(false);

        departmentService.delete(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAnExceptionWhenDepartmentIdIsInvalid(){
        Mockito.when(departmentRepository.existsById(1L)).thenReturn(false);

        departmentService.employees(1L);
    }

    @Test
    public void shouldReturnAListOfEmployeesThatWorksOnADepartment(){
        Employee employee = new Employee(
                "Tiago Feliz",
                "063.620.145-70",
                LocalDate.of(1996, 5, 30),
                1000.0,
                Gender.MALE,
                new Address(),
                null);
        employee.setId(1L);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Department department = new Department();
        department.setId(1L);

        Project project = new Project();
        project.setId(1L);
        project.setDepartment(department);

        project.setEmployees(employees);

        List<Project> projects = new ArrayList<>();
        projects.add(project);

        department.setProjects(projects);

        Mockito.when(departmentRepository.existsById(1L)).thenReturn(true);
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        Mockito.when(employeeRepository.findByProjectsDepartmentId(1L)).thenReturn(employees);

        List<EmployeeDto> departmentEmployees = departmentService.employees(1L);

        assertEquals(1, departmentEmployees.size());
        assertEquals(employees.get(0).getId(), departmentEmployees.get(0).getId(), 0.00001);
    }

    @Test
    public void shouldReturnAListOfBudgetsWithBudgetStatus(){
        Employee employee = new Employee();
        employee.setId(1L);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Department department = new Department();
        department.setId(1L);

        Budget budget = new Budget();
        budget.setValue(6000);
        budget.setStartDate(LocalDate.of(2019, 5, 1));
        budget.setEndDate(LocalDate.of(2019, 5, 30));
        budget.setDepartment(department);

        List<Budget> budgets = new ArrayList<>();
        budgets.add(budget);

        Project project = new Project();
        project.setId(1L);
        project.setValue(2000);
        project.setName("Similaridade entre textos");
        project.setStartDate(LocalDate.of(2019, 5, 10));
        project.setEndDate(LocalDate.of(2019, 5, 15));
        project.setDepartment(department);
        project.setEmployees(employees);

        List<Project> projects = new ArrayList<>();
        projects.add(project);

        department.setProjects(projects);
        department.setBudgets(budgets);

        Mockito.when(departmentRepository.existsById(1L)).thenReturn(true);
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        List<BudgetStatusDto> budgetStatusList = departmentService.budgetStatus(1L);

        assertEquals(1, budgetStatusList.size());
        assertEquals(BudgetStatus.GREEN, budgetStatusList.get(0).getStatus());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAnExceptionInBudgetListWhenDepartmentIdIsInvalid(){
        Mockito.when(departmentRepository.existsById(1L)).thenReturn(false);

        departmentService.budgetStatus(1L);
    }

    @Test
    public void shouldReturnAEmptyBudgetStatusDtoListWhenDepartmentHasNoBudgets(){
        Department department = new Department();
        department.setId(1L);
        department.setBudgets(new ArrayList<>());
        department.setProjects(new ArrayList<>());

        Mockito.when(departmentRepository.existsById(1L)).thenReturn(true);
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        List<BudgetStatusDto> budgetStatusList = departmentService.budgetStatus(1L);

        assertTrue(budgetStatusList.isEmpty());
    }

    @Test
    public void shouldReturnAGREENStatusWhenDepartmentHasNoProjects(){
        Department department = new Department();
        department.setId(1L);

        Budget budget = new Budget();
        budget.setValue(6000);
        budget.setStartDate(LocalDate.of(2019, 5, 1));
        budget.setEndDate(LocalDate.of(2019, 5, 30));
        budget.setDepartment(department);

        department.setBudgets(Arrays.asList(budget));
        department.setProjects(new ArrayList<>());

        Mockito.when(departmentRepository.existsById(1L)).thenReturn(true);
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        List<BudgetStatusDto> budgetStatusList = departmentService.budgetStatus(1L);

        assertEquals(1, budgetStatusList.size());
        assertEquals(BudgetStatus.GREEN, budgetStatusList.get(0).getStatus());
        assertEquals(budget.getValue(), budgetStatusList.get(0).getValue(), 0.00001);
        assertEquals(budget.getStartDate(), budgetStatusList.get(0).getStartDate());
        assertEquals(budget.getEndDate(), budgetStatusList.get(0).getEndDate());
    }

    @Test
    public void shouldReturnAGREENStatusWhenProjectsValueIsLessThanTheBudgetValue(){
        Department department = new Department();
        department.setId(1L);

        Budget budget = new Budget();
        budget.setValue(6000);
        budget.setStartDate(LocalDate.of(2019, 5, 1));
        budget.setEndDate(LocalDate.of(2019, 5, 30));
        budget.setDepartment(department);

        Project project = new Project();
        project.setValue(2000);
        project.setEmployees(Arrays.asList(new Employee()));
        project.setStartDate(LocalDate.of(2019, 5, 10));
        project.setEndDate(LocalDate.of(2019, 5, 15));

        department.setBudgets(Arrays.asList(budget));
        department.setProjects(Arrays.asList(project));

        Mockito.when(departmentRepository.existsById(1L)).thenReturn(true);
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        List<BudgetStatusDto> budgetStatusList = departmentService.budgetStatus(1L);

        assertEquals(1, budgetStatusList.size());
        assertEquals(BudgetStatus.GREEN, budgetStatusList.get(0).getStatus());
        assertEquals(budget.getValue(), budgetStatusList.get(0).getValue(), 0.00001);
        assertEquals(budget.getStartDate(), budgetStatusList.get(0).getStartDate());
        assertEquals(budget.getEndDate(), budgetStatusList.get(0).getEndDate());
    }

    @Test
    public void shouldReturnAYELLOWStatusWhenProjectsValueIsHigherThanTheBudgetValueAndLessThen10PercentTheBudgetsValue(){
        Department department = new Department();
        department.setId(1L);

        Budget budget = new Budget();
        budget.setValue(6000);
        budget.setStartDate(LocalDate.of(2019, 5, 1));
        budget.setEndDate(LocalDate.of(2019, 5, 30));
        budget.setDepartment(department);

        Project project = new Project();
        project.setValue(6500);
        project.setEmployees(Arrays.asList(new Employee()));
        project.setStartDate(LocalDate.of(2019, 5, 10));
        project.setEndDate(LocalDate.of(2019, 5, 15));

        department.setBudgets(Arrays.asList(budget));
        department.setProjects(Arrays.asList(project));

        Mockito.when(departmentRepository.existsById(1L)).thenReturn(true);
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        List<BudgetStatusDto> budgetStatusList = departmentService.budgetStatus(1L);

        assertEquals(1, budgetStatusList.size());
        assertEquals(BudgetStatus.YELLOW, budgetStatusList.get(0).getStatus());
        assertEquals(budget.getValue(), budgetStatusList.get(0).getValue(), 0.00001);
        assertEquals(budget.getStartDate(), budgetStatusList.get(0).getStartDate());
        assertEquals(budget.getEndDate(), budgetStatusList.get(0).getEndDate());
    }


    @Test
    public void shouldReturnAREDStatusWhenProjectsValueIsHigherThan10PercentTheBudgetsValue(){
        Department department = new Department();
        department.setId(1L);

        Budget budget = new Budget();
        budget.setValue(6000);
        budget.setStartDate(LocalDate.of(2019, 5, 1));
        budget.setEndDate(LocalDate.of(2019, 5, 30));
        budget.setDepartment(department);

        Project project = new Project();
        project.setValue(6700);
        project.setEmployees(Arrays.asList(new Employee()));
        project.setStartDate(LocalDate.of(2019, 5, 10));
        project.setEndDate(LocalDate.of(2019, 5, 15));

        department.setBudgets(Arrays.asList(budget));
        department.setProjects(Arrays.asList(project));

        Mockito.when(departmentRepository.existsById(1L)).thenReturn(true);
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        List<BudgetStatusDto> budgetStatusList = departmentService.budgetStatus(1L);

        assertEquals(1, budgetStatusList.size());
        assertEquals(BudgetStatus.RED, budgetStatusList.get(0).getStatus());
        assertEquals(budget.getValue(), budgetStatusList.get(0).getValue(), 0.00001);
        assertEquals(budget.getStartDate(), budgetStatusList.get(0).getStartDate());
        assertEquals(budget.getEndDate(), budgetStatusList.get(0).getEndDate());
    }

    @Test
    public void shouldReturnAListOfDepartmentProjects(){
        Department department = new Department();
        department.setId(1L);

        Project project = new Project();
        project.setValue(6700);
        project.setEmployees(Arrays.asList(new Employee()));
        project.setStartDate(LocalDate.of(2019, 5, 10));
        project.setEndDate(LocalDate.of(2019, 5, 15));

        department.setProjects(Arrays.asList(project));

        Mockito.when(departmentRepository.existsById(1L)).thenReturn(true);
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        List<ProjectDto> departmentProjects = departmentService.projects(1L);

        assertEquals(1, departmentProjects.size());
        assertEquals(project.getValue(), departmentProjects.get(0).getValue(), 0.000001);
        assertEquals(project.getStartDate(), departmentProjects.get(0).getStartDate());
        assertEquals(project.getEndDate(), departmentProjects.get(0).getEndDate());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldReturnAnExceptionWhenDepartmentIdIsInvalid(){
        Mockito.when(departmentRepository.existsById(1L)).thenReturn(false);

        departmentService.projects(1L);
    }

    @Test
    public void shouldReturnAEmptyListOfDepartmentProjectsWhenDepartmentHasNoProjects(){
        Department department = new Department();
        department.setId(1L);

        department.setProjects(new ArrayList<>());

        Mockito.when(departmentRepository.existsById(1L)).thenReturn(true);
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        List<ProjectDto> departmentProjects = departmentService.projects(1L);

        assertEquals(0, departmentProjects.size());
        assertTrue(departmentProjects.isEmpty());
    }

}
