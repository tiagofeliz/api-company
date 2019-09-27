package br.com.hotmart.company.service.impl;

import br.com.hotmart.company.model.dto.BudgetStatusDto;
import br.com.hotmart.company.model.dto.DepartmentDto;
import br.com.hotmart.company.model.dto.EmployeeDto;
import br.com.hotmart.company.model.dto.ProjectDto;
import br.com.hotmart.company.model.entity.*;
import br.com.hotmart.company.repository.DepartmentRepository;
import br.com.hotmart.company.repository.EmployeeRepository;
import br.com.hotmart.company.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Double YELLOW_LIMIT = 10D;

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

    @Override
    public List<BudgetStatusDto> budgetStatus(Long id) {
        Optional<Department> department = findBy(id);
        List<Budget> departmentBudgets = department.get().getBudgets();
        List<Project> departmentProjects = department.get().getProjects();
        List<BudgetStatusDto> budgetStatusList = new ArrayList<>();
        departmentBudgets.forEach(budget -> {
            List<Project> budgetProjects = filterProjectsByBudget(departmentProjects, budget);
            Double sumOfProjectsValue = sumProjectsValueConsideringEmployeesSalary(budgetProjects);
            BudgetStatus budgetStatus = statusFromBudget(budget.getValue(), sumOfProjectsValue);
            budgetStatusList.add(new BudgetStatusDto(budget, budgetStatus));
        });
        return budgetStatusList;
    }

    @Override
    public List<ProjectDto> projects(Long id) {
        Optional<Department> department = findBy(id);
        return department.get().getProjects().stream().map(ProjectDto::new).collect(Collectors.toList());
    }

    private BudgetStatus statusFromBudget(Double budgetValue, Double sumOfProjectsValue) {
        Double yellowLimit = budgetValue + (budgetValue * YELLOW_LIMIT) / 100;
        if(sumOfProjectsValue <= budgetValue){
            return BudgetStatus.GREEN;
        }else if(sumOfProjectsValue <= yellowLimit){
            return BudgetStatus.YELLOW;
        }else{
            return BudgetStatus.RED;
        }
    }

    private Double sumProjectsValueConsideringEmployeesSalary(List<Project> projects) {
        AtomicReference<Double> sumOfProjectsValue = new AtomicReference<>(0D);
        projects.stream().forEach(project -> {
            List<Double> employeeSalaryList = project.getEmployees().stream().map(Employee::getSalary).collect(Collectors.toList());
            Double employeesSalarySum = employeeSalaryList.stream().reduce(0D, (salary1, salary2) -> salary1 + salary2);
            Double finalProjectValue = employeesSalarySum + project.getValue();
            sumOfProjectsValue.set(finalProjectValue);
        });
        return sumOfProjectsValue.get();
    }

    private List<Project> filterProjectsByBudget(List<Project> projects, Budget budget) {
        return projects.stream().filter(project -> (project.getStartDate().isAfter(budget.getStartDate()) && project.getEndDate().isBefore(budget.getEndDate()))).collect(Collectors.toList());
    }

    private Optional<Department> findBy(Long id){
        Optional<Department> department = departmentRepository.findById(id);
        if(!department.isPresent()){
            throw new RuntimeException("Department not found");
        }
        return department;
    }
}
