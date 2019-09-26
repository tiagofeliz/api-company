package br.com.hotmart.company.service;

import br.com.hotmart.company.model.dto.DepartmentDto;
import br.com.hotmart.company.model.dto.EmployeeDto;
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

        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(currentDepartment));

        DepartmentDto updatedDepartment = departmentService.update(updateTo, 1L);

        assertEquals(updateTo.getName(), updatedDepartment.getName());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAExceptionOnUpdateWhenDepartmentIdIsInvalid(){
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        departmentService.update(new Department(), 1L);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAExceptionOnDeleteWhenDepartmentIdIsInvalid(){
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        departmentService.delete(1L);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAnExceptionWhenDepartmentIdIsInvalid(){
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

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

        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        Mockito.when(employeeRepository.findByProjectsDepartment_Id(1L)).thenReturn(employees);

        List<EmployeeDto> departmentEmployees = departmentService.employees(1L);

        assertEquals(1, departmentEmployees.size());
        assertEquals(employees.get(0).getId(), departmentEmployees.get(0).getId(), 0.00001);
    }

}
