package br.com.hotmart.company.repository;

import br.com.hotmart.company.model.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
