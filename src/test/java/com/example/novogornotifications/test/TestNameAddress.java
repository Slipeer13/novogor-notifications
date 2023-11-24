package com.example.novogornotifications.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class TestNameAddress {


    @Test
    public void testNameAddress() {
        String address1 = "Подключить улица 1й Павловский проезд59 /1.".toLowerCase(Locale.ROOT);
        String address2 = "Подключить       !1-й Павловский проезд&*, д59/1".toLowerCase(Locale.ROOT);
        String address3 ="Подключить Г. ПЕРМЬ, УЛ. АЛЕКСАНДРА ЩЕРБАКОВА, д. 75.".toLowerCase(Locale.ROOT);
        String address4 ="Подключить АЛЕКСАНДРА ЩЕРБАКОВА, д 75.".toLowerCase(Locale.ROOT);
        String address5 ="Подключить Г.ПЕРМЬ, УЛ. пермская, д. 75.".toLowerCase(Locale.ROOT);
        String address6 ="Подключить пермская д75.".toLowerCase(Locale.ROOT);
        String address7 ="Подключить Г.ПЕРМЬ, УЛ.пермская, д 75.".toLowerCase(Locale.ROOT);
        String address8 ="Подключить ул пермская д75.".toLowerCase(Locale.ROOT);

        Assertions.assertEquals(formatAddress(address1), formatAddress(address2));
        Assertions.assertEquals(formatAddress(address3), formatAddress(address4));
        Assertions.assertEquals(formatAddress(address5), formatAddress(address6));
        Assertions.assertEquals(formatAddress(address7), formatAddress(address8));
    }

    public String formatAddress(String address) {
        String[] request = address.split("подключить");
        address = request[1];
        return address.replaceAll("[^a-zA-Z0-9а-яА-Я ,./]", "")//убирает спец символы
                .replaceAll("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)", " ")//вставляет пробел между словом и цифрами
                .replaceAll("(?<=\\d)\\s(?=\\d)", "")//убирает пробел между цифрами
                .replace("улица", " ")
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
