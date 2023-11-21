package com.example.novogornotifications.service;

import com.example.novogornotifications.controller.TelegramBot;
import com.example.novogornotifications.entity.Member;
import com.example.novogornotifications.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@EnableAsync
public class CallService {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private formatService formatService;
    @Autowired
    private TelegramBot telegramBot;

    @Async
    @Scheduled(cron = "0 0 3 * * *", zone = "Europe/Moscow")
    public void deleteNotification() {
        notificationService.deleteNotification(LocalDate.now(ZoneId.of("Europe/Moscow")).minusDays(1));
    }


    @Async
    @Scheduled(cron = "0 0 06 * * *", zone = "Europe/Moscow")
    public void callMorning() {
        call(LocalDate.now(ZoneId.of("Europe/Moscow")));
    }

    @Async
    @Scheduled(cron = "0 0 18 * * *", zone = "Europe/Moscow")
    public void callEven() {
        call(LocalDate.now(ZoneId.of("Europe/Moscow")).plusDays(1));
    }


    private void call(LocalDate date) {
        List<Notification> notifications = notificationService.getNotificationsWithAddressesMembers(date);
        notifications.forEach(notification -> notification.getAddress().getMemberList()
                        .forEach(member -> sendNotification(member, notification)));

    }


    private void sendNotification(Member member, Notification notification) {
        String result = "Отключение воды: " + notification.getAddress().getName()
                + " " + notification.getInfo() + " " + notification.getDateDisabling().format(formatService.getFormatter());
        telegramBot.sendMessage(result, member.getId().toString());
    }

}
