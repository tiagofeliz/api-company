package br.com.tiagofeliz.company.model.form;

import br.com.tiagofeliz.company.model.entity.Employee;
import br.com.tiagofeliz.company.model.entity.Gender;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class EmployeeFormTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAExceptionWhenEmployeesAddressIsNotPresent(){
        EmployeeForm form = new EmployeeForm();
        form.setName("Employee name");
        form.setSalary(1000);
        form.setGender(Gender.MALE);
        form.setBirthDate(LocalDate.of(1996, 5, 30));
        form.setCpf("000.000.000-00");
        form.setIdSupervisor(1L);

        form.toEntity();
    }

    @Test
    public void shouldReturnAInstanceOfAEmployee(){
        AddressForm addressForm = new AddressForm();
        addressForm.setCity("City name");
        addressForm.setCountry("Country");
        addressForm.setStreet("Street name");
        addressForm.setUf("AC");
        addressForm.setZipCode("00000-000");

        EmployeeForm form = new EmployeeForm();
        form.setName("Employee name");
        form.setSalary(1000);
        form.setGender(Gender.MALE);
        form.setBirthDate(LocalDate.of(1996, 5, 30));
        form.setCpf("000.000.000-00");
        form.setIdSupervisor(1L);
        form.setAddress(addressForm);

        Employee employee = form.toEntity();

        assertEquals(form.getName(), employee.getName());
        assertEquals(form.getSalary(), employee.getSalary(), 0.00001);
        assertEquals(form.getGender(), employee.getGender());
        assertEquals(form.getBirthDate(), employee.getBirthDate());
        assertEquals(form.getCpf(), employee.getCpf());
        assertEquals(form.getIdSupervisor(), employee.getSupervisor().getId());
        assertEquals(addressForm.toEntity(), employee.getAddress());
    }

}
