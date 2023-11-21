package com.example.novogornotifications.service;

import com.example.novogornotifications.entity.Address;
import com.example.novogornotifications.entity.Notification;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;
import java.util.List;

public interface ParsingExcelFileNotificationService {

    List<Address> getAddresses(MimeBodyPart part) throws MessagingException, IOException;
}
