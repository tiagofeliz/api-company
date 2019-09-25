package br.com.hotmart.company.service.impl;

import br.com.hotmart.company.model.dto.ProjectDto;
import br.com.hotmart.company.model.entity.Project;
import br.com.hotmart.company.repository.ProjectRepository;
import br.com.hotmart.company.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<ProjectDto> findAll() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(ProjectDto::new).collect(Collectors.toList());
    }

    @Override
    public Optional<ProjectDto> findById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(ProjectDto::new);
    }

    @Override
    public ProjectDto create(Project project) {
        return new ProjectDto(projectRepository.save(project));
    }

    @Override
    public ProjectDto update(Project project, Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if(projectOptional.isPresent()){
            save(projectOptional.get(), project);
            return new ProjectDto(projectOptional.get());
        }else{
            throw new RuntimeException("Project not found");
        }
    }

    private void save(Project currentProject, Project updateTo) {
        currentProject.setName(updateTo.getName());
        currentProject.setValue(updateTo.getValue());
        currentProject.setStartDate(updateTo.getStartDate());
        currentProject.setEndDate(updateTo.getEndDate());
    }

    @Override
    public void delete(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()){
            projectRepository.delete(project.get());
        }else{
            throw new RuntimeException("Project not found");
        }
    }
}
