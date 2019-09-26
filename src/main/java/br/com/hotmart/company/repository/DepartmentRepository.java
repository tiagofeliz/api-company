package br.com.hotmart.company.repository;

import br.com.hotmart.company.model.entity.Department;
import br.com.hotmart.company.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
