package br.com.hotmart.company.model.dto;

import br.com.hotmart.company.model.entity.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AddressDto {

    private Long id;
    private String country;
    private String uf;
    private String city;
    private String street;
    private String zipCode;

    public AddressDto(Address address) {
        this.id = address.getId();
        this.country = address.getCountry();
        this.uf = address.getUf();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.zipCode = address.getZipCode();
    }

    public static List<AddressDto> asList(List<Address> addressList){
        return addressList.stream().map(AddressDto::new).collect(Collectors.toList());
    }

}
