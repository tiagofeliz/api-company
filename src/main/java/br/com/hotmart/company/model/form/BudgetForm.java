package br.com.hotmart.company.model.form;

import br.com.hotmart.company.model.entity.Budget;
import br.com.hotmart.company.model.entity.Department;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BudgetForm {

    private double value;
    private LocalDate startDate;
    private LocalDate endDate;
    private Department department;

    private Long idDepartment;

    public Budget toEntity(){
        Budget budget = new Budget();
        budget.setValue(this.value);
        budget.setStartDate(this.startDate);
        budget.setEndDate(this.endDate);
        if(this.idDepartment != null) {
            Department department = new Department();
            department.setId(this.idDepartment);
            budget.setDepartment(department);
        }else{
            throw new IllegalArgumentException("Department id is required");
        }
        return budget;
    }

}
