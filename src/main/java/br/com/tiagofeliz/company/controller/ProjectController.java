package br.com.tiagofeliz.company.controller;

import br.com.tiagofeliz.company.model.dto.EmployeeDto;
import br.com.tiagofeliz.company.model.dto.ProjectDto;
import br.com.tiagofeliz.company.model.entity.Project;
import br.com.tiagofeliz.company.model.form.ProjectForm;
import br.com.tiagofeliz.company.model.form.UpdateProjectForm;
import br.com.tiagofeliz.company.service.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDto>> index(){
        List<ProjectDto> projects = projectService.findAll();
        if(projects.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> show(@PathVariable Long id){
        Optional<ProjectDto> project = projectService.findById(id);
        if(!project.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project.get());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProjectDto> store(@RequestBody @Valid ProjectForm form, UriComponentsBuilder uriBuilder){
        Project project = form.toEntity();
        ProjectDto dto = projectService.create(project, form.getIdDepartment());

        URI uri = uriBuilder.path("/employee/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProjectDto> update(@PathVariable Long id, @RequestBody @Valid UpdateProjectForm form){
        Project project = form.toEntity();
        return ResponseEntity.ok(projectService.update(project, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        projectService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/registerEmployee/{employeeId}")
    @Transactional
    public ResponseEntity<List<EmployeeDto>> registerEmployee(@PathVariable Long id, @PathVariable Long employeeId){
        List<EmployeeDto> projectEmployees = projectService.registerEmployee(id, employeeId);
        if(projectEmployees.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(projectEmployees);
    }

    @GetMapping("/{id}/unregisterEmployee/{employeeId}")
    @Transactional
    public ResponseEntity<List<EmployeeDto>> unregisterEmployee(@PathVariable Long id, @PathVariable Long employeeId){
        List<EmployeeDto> projectEmployees = projectService.unregisterEmployee(id, employeeId);
        if(projectEmployees.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(projectEmployees);
    }

}
