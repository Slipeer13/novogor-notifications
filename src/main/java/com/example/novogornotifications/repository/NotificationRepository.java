package com.example.novogornotifications.repository;

import com.example.novogornotifications.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Notification n WHERE n.dateDisabling <= :date")
    void deleteNotificationsByDateDisablingLessThanEqual(@Param("date") LocalDate date);

    @Query("SELECT DISTINCT n FROM Notification n JOIN FETCH n.address a JOIN FETCH a.memberList WHERE n.dateDisabling = :date")
    List<Notification> getNotificationsWithAddressesMembers(@Param("date") LocalDate date);
}
