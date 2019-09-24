package br.com.hotmart.company.controller;

import br.com.hotmart.company.model.dto.AddressDto;
import br.com.hotmart.company.model.entity.Address;
import br.com.hotmart.company.model.form.AddressForm;
import br.com.hotmart.company.service.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressServiceImpl addressService;

    @GetMapping
    public ResponseEntity<List<AddressDto>> index(){
        List<AddressDto> addresses = addressService.findAll();
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AddressDto> update(@PathVariable Long id, @RequestBody @Valid AddressForm form){
        Address address = form.toEntity();
        return ResponseEntity.ok(addressService.update(address, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> show(@PathVariable Long id){
        Optional<AddressDto> address = addressService.findById(id);
        if(!address.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address.get());
    }

}
