package br.com.hotmart.company.service;

import br.com.hotmart.company.model.dto.AddressDto;
import br.com.hotmart.company.model.entity.Address;

public interface AddressService {

    AddressDto create(Address address);

}
