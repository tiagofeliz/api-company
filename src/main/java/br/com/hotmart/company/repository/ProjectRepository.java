package br.com.hotmart.company.repository;

import br.com.hotmart.company.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByEmployees_Id(Long employeeId);
}
