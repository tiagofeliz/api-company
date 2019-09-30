package br.com.hotmart.company.model.dto;

import br.com.hotmart.company.model.entity.Budget;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BudgetDto {

    private Long id;
    private double value;
    private LocalDate startDate;
    private LocalDate endDate;

    public BudgetDto(Budget budget){
        this.id = budget.getId();
        this.value = budget.getValue();
        this.startDate = budget.getStartDate();
        this.endDate = budget.getEndDate();
    }

}
