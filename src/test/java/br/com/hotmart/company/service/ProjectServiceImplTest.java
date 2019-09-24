package br.com.hotmart.company.service;

import br.com.hotmart.company.model.dto.ProjectDto;
import br.com.hotmart.company.model.entity.Employee;
import br.com.hotmart.company.model.entity.Project;
import br.com.hotmart.company.repository.ProjectRepository;
import br.com.hotmart.company.service.impl.ProjectServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Test
    public void shouldReturnAListOfProject(){
        List<Project> projects = new ArrayList<>();
        Project project = new Project();
        project.setName("Revisão de produtos");
        project.setValue(1700.0);
        project.setStartDate(LocalDate.of(2019, 9, 1));
        project.setEndDate(LocalDate.of(2019, 9, 30));

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());

        project.setEmployees(employees);

        projects.add(project);

        Mockito.when(projectRepository.findAll()).thenReturn(projects);

        List<ProjectDto> projectList = projectService.findAll();

        assertEquals(1, projectList.size());
        assertEquals(project.getName(), projectList.get(0).getName());
    }

    @Test
    public void shouldReturnAEmptyListWhenNoAreRecords(){
        List<Project> projects = new ArrayList<>();
        Mockito.when(projectRepository.findAll()).thenReturn(projects);

        List<ProjectDto> projectDtoList = projectService.findAll();

        assertTrue(projectDtoList.isEmpty());
    }

    @Test
    public void shouldReturnAProject(){
        Project project = new Project();
        project.setId(1L);
        project.setName("Revisão de produtos");
        project.setValue(1700.0);
        project.setStartDate(LocalDate.of(2019, 9, 1));
        project.setEndDate(LocalDate.of(2019, 9, 30));

        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Optional<ProjectDto> projectDto = projectService.findById(1L);

        assertEquals(project.getId(), projectDto.get().getId());
        assertEquals(project.getName(), projectDto.get().getName());
    }

    @Test
    public void shouldReturnEmptyWhenIdIsInvalid(){
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ProjectDto> projectDto = projectService.findById(1L);

        assertEquals(Optional.empty(), projectDto);
    }

    @Test
    public void shouldCreateANewProject(){
        Project project = new Project();
        project.setName("Revisão de produtos");
        project.setValue(1700.0);
        project.setStartDate(LocalDate.of(2019, 9, 1));
        project.setEndDate(LocalDate.of(2019, 9, 30));

        Mockito.when(projectRepository.save(project)).thenReturn(project);

        ProjectDto projectSaved = projectService.create(project);

        assertEquals(project.getName(), projectSaved.getName());
    }

    @Test
    public void shouldUpdateAProject(){
        Project currentProject = new Project();
        currentProject.setName("Revisão de produtos");

        Project updateTo = new Project();
        updateTo.setName("Similaridade entre textos");

        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(currentProject));

        ProjectDto updatedDepartment = projectService.update(updateTo, 1L);

        assertEquals(updateTo.getName(), updatedDepartment.getName());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAExceptionOnUpdateWhenProjectIdIsInvalid(){
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        projectService.update(new Project(), 1L);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAExceptionOnDeleteWhenProjectIdIsInvalid(){
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        projectService.delete(1L);
    }

}
