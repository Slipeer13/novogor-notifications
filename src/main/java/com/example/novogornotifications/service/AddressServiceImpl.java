package com.example.novogornotifications.service;

import com.example.novogornotifications.entity.Address;
import com.example.novogornotifications.entity.Notification;
import com.example.novogornotifications.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CheckAddressService checkAddressService;

    @Override
    @Transactional
    public Optional<Address> get(String address) {
        return Optional.ofNullable(addressRepository.findAddressByName(address));
    }

    @Override
    @Transactional
    public Address update(Address address) {
        String addressName = checkAddressService.formatAddress(address.getName());
        Optional<Address> addressOptional = get(addressName);
        Address addressFromDB = addressOptional.orElseGet(() -> save(addressName));
        List<Notification> notificationListFromDB = addressFromDB.getNotifications();
        List<Notification> notifications = address.getNotifications();
        notifications.forEach(n-> n.setAddress(addressFromDB));
        notificationListFromDB.addAll(notifications);
        return addressRepository.save(addressFromDB);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address save(String address) {
        return save(new Address((address)));
    }


}
