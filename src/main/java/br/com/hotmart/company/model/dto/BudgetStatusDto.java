package br.com.hotmart.company.model.dto;

import br.com.hotmart.company.model.entity.Budget;
import br.com.hotmart.company.model.entity.BudgetStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BudgetStatusDto {

    private Long id;
    private double value;
    private LocalDate startDate;
    private LocalDate endDate;
    private BudgetStatus status;

    public BudgetStatusDto(Budget budget, BudgetStatus status){
        this.id = budget.getId();
        this.value = budget.getValue();
        this.startDate = budget.getStartDate();
        this.endDate = budget.getEndDate();
        this.status = status;
    }

}
