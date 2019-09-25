package br.com.hotmart.company.model.form;

import br.com.hotmart.company.model.entity.Employee;
import br.com.hotmart.company.model.entity.Gender;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

public class EmployeeFormTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAExceptionWhenEmployeesAddressIsNotPresent(){
        EmployeeForm form = new EmployeeForm();
        form.setName("Tiago Feliz");
        form.setSalary(1000);
        form.setGender(Gender.MALE);
        form.setBirthDate(LocalDate.of(1996, 5, 30));
        form.setCpf("063.620.145-70");
        form.setIdSupervisor(1L);

        Employee employee = form.toEntity();
    }

    @Test
    public void shouldReturnAInstanceOfAEmployee(){
        AddressForm addressForm = new AddressForm();
        addressForm.setCity("Guanambi");
        addressForm.setCountry("Brasil");
        addressForm.setStreet("Minas Gerais");
        addressForm.setUf("BA");
        addressForm.setZipCode("46430-000");

        EmployeeForm form = new EmployeeForm();
        form.setName("Tiago Feliz");
        form.setSalary(1000);
        form.setGender(Gender.MALE);
        form.setBirthDate(LocalDate.of(1996, 5, 30));
        form.setCpf("063.620.145-70");
        form.setIdSupervisor(1L);
        form.setAddress(addressForm);

        Employee employee = form.toEntity();

        assertEquals(form.getName(), employee.getName());
        assertEquals(form.getSalary(), employee.getSalary(), 0.00001);
        assertEquals(form.getGender(), employee.getGender());
        assertEquals(form.getBirthDate(), employee.getBirthDate());
        assertEquals(form.getCpf(), employee.getCpf());
        assertEquals(form.getIdSupervisor(), employee.getSupervisor().getId());
        assertTrue(addressForm.toEntity().equals(employee.getAddress()));
    }

}
