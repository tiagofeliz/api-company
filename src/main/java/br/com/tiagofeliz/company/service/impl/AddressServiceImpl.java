package br.com.tiagofeliz.company.service.impl;

import br.com.tiagofeliz.company.config.exception.ResourceNotFoundException;
import br.com.tiagofeliz.company.model.dto.AddressDto;
import br.com.tiagofeliz.company.model.entity.Address;
import br.com.tiagofeliz.company.repository.AddressRepository;
import br.com.tiagofeliz.company.service.AddressService;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<AddressDto> findAll() {
        List<Address> addresses = addressRepository.findAll();
        return AddressDto.asList(addresses);
    }

    @Override
    public AddressDto create(Address address) {
        return new AddressDto(addressRepository.save(address));
    }

    @Override
    public AddressDto update(Address updateTo, Long id) {
        if(!exists(id)){
            throw new ResourceNotFoundException("Address not found");
        }
        Address address = findBy(id);
        save(address, updateTo);
        return new AddressDto(address);
    }

    public void save(Address currentAddress, Address toSaveAddress) {
        currentAddress.setCountry(toSaveAddress.getCountry());
        currentAddress.setCity(toSaveAddress.getCity());
        currentAddress.setStreet(toSaveAddress.getStreet());
        currentAddress.setUf(toSaveAddress.getUf());
        currentAddress.setZipCode(toSaveAddress.getZipCode());
    }

    @Override
    public Optional<AddressDto> findById(Long id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        return addressOptional.map(AddressDto::new);
    }

    private boolean exists(Long id){
        return addressRepository.existsById(id);
    }

    private Address findBy(Long id){
        return addressRepository.findById(id).get();
    }
}
