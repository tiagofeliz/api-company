package br.com.tiagofeliz.company.repository;

import br.com.tiagofeliz.company.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
