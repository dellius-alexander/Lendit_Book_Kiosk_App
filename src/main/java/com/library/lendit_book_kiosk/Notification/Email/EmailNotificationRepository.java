package com.library.lendit_book_kiosk.Notification.Email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "EmailNotificationRepository")
public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {
}
