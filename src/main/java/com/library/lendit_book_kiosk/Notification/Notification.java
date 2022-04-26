package com.library.lendit_book_kiosk.Notification;

import com.library.lendit_book_kiosk.Notification.Email.EmailNotification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "notification")
public class Notification implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Notification.class);
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Column(
            name = "notification_id",
            unique = true
    )
    private Long id;
    private String type;
    private String message;
    @OneToMany(
            mappedBy = "notification"
    )
    private Set<EmailNotification> emailNotifications;

    /////////////////////////////////////////////////////////////////


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
