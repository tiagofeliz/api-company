package br.com.tiagofeliz.company.service.impl;

import br.com.tiagofeliz.company.config.exception.ResourceNotFoundException;
import br.com.tiagofeliz.company.model.dto.BudgetDto;
import br.com.tiagofeliz.company.model.dto.DepartmentDto;
import br.com.tiagofeliz.company.model.entity.Budget;
import br.com.tiagofeliz.company.model.entity.Department;
import br.com.tiagofeliz.company.repository.BudgetRepository;
import br.com.tiagofeliz.company.service.BudgetService;
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
        return budget.map(BudgetDto::new);
    }

    @Override
    public BudgetDto create(Budget budget) {
        budget.setDepartment(getDepartment(budget.getDepartment().getId()));
        return new BudgetDto(budgetRepository.save(budget));
    }

    private Department getDepartment(Long id) {
        Optional<DepartmentDto> departmentDto = departmentService.findById(id);
        if(!departmentDto.isPresent()){
            throw new ResourceNotFoundException("Budget's department not found");
        }
        return departmentDto.get().toEntity();
    }

    @Override
    public BudgetDto update(Budget updateTo, Long id) {
        if(!exists(id)){
            throw new ResourceNotFoundException("Budget not found");
        }
        Budget budget = findBy(id);
        save(budget, updateTo);
        return new BudgetDto(budget);
    }

    private void save(Budget currentBudget, Budget updateTo) {
        currentBudget.setValue(updateTo.getValue());
        currentBudget.setStartDate(updateTo.getStartDate());
        currentBudget.setEndDate(updateTo.getEndDate());
    }

    @Override
    public void delete(Long id) {
        if(!exists(id)){
            throw new ResourceNotFoundException("Budget not found");
        }
        Budget budget = findBy(id);
        budgetRepository.delete(budget);
    }

    private boolean exists(Long id){
        return budgetRepository.existsById(id);
    }

    private Budget findBy(Long id){
        return budgetRepository.findById(id).get();
    }
}
