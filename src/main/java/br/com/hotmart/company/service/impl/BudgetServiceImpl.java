package br.com.hotmart.company.service.impl;

import br.com.hotmart.company.model.dto.BudgetDto;
import br.com.hotmart.company.model.dto.DepartmentDto;
import br.com.hotmart.company.model.entity.Budget;
import br.com.hotmart.company.model.entity.Department;
import br.com.hotmart.company.repository.BudgetRepository;
import br.com.hotmart.company.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Override
    public Optional<BudgetDto> findById(Long id) {
        Optional<Budget> budget = budgetRepository.findById(id);
        if(budget.isPresent()){
            return Optional.of(new BudgetDto(budget.get()));
        }
        return Optional.empty();
    }

    @Override
    public BudgetDto create(Budget budget) {
        budget.setDepartment(getDepartment(budget.getDepartment().getId()));
        return new BudgetDto(budgetRepository.save(budget));
    }

    private Department getDepartment(Long id) {
        Optional<DepartmentDto> departmentDto = departmentService.findById(id);
        if(!departmentDto.isPresent()){
            throw new RuntimeException("Budget's department not found");
        }
        Department department = departmentDto.get().toEntity();
        return department;
    }

    @Override
    public BudgetDto update(Budget budget, Long id) {
        Optional<Budget> budgetOptional = budgetRepository.findById(id);
        if(budgetOptional.isPresent()) {
            save(budgetOptional.get(), budget);
            return new BudgetDto(budgetOptional.get());
        }else{
            throw new RuntimeException("Budget not found");
        }
    }

    private void save(Budget currentBudget, Budget updateTo) {
        currentBudget.setValue(updateTo.getValue());
        currentBudget.setStartDate(updateTo.getStartDate());
        currentBudget.setEndDate(updateTo.getEndDate());
    }

    @Override
    public void delete(Long id) {
        Optional<Budget> budget = budgetRepository.findById(id);
        if(budget.isPresent()) {
            budgetRepository.delete(budget.get());
        }else{
            throw new RuntimeException("Budget not found");
        }
    }
}
