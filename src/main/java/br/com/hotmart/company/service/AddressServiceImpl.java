package br.com.hotmart.company.service;

import br.com.hotmart.company.model.dto.AddressDto;
import br.com.hotmart.company.model.entity.Address;
import br.com.hotmart.company.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public AddressDto create(Address address) {
        return new AddressDto(addressRepository.save(address));
    }
}
