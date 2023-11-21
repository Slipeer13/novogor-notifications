package com.example.novogornotifications.service;

import com.example.novogornotifications.entity.Address;

import java.util.Optional;

public interface AddressService {

    Optional<Address> get(String address);

    Address update(Address address);

    Address save(Address address);

    Address save(String address);
}
