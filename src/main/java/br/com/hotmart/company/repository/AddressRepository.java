package br.com.hotmart.company.repository;

import br.com.hotmart.company.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
