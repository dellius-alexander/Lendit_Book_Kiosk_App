package com.library.lendit_book_kiosk.Notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "NotificationRepository")
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
