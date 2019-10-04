package br.com.hotmart.company.service;

import br.com.hotmart.company.config.exception.ResourceNotFoundException;
import br.com.hotmart.company.model.dto.AddressDto;
import br.com.hotmart.company.model.entity.Address;
import br.com.hotmart.company.repository.AddressRepository;
import br.com.hotmart.company.service.impl.AddressServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        assertEquals(address.getCity(), addresses.get(0).getCity());
        assertEquals(address.getUf(), addresses.get(0).getUf());
        assertEquals(address.getCountry(), addresses.get(0).getCountry());
        assertEquals(address.getZipCode(), addresses.get(0).getZipCode());
    }

    @Test
    public void shouldSaveAddress(){
        Address address = new Address("Brasil", "MG", "BH", "Entre Rios", "30710-080");

        Mockito.when(addressRepository.save(address)).thenReturn(address);

        AddressDto savedAddress = addressService.create(address);

        assertEquals(address.getStreet(), savedAddress.getStreet());
        assertEquals(address.getCountry(), savedAddress.getCountry());
        assertEquals(address.getUf(), savedAddress.getUf());
        assertEquals(address.getCity(), savedAddress.getCity());
        assertEquals(address.getZipCode(), savedAddress.getZipCode());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowAExceptionWhenAddressIdIsNotValid(){
        Address address = new Address("Brasil", "MG", "BH", "Entre Rios", "30710-080");
        Mockito.when(addressRepository.existsById(1L)).thenReturn(false);

        addressService.update(address, 1L);
    }

    @Test
    public void shouldUpdateAAddress(){
        Address currentAddress = new Address("Brasil", "MG", "BH", "Entre Rios", "30710-080");
        Address updateTo = new Address("Japao", "MG", "BH", "Entre Rios", "30710-080");
        Mockito.when(addressRepository.existsById(1L)).thenReturn(true);
        Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(currentAddress));

        AddressDto updatedAddress = addressService.update(updateTo, 1L);

        assertEquals(updateTo.getStreet(), updatedAddress.getStreet());
        assertEquals(updateTo.getCountry(), updatedAddress.getCountry());
        assertEquals(updateTo.getUf(), updatedAddress.getUf());
        assertEquals(updateTo.getCity(), updatedAddress.getCity());
        assertEquals(updateTo.getZipCode(), updatedAddress.getZipCode());
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

        Optional<AddressDto> addressDto = addressService.findById(1L);

        assertEquals(address.getStreet(), addressDto.get().getStreet());
        assertEquals(address.getZipCode(), addressDto.get().getZipCode());
        assertEquals(address.getCity(), addressDto.get().getCity());
        assertEquals(address.getCountry(), addressDto.get().getCountry());
        assertEquals(address.getUf(), addressDto.get().getUf());
    }

    @Test
    public void shouldReturnEmptyWhenResourceIdIsNotFound(){
        Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<AddressDto> address = addressService.findById(1L);

        assertEquals(Optional.empty(), address);
    }

}
