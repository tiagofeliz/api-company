package br.com.hotmart.company.controller;

import br.com.hotmart.company.model.dto.EmployeeDto;
import br.com.hotmart.company.model.dto.ProjectDto;
import br.com.hotmart.company.model.entity.Employee;
import br.com.hotmart.company.model.form.EmployeeForm;
import br.com.hotmart.company.service.impl.EmployeeServiceImpl;
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
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> index(){
        List<EmployeeDto> employees = employeeService.findAll();
        if(employees.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> findByName(@RequestParam String name){
        List<EmployeeDto> employees = employeeService.findByName(name);
        if(employees.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> show(@PathVariable Long id){
        Optional<EmployeeDto> employee = employeeService.findById(id);
        if(!employee.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee.get());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<EmployeeDto> store(@RequestBody @Valid EmployeeForm form, UriComponentsBuilder uriBuilder){
        Employee employee = form.toEntity();
        EmployeeDto dto = employeeService.create(employee);

        URI uri = uriBuilder.path("/employee/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<EmployeeDto> update(@PathVariable Long id, @RequestBody @Valid EmployeeForm form){
        Employee employee = form.toEntity();
        return ResponseEntity.ok(employeeService.update(employee, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/supervisedEmployees")
    public ResponseEntity<List<EmployeeDto>> supervisedEmployees(@PathVariable Long id){
        List<EmployeeDto> employees = employeeService.supervisedEmployees(id);
        if(employees.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}/projects")
    public ResponseEntity<List<ProjectDto>> projects(@PathVariable Long id){
        List<ProjectDto> employeeProjects = employeeService.projects(id);
        if(employeeProjects.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employeeProjects);
    }

}
