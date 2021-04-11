package br.com.tiagofeliz.company.service;

import br.com.tiagofeliz.company.model.dto.BudgetDto;
import br.com.tiagofeliz.company.model.entity.Budget;

import java.util.Optional;

public interface BudgetService {

    Optional<BudgetDto> findById(Long id);
    BudgetDto create(Budget budget);
    BudgetDto update(Budget budget, Long id);
    void delete(Long id);

}
