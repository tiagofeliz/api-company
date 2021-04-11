package br.com.tiagofeliz.company.repository;

import br.com.tiagofeliz.company.model.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
