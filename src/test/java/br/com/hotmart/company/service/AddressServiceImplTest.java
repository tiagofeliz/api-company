package br.com.hotmart.company.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.hotmart.company.model.dto.AddressDto;
import br.com.hotmart.company.model.entity.Address;
import br.com.hotmart.company.repository.AddressRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepository addressRepository;

    @Test
    public void shouldReturnAEmptyListWhenThereNoAreRecords(){
        List<Address> addressList = new ArrayList<>();
        Mockito.when(addressRepository.findAll()).thenReturn(addressList);
        List<AddressDto> addresses = addressService.findAll();

        assertTrue(addresses.isEmpty());
    }

    @Test
    public void shouldReturnAListOfAddressesWhenThereAreRecords(){
        Address address = new Address(
                "Brasil",
                "MG",
                "BH",
                "Entre Rios",
                "30710-080");

        List<Address> addressList = new ArrayList<>();
        addressList.add(address);

        Mockito.when(addressRepository.findAll()).thenReturn(addressList);
        List<AddressDto> addresses = addressService.findAll();

        assertEquals(1, addresses.size());
        assertEquals(address.getStreet(), addresses.get(0).getStreet());
    }

    @Test
    public void shouldSaveAddress(){
        Address address = new Address("Brasil", "MG", "BH", "Entre Rios", "30710-080");

        Mockito.when(addressRepository.save(address)).thenReturn(address);

        AddressDto savedAddress = addressService.create(address);

        assertEquals(address.getStreet(), savedAddress.getStreet());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAExceptionWhenAddressIdIsNotValid(){
        Address address = new Address("Brasil", "MG", "BH", "Entre Rios", "30710-080");
        Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        addressService.update(address, 1L);
    }

    @Test
    public void shouldUpdateAAddress(){
        Address currentAddress = new Address("Brasil", "MG", "BH", "Entre Rios", "30710-080");
        Address updateTo = new Address("Japao", "MG", "BH", "Entre Rios", "30710-080");
        Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(currentAddress));

        AddressDto updatedAddress = addressService.update(updateTo, 1L);

        assertEquals(updateTo.getStreet(), updatedAddress.getStreet());
    }

    @Test
    public void shouldReturnAAddress(){
        Address address = new Address(
                "Brasil",
                "MG",
                "BH",
                "Entre Rios",
                "30710-080");
        address.setId(1L);

        Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        Optional<AddressDto> newEmployee = addressService.findById(1L);

        assertEquals(address.getStreet(), newEmployee.get().getStreet());
    }

}
