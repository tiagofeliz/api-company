package br.com.hotmart.company.service;

import br.com.hotmart.company.model.dto.BudgetDto;
import br.com.hotmart.company.model.entity.Budget;

import java.util.List;
import java.util.Optional;

public interface BudgetService {

    Optional<BudgetDto> findById(Long id);
    BudgetDto create(Budget budget);
    BudgetDto update(Budget budget, Long id);
    void delete(Long id);

}
