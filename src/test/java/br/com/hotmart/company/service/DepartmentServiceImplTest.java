package br.com.hotmart.company.service;

import br.com.hotmart.company.model.dto.DepartmentDto;
import br.com.hotmart.company.model.entity.Department;
import br.com.hotmart.company.repository.DepartmentRepository;
import br.com.hotmart.company.service.impl.DepartmentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

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
    public void shouldReturnEmptyWhenIdIsEnvalid(){
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

}
