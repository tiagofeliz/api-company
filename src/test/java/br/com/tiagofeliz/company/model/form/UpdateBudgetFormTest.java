package br.com.tiagofeliz.company.model.form;

import br.com.tiagofeliz.company.model.entity.Budget;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class UpdateBudgetFormTest {

    @Test
    public void shouldReturnAInstanceOfABudget(){
        UpdateBudgetForm form = new UpdateBudgetForm();
        form.setValue(1000);
        form.setStartDate(LocalDate.of(2019, 5, 1));
        form.setEndDate(LocalDate.of(2019, 5, 30));

        Budget budget = form.toEntity();

        assertEquals(form.getValue(), budget.getValue(), 0.00001);
        assertEquals(form.getStartDate(), budget.getStartDate());
        assertEquals(form.getEndDate(), budget.getEndDate());
    }

}
