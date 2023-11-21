package com.example.novogornotifications.service;

public interface DisableService {

    String getConnect(long chatId, String address);

    String getDisconnect(long chatId, String address);

    String getInfoDisable(long chatId);

}
