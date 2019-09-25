package br.com.hotmart.company.service.impl;

import br.com.hotmart.company.model.dto.AddressDto;
import br.com.hotmart.company.model.entity.Address;
import br.com.hotmart.company.repository.AddressRepository;
import br.com.hotmart.company.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<AddressDto> findAll() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(AddressDto::new).collect(Collectors.toList());
    }

    @Override
    public AddressDto create(Address address) {
        return new AddressDto(addressRepository.save(address));
    }

    @Override
    public AddressDto update(Address address, Long id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if(addressOptional.isPresent()) {
            save(addressOptional.get(), address);
            return new AddressDto(addressOptional.get());
        }else{
            throw new RuntimeException("Address not found");
        }
    }

    public void save(Address currentAddress, Address toSaveAddress) {
        currentAddress.setCountry(toSaveAddress.getCountry());
        currentAddress.setCity(toSaveAddress.getCity());
        currentAddress.setStreet(toSaveAddress.getStreet());
        currentAddress.setUf(toSaveAddress.getUf());
        currentAddress.setZipCode(toSaveAddress.getZipCode());
    }

    public Optional<AddressDto> findById(Long id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        return addressOptional.map(AddressDto::new);
    }
}
