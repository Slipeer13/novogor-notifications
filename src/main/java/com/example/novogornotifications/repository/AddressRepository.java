package com.example.novogornotifications.repository;

import com.example.novogornotifications.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findAddressByName(String name);
}
