package com.example.novogornotifications.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface SendMessageService {

    List<SendMessage> getMessage(Update update);

    String getResponseText(long chatId, String messageText);
}
