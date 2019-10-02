package br.com.hotmart.company.repository;

import br.com.hotmart.company.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    List findBySupervisor_Id(Long idSupervisor);
    List findByProjectsDepartmentId(Long departmentId);
    List findByName(String employeeName);
}
