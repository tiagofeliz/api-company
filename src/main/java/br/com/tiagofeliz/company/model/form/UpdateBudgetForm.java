package br.com.tiagofeliz.company.model.form;

import br.com.tiagofeliz.company.model.entity.Budget;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UpdateBudgetForm {

    private double value;
    private LocalDate startDate;
    private LocalDate endDate;

    public Budget toEntity(){
        Budget budget = new Budget();
        budget.setValue(this.value);
        budget.setStartDate(this.startDate);
        budget.setEndDate(this.endDate);
        return budget;
    }

}
