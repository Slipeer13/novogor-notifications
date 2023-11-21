package com.example.novogornotifications.service;

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

@Service
public interface MessageService {

    void readAndSaveDisableMessage(Message message) throws MessagingException, IOException;
}
