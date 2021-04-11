package br.com.tiagofeliz.company.model.form;

import br.com.tiagofeliz.company.model.entity.Project;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class ProjectFormTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAExceptionIfDepartmentIdIsNotPresent(){
        ProjectForm form = new ProjectForm();
        form.setName("Project name");
        form.setStartDate(LocalDate.of(2019, 5, 1));
        form.setEndDate(LocalDate.of(2019, 5, 30));
        form.setValue(1500);

        form.toEntity();
    }

    @Test
    public void shouldReturnAInstanceOfAProject(){
        ProjectForm form = new ProjectForm();
        form.setName("Project name");
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
