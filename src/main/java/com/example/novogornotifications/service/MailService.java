package com.example.novogornotifications.service;

import com.example.novogornotifications.configutation.PropertiesMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@Service
@EnableAsync
public class MailService {

    @Autowired
    private PropertiesMail propertiesMail;
    @Autowired
    private MessageService messageService;

    private Store store;

    {
        try {
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");
            props.put("mail.imaps.ssl.trust", "*");
            props.put("mail.imaps.ssl.protocols", "TLSv1.2");
            store = Session.getInstance(props).getStore();
        }  catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    @Async
    @Scheduled(fixedDelay = 5 * 60000)
    public void getMessages() throws MessagingException, IOException {
        store.connect(propertiesMail.host(), propertiesMail.login(), propertiesMail.pass());
        // Получение папки с сообщениями
        Folder folder = store.getFolder("inbox");
        folder.open(Folder.READ_WRITE);
        Optional<Message[]> messages = Optional.ofNullable(folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false)));
        if(messages.isPresent()) {
            for (Message message : messages.get()) {
                if (message.getSubject().contains("отключ")) {
                    messageService.readAndSaveDisableMessage(message);
                }
            }
        }
        folder.close(true);
        store.close();
    }
}
