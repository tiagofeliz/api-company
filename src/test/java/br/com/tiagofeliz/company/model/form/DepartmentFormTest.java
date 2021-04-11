package br.com.tiagofeliz.company.model.form;

import br.com.tiagofeliz.company.model.entity.Department;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DepartmentFormTest {

    @Test
    public void shouldReturnAInstanceOfDepartment(){
        DepartmentForm form = new DepartmentForm();
        form.setName("Department name");
        form.setBudgets(new ArrayList<>());

        Department department = form.toEntity();

        assertEquals(form.getName(), department.getName());
        assertEquals(form.getBudgets(), department.getBudgets());
    }

}
