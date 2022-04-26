package com.library.lendit_book_kiosk.Notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;

@Transactional
@Service
public class NotificationService implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    @Autowired
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

}
