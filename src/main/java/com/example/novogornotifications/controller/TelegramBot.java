package com.example.novogornotifications.controller;

import com.example.novogornotifications.configutation.PropertiesBot;
import com.example.novogornotifications.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private PropertiesBot propertiesBot;
    @Autowired
    private SendMessageService sendMessageService;
    @Value("${bot.token}")
    private String token;

    public TelegramBot(@Value("${bot.token}") String token) {
        super(token);
    }

    @Override
    public String getBotUsername() {
        return propertiesBot.username();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            sendMessageService.getMessage(update).forEach(m-> {
                try {
                    execute(m);
                } catch (Exception ignored) {}
            });
        }
    }

    public void sendMessage(String messageText , String chatId) {
        try {
            String urlString = "https://api.telegram.org/bot" + token + "/sendMessage?chat_id="
                    + chatId + "&text=" + URLEncoder.encode(messageText, "UTF-8");

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
