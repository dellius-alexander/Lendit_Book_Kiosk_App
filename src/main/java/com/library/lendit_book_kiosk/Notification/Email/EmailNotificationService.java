package com.library.lendit_book_kiosk.Notification.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;

@Transactional
@Service
public class EmailNotificationService implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(EmailNotificationService.class);
    @Autowired
    private final EmailNotificationRepository emailNotificationRepository;

    public EmailNotificationService(EmailNotificationRepository emailNotificationRepository) {
        this.emailNotificationRepository = emailNotificationRepository;
    }

}
