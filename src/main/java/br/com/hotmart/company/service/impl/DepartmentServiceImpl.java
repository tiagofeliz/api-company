package br.com.hotmart.company.service.impl;

import br.com.hotmart.company.model.dto.DepartmentDto;
import br.com.hotmart.company.model.entity.Department;
import br.com.hotmart.company.repository.DepartmentRepository;
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

    @Override
    public List<DepartmentDto> findAll() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(DepartmentDto::new).collect(Collectors.toList());
    }

    @Override
    public Optional<DepartmentDto> findById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()){
            return Optional.of(new DepartmentDto(department.get()));
        }
        return Optional.empty();
    }

    @Override
    public DepartmentDto create(Department department) {
        return new DepartmentDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentDto update(Department department, Long id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if(departmentOptional.isPresent()){
            save(departmentOptional.get(), department);
            return new DepartmentDto(departmentOptional.get());
        }else{
            throw new RuntimeException("Department not found");
        }
    }

    private void save(Department currentDepartment, Department updateTo){
        currentDepartment.setName(updateTo.getName());
    }

    @Override
    public void delete(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()){
            departmentRepository.delete(department.get());
        }else{
            throw new RuntimeException("Department not found");
        }
    }
}
