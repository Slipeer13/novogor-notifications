package com.example.novogornotifications.service;

import com.example.novogornotifications.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private ParsingExcelFileNotificationService parsingExcelFileNotificationService;
    @Autowired
    private AddressService addressService;

    @Override
    public void readAndSaveDisableMessage(Message message) throws MessagingException, IOException {
        Object content = message.getContent();
        if (content instanceof Multipart multipart) {
            for (int i = 0; i < multipart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(i);
                List<Address> addresses = parsingExcelFileNotificationService.getAddresses(part);
                if(!addresses.isEmpty()) addresses.forEach(a-> addressService.update(a));
            }
        }

    }
}
