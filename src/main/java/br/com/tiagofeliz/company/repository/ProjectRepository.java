package br.com.tiagofeliz.company.repository;

import br.com.tiagofeliz.company.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByEmployees_Id(Long employeeId);
}
