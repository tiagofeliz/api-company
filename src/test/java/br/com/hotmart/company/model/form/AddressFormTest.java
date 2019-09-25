package br.com.hotmart.company.model.form;

import br.com.hotmart.company.model.entity.Address;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddressFormTest {

    @Test
    public void shouldReturnAInstanceOfAddress(){
        AddressForm addressForm = new AddressForm();
        addressForm.setCity("Guanambi");
        addressForm.setCountry("Brasil");
        addressForm.setStreet("Minas Gerais");
        addressForm.setUf("BA");
        addressForm.setZipCode("46430-000");

        Address address = addressForm.toEntity();

        assertNotNull(address);
        assertEquals(addressForm.getCity(), address.getCity());
        assertEquals(addressForm.getCountry(), address.getCountry());
        assertEquals(addressForm.getStreet(), address.getStreet());
        assertEquals(addressForm.getUf(), address.getUf());
        assertEquals(addressForm.getZipCode(), address.getZipCode());
    }

}
