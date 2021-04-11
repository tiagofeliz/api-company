package br.com.tiagofeliz.company.repository;

import br.com.tiagofeliz.company.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
