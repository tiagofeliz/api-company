package br.com.hotmart.company.service;

import static org.junit.Assert.assertEquals;

import br.com.hotmart.company.model.dto.AddressDto;
import br.com.hotmart.company.model.entity.Address;
import br.com.hotmart.company.repository.AddressRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepository addressRepository;

    @Test
    public void shouldSaveAddress(){
        Address address = new Address("Brasil", "MG", "BH", "Entre Rios", "30710-080");

        AddressDto savedAddress = addressService.create(address);

        assertEquals(address.getStreet(), savedAddress.getStreet());
    }

}
