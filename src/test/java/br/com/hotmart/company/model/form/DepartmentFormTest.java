package br.com.hotmart.company.model.form;

import br.com.hotmart.company.model.entity.Department;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DepartmentFormTest {

    @Test
    public void shouldReturnAInstanceOfDepartment(){
        DepartmentForm form = new DepartmentForm();
        form.setName("Backoffice");
        form.setBudgets(new ArrayList<>());
        form.setProjects(new ArrayList<>());

        Department department = form.toEntity();

        assertEquals(form.getName(), department.getName());
        assertEquals(form.getBudgets(), department.getBudgets());
        assertEquals(form.getProjects(), department.getProjects());
    }

}
