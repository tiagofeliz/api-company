package br.com.hotmart.company.model.form;

import org.junit.Test;

import java.time.LocalDate;

public class BudgetFormTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAExceptionIfDepartmentIdIsNotPresent(){
        BudgetForm form = new BudgetForm();
        form.setValue(1000);
        form.setStartDate(LocalDate.of(1996, 5, 30));
        form.setEndDate(LocalDate.of(1996, 6, 30));

        form.toEntity();
    }

}
