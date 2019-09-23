package br.com.hotmart.company.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String uf;
    private String city;
    private String street;
    private String zipCode;

    public Address(String country, String uf, String city, String street, String zipCode) {
        this.country = country;
        this.uf = uf;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }
}
