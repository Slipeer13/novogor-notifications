package com.example.novogornotifications.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CheckAddressServiceImpl implements CheckAddressService {


    @Override
    public boolean checkAddress(String address) {
        Pattern pattern = Pattern.compile("[А-Яа-яёЁ]+\\s+\\d+");
        Matcher matcher = pattern.matcher(address);
        return matcher.find();
    }

    @Override
    public String formatAddress(String address) {
        return address.replaceAll(",", "").replaceAll("улица", "").replaceAll("\\s+", " ");
    }
}
