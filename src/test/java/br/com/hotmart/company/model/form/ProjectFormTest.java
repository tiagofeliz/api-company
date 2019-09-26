package br.com.hotmart.company.model.form;

import br.com.hotmart.company.model.entity.Project;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProjectFormTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAExceptionIfDepartmentIdIsNotPresent(){
        ProjectForm form = new ProjectForm();
        form.setName("Revisão de produtos");
        form.setStartDate(LocalDate.of(2019, 5, 1));
        form.setEndDate(LocalDate.of(2019, 5, 30));
        form.setValue(1500);

        form.toEntity();
    }

    @Test
    public void shouldCreateADepartmentWhenDepartmentIdIsNotNull(){
        ProjectForm form = new ProjectForm();
        form.setName("Revisão de produtos");
        form.setStartDate(LocalDate.of(2019, 5, 1));
        form.setEndDate(LocalDate.of(2019, 5, 30));
        form.setValue(1500);
        form.setIdDepartment(1L);

        Project project = form.toEntity();

        assertEquals(1L, project.getDepartment().getId(), 0.00001);
        assertNotNull(project.getDepartment());
    }

    @Test
    public void shouldReturnAInstanceOfAProject(){
        ProjectForm form = new ProjectForm();
        form.setName("Revisão de produtos");
        form.setStartDate(LocalDate.of(2019, 5, 1));
        form.setEndDate(LocalDate.of(2019, 5, 30));
        form.setValue(1500);
        form.setIdDepartment(1L);

        Project project = form.toEntity();

        assertEquals(form.getName(), project.getName());
        assertEquals(form.getStartDate(), project.getStartDate());
        assertEquals(form.getEndDate(), project.getEndDate());
        assertEquals(form.getValue(), project.getValue(), 0.00001);
    }

}
