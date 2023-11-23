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
        return address.replaceAll("[^a-zA-Z0-9а-яА-Я ,./]", "").replace("улица", "")
                .replace(" ", "").replace(",", " ").replace(".", " ")
                .replaceAll("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)", " ").replaceAll("(?<=\\d)\\s(?=\\d)", "")//вставляет пробел между словом и цифрами и убирает пробел между цифрами
                .replaceAll("\\s+", " ").trim();
    }
}
