package br.com.hotmart.company.service;

import br.com.hotmart.company.config.exception.ResourceNotFoundException;
import br.com.hotmart.company.model.dto.BudgetDto;
import br.com.hotmart.company.model.dto.DepartmentDto;
import br.com.hotmart.company.model.entity.Budget;
import br.com.hotmart.company.model.entity.Department;
import br.com.hotmart.company.repository.BudgetRepository;
import br.com.hotmart.company.service.impl.BudgetServiceImpl;
import br.com.hotmart.company.service.impl.DepartmentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BudgetServiceImplTest {

    @InjectMocks
    private BudgetServiceImpl budgetService;

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private DepartmentServiceImpl departmentService;

    @Test
    public void shouldReturnABudget(){
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setValue(5000);
        budget.setStartDate(LocalDate.of(2019, 9, 1));
        budget.setEndDate(LocalDate.of(2019, 9, 30));

        Mockito.when(budgetRepository.findById(1L)).thenReturn(Optional.of(budget));

        Optional<BudgetDto> budgetDto = budgetService.findById(1L);

        assertEquals(budget.getId(), budgetDto.get().getId());
        assertEquals(budget.getValue(), budgetDto.get().getValue(), 0.00001);
    }

    @Test
    public void shouldReturnEmptyWhenIdIsInvalid(){
        Mockito.when(budgetRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<BudgetDto> budgetDto = budgetService.findById(1L);

        assertEquals(Optional.empty(), budgetDto);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAnExceptionWhenDepartmentIdIsInvalid(){
        Department department = new Department();
        department.setId(1L);

        Budget budget = new Budget();
        budget.setDepartment(department);

        Mockito.when(departmentService.findById(1L)).thenReturn(Optional.empty());

        budgetService.create(budget);
    }

    @Test
    public void shouldCreateANewBudget(){
        Department department = new Department();
        department.setId(1L);

        Budget budget = new Budget();
        budget.setValue(1500);
        budget.setDepartment(department);

        Mockito.when(departmentService.findById(1L)).thenReturn(Optional.of(new DepartmentDto(department)));

        Mockito.when(budgetRepository.save(budget)).thenReturn(budget);

        BudgetDto budgetSaved = budgetService.create(budget);

        assertEquals(budget.getValue(), budgetSaved.getValue(), 0.00001);
    }

    @Test
    public void shouldUpdateABudget(){
        Budget currentBudget = new Budget();
        currentBudget.setValue(1500);

        Budget updateTo = new Budget();
        updateTo.setValue(2000);

        Mockito.when(budgetRepository.existsById(1L)).thenReturn(true);
        Mockito.when(budgetRepository.findById(1L)).thenReturn(Optional.of(currentBudget));

        BudgetDto updatedBudget = budgetService.update(updateTo, 1L);

        assertEquals(updateTo.getValue(), updatedBudget.getValue(), 0.00001);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAExceptionOnUpdateWhenBudgetIdIsInvalid(){
        Mockito.when(budgetRepository.existsById(1L)).thenReturn(false);

        budgetService.update(new Budget(), 1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAExceptionOnDeleteWhenBudgetIdIsInvalid(){
        Mockito.when(budgetRepository.existsById(1L)).thenReturn(false);

        budgetService.delete(1L);
    }

}
