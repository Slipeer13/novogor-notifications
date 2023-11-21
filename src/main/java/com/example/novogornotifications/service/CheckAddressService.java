package com.example.novogornotifications.service;

public interface CheckAddressService {

    boolean checkAddress(String address);

    String formatAddress(String address);
}
