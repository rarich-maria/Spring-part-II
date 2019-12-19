package com.geekbrains.decembermarket.services;

import com.geekbrains.decembermarket.entites.Address;
import com.geekbrains.decembermarket.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private AddressRepository addressRepository;

    @Autowired
    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address save (Address address) {
        return addressRepository.save(address);
    }
}
