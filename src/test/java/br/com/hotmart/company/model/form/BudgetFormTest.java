package br.com.hotmart.company.model.form;

import br.com.hotmart.company.model.entity.Budget;
import br.com.hotmart.company.model.entity.Department;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class BudgetFormTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAExceptionIfDepartmentIdIsNotPresent(){
        BudgetForm form = new BudgetForm();
        form.setValue(1000);
        form.setStartDate(LocalDate.of(1996, 5, 30));
        form.setEndDate(LocalDate.of(1996, 6, 30));

        form.toEntity();
    }

    @Test
    public void shouldCreateADepartmentWhenDepartmentIdIsNotNull(){
        BudgetForm form = new BudgetForm();
        form.setValue(1000);
        form.setStartDate(LocalDate.of(1996, 5, 30));
        form.setEndDate(LocalDate.of(1996, 6, 30));
        form.setIdDepartment(1L);

        Budget budget = form.toEntity();

        assertEquals(1L, budget.getDepartment().getId(), 0.00001);
        assertNotNull(budget.getDepartment());
    }

    @Test
    public void shouldReturnAInstanceOfBudget(){
        BudgetForm form = new BudgetForm();
        form.setValue(1000);
        form.setStartDate(LocalDate.of(1996, 5, 30));
        form.setEndDate(LocalDate.of(1996, 6, 30));
        form.setIdDepartment(1L);

        Department department = new Department();
        department.setId(1L);

        Budget budget = form.toEntity();
        assertNotNull(budget);
        assertEquals(form.getValue(), budget.getValue(), 0.00001);
        assertEquals(form.getEndDate(), budget.getEndDate());
        assertEquals(form.getStartDate(), budget.getStartDate());
        assertEquals(form.getIdDepartment(), budget.getDepartment().getId(), 0.00001);
        assertNotNull(budget.getDepartment());
    }

}
