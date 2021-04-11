package br.com.tiagofeliz.company.service.impl;

import br.com.tiagofeliz.company.config.exception.ResourceNotFoundException;
import br.com.tiagofeliz.company.model.dto.BudgetStatusDto;
import br.com.tiagofeliz.company.model.dto.DepartmentDto;
import br.com.tiagofeliz.company.model.dto.EmployeeDto;
import br.com.tiagofeliz.company.model.dto.ProjectDto;
import br.com.tiagofeliz.company.model.entity.*;
import br.com.tiagofeliz.company.repository.DepartmentRepository;
import br.com.tiagofeliz.company.repository.EmployeeRepository;
import br.com.tiagofeliz.company.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        return DepartmentDto.asList(departments);
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
    public DepartmentDto update(Department updateTo, Long id) {
        if(!exists(id)){
            throw new ResourceNotFoundException("Department not found");
        }
        Department department = findBy(id);
        save(department, updateTo);
        return new DepartmentDto(department);
    }

    private void save(Department currentDepartment, Department updateTo){
        currentDepartment.setName(updateTo.getName());
    }

    @Override
    public void delete(Long id) {
        if(!exists(id)){
            throw new ResourceNotFoundException("Department not found");
        }
        Department department = findBy(id);
        departmentRepository.delete(department);
    }

    @Override
    public List<EmployeeDto> employees(Long id) {
        if(!exists(id)){
            throw new ResourceNotFoundException("Department not found");
        }
        Department department = findBy(id);
        List<Employee> employees = employeeRepository.findByProjectsDepartmentId(department.getId());
        return EmployeeDto.asList(employees);
    }

    @Override
    public List<BudgetStatusDto> budgetStatus(Long id) {
        if(!exists(id)){
            throw new ResourceNotFoundException("Department not found");
        }
        Department department = findBy(id);
        List<Budget> departmentBudgets = department.getBudgets();
        List<Project> departmentProjects = department.getProjects();
        List<BudgetStatusDto> budgetStatusList = new ArrayList<>();
        departmentBudgets.forEach(budget -> {
            List<Project> budgetProjects = filterProjectsByBudget(departmentProjects, budget);
            int monthsQuantityInPeriod = monthAmount(budget);
            Double sumOfProjectsValue = sumProjectsValueConsideringEmployeesSalary(department.getId(), budgetProjects, monthsQuantityInPeriod);
            BudgetStatus budgetStatus = statusFromBudget(budget.getValue(), sumOfProjectsValue);
            budgetStatusList.add(new BudgetStatusDto(budget, budgetStatus));
        });
        return budgetStatusList;
    }

    private int monthAmount(Budget budget) {
        Period diff = Period.between(
                budget.getStartDate().withDayOfMonth(1),
                budget.getEndDate().withDayOfMonth(1));
        return diff.getMonths();
    }

    @Override
    public List<ProjectDto> projects(Long id) {
        if(!exists(id)){
            throw new ResourceNotFoundException("Department not found");
        }
        Department department = findBy(id);
        return ProjectDto.asList(department.getProjects());
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

    private Double sumProjectsValueConsideringEmployeesSalary(Long departmentId, List<Project> budgetProjects, int monthsQuantityInPeriod) {
        List<Employee> departmentEmployees = employeeRepository.findByProjectsDepartmentId(departmentId);
        Double employeesSalarySum = departmentEmployees.stream().mapToDouble(Employee::getSalary).sum();
        employeesSalarySum = employeesSalarySum * monthsQuantityInPeriod;
        Double projectValueSum = budgetProjects.stream().mapToDouble(Project::getValue).sum();
        return employeesSalarySum + projectValueSum;
    }

    private List<Project> filterProjectsByBudget(List<Project> projects, Budget budget) {
        return projects.stream().filter(project -> (project.getStartDate().isAfter(budget.getStartDate()) && project.getEndDate().isBefore(budget.getEndDate()))).collect(Collectors.toList());
    }

    private boolean exists(Long id){
        return departmentRepository.existsById(id);
    }

    private Department findBy(Long id){
        return departmentRepository.findById(id).get();
    }
}
