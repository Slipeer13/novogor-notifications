package com.example.novogornotifications.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class SendMessageServiceImpl implements SendMessageService{

    @Autowired
    private DisableService disableService;


    @Override
    public List<SendMessage> getMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();
        List<SendMessage> messageList = new ArrayList<>();
        String result = getResponseText(chatId, messageText.toLowerCase(Locale.ROOT));
        int length = result.length();
        if(length > 4096) {
            int start = 0;
            int end = 4096;
            while (true){
                messageList.add(new SendMessage(String.valueOf(chatId), result.substring(start, end)));
                if(end == length) break;
                start = end;
                end = length - end > 4096 ? end + 4096 : length;
            }
        } else {
            messageList.add(new SendMessage(String.valueOf(chatId), result));
        }
        return messageList;
    }

    @Override
    public String getResponseText(long chatId, String messageText) {
        String result = null;
        if (messageText.contains("подключить")) {
            String[] request = messageText.split("подключить");
            if(request.length > 1) {
                String addressName = request[1];
                result = disableService.getConnect(chatId, addressName);
            }
        } else if (messageText.contains("отключить")) {
            String[] request = messageText.split("отключить");
            if(request.length > 1) {
                String addressName = request[1];
                result = disableService.getDisconnect(chatId, addressName);
            }
        } else if (messageText.contains("инфо")) {
            result = disableService.getInfoDisable(chatId);
        }
        if(result == null) result = """
                С помощью этого бота можно получать информацию об отключении воды в г.Перми. Для этого есть возможные запросы:

                подключить улица дом
                отключить улица дом
                инфо

                например: подключить уссурийская 13
                Для правильного введения адреса рекомендуется скопировать адрес(название улицы и номер дома) из сервиса https://yandex.ru/maps/50/perm\s
                Оповещение об отключении воды будет отправлено вам за день до отключения и в день отключения воды""";
        return result;
    }
}
