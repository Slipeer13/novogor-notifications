package com.example.novogornotifications.service;

import org.springframework.stereotype.Service;

@Service
public class CheckAddressServiceImpl implements CheckAddressService {


    @Override
    public String formatAddress(String address) {
        return address.replaceAll("[^a-zA-Z0-9а-яА-Я ,./ё]", "")//убирает спец символы
                .replaceAll("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)", " ")//вставляет пробел между словом и цифрами
                .replaceAll("(?<=\\d)\\s(?=\\d)", "")//убирает пробел между цифрами
                .replace("улица", " ")
                .replace("улицу", " ")
                .replace(" ул ", " ")
                .replace(" ул.", " ")
                .replace(" дом ", " ")
                .replace(" д ", " ")
                .replace(" д.", " ")
                .replace(" г ", " ")
                .replace(" г.", " ")
                .replace(" гор.", " ")
                .replace("пермь ", " ")
                .replace("пермь,", " ")
                .replace(" ", " ")
                .replace(",", " ")
                .replace(".", " ")
                .replaceAll("\\s+", " ").trim();
    }
}
