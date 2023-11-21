package com.example.novogornotifications.service;

import com.example.novogornotifications.entity.Notification;
import com.example.novogornotifications.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> getNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public void deleteNotification(LocalDate date) {
        notificationRepository.deleteNotificationsByDateDisablingLessThanEqual(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> getNotificationsWithAddressesMembers(LocalDate date) {
        return notificationRepository.getNotificationsWithAddressesMembers(date);
    }
}
