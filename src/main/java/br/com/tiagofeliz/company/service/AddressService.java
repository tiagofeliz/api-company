package br.com.tiagofeliz.company.service;

import br.com.tiagofeliz.company.model.dto.AddressDto;
import br.com.tiagofeliz.company.model.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    List<AddressDto> findAll();
    Optional<AddressDto> findById(Long id);
    AddressDto create(Address address);
    AddressDto update(Address address, Long id);

}
