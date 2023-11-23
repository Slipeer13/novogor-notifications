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
        return address.replaceAll("[^a-zA-Z0-9а-яА-Я ,./]", "")//убирает спец символы
                .replace("улица", "")
                .replace("ул ", "")
                .replace("ул.", "")
                .replace("дом ", "")
                .replace("д ", "")
                .replace("д.", "")
                .replace("г ", "")
                .replace("г.", "")
                .replace("гор.", "")
                .replace("пермь ", "")
                .replace("пермь,", "")
                .replace(" ", "")
                .replace(",", " ")
                .replace(".", " ")
                .replaceAll("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)", " ")//вставляет пробел между словом и цифрами
                .replaceAll("(?<=\\d)\\s(?=\\d)", "")//убирает пробел между цифрами
                .replaceAll("\\s+", " ").trim();
    }
}
