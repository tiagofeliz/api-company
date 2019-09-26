package br.com.hotmart.company.service.impl;

import br.com.hotmart.company.model.dto.DepartmentDto;
import br.com.hotmart.company.model.dto.EmployeeDto;
import br.com.hotmart.company.model.entity.Department;
import br.com.hotmart.company.model.entity.Employee;
import br.com.hotmart.company.repository.DepartmentRepository;
import br.com.hotmart.company.repository.EmployeeRepository;
import br.com.hotmart.company.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<DepartmentDto> findAll() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(DepartmentDto::new).collect(Collectors.toList());
    }

    @Override
    public Optional<DepartmentDto> findById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        return department.map(DepartmentDto::new);
    }

    @Override
    public DepartmentDto create(Department department) {
        return new DepartmentDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentDto update(Department department, Long id) {
        Optional<Department> departmentOptional = findBy(id);
        save(departmentOptional.get(), department);
        return new DepartmentDto(departmentOptional.get());
    }

    private void save(Department currentDepartment, Department updateTo){
        currentDepartment.setName(updateTo.getName());
    }

    @Override
    public void delete(Long id) {
        Optional<Department> department = findBy(id);
        departmentRepository.delete(department.get());
    }

    @Override
    public List<EmployeeDto> employees(Long id) {
        Optional<Department> department = findBy(id);
        List<Employee> employees = employeeRepository.findByProjectsDepartment_Id(department.get().getId());
        return employees.stream().map(EmployeeDto::new).collect(Collectors.toList());
    }

    private Optional<Department> findBy(Long id){
        Optional<Department> department = departmentRepository.findById(id);
        if(!department.isPresent()){
            throw new RuntimeException("Department not found");
        }
        return department;
    }
}
