package br.com.tiagofeliz.company.model.form;

import br.com.tiagofeliz.company.model.entity.Address;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddressFormTest {

    @Test
    public void shouldReturnAInstanceOfAddress(){
        AddressForm addressForm = new AddressForm();
        addressForm.setCity("City name");
        addressForm.setCountry("Country name");
        addressForm.setStreet("Street name");
        addressForm.setUf("AC");
        addressForm.setZipCode("00000-000");

        Address address = addressForm.toEntity();

        assertNotNull(address);
        assertEquals(addressForm.getCity(), address.getCity());
        assertEquals(addressForm.getCountry(), address.getCountry());
        assertEquals(addressForm.getStreet(), address.getStreet());
        assertEquals(addressForm.getUf(), address.getUf());
        assertEquals(addressForm.getZipCode(), address.getZipCode());
    }

}
