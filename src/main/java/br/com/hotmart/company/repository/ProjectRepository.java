package br.com.hotmart.company.repository;

import br.com.hotmart.company.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
