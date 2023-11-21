package com.example.novogornotifications.service;

import com.example.novogornotifications.entity.Notification;

import java.time.LocalDate;
import java.util.List;

public interface NotificationService {

    List<Notification> getNotifications();

    public List<Notification> getNotificationsWithAddressesMembers(LocalDate date);

    void deleteNotification(LocalDate date);
}
