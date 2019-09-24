package br.com.hotmart.company.service;

import br.com.hotmart.company.model.dto.AddressDto;
import br.com.hotmart.company.model.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    List<AddressDto> findAll();
    Optional<AddressDto> findById(Long id);
    AddressDto create(Address address);
    AddressDto update(Address address, Long id);

}
