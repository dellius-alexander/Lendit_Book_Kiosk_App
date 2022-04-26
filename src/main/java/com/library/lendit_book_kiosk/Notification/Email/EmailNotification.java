package com.library.lendit_book_kiosk.Notification.Email;

import com.library.lendit_book_kiosk.Book.Borrow.Borrow_Book;
import com.library.lendit_book_kiosk.Notification.Notification;
import com.library.lendit_book_kiosk.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "email_notification")
public class EmailNotification implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(EmailNotification.class);
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )

    @Column(
            name = "email_id",
            unique = true
    )
    private Long id;
    @ManyToOne(
            targetEntity = User.class
    )
    @JoinColumn(
            name = "email",
            referencedColumnName = "email"
    )
    private User user;
    @ManyToOne(
            targetEntity = Notification.class,
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumns(
            value = {
                    @JoinColumn(
                            name = "notification_id",
                            referencedColumnName = "notification_id"
                    ),
                    @JoinColumn(
                            name = "message",
                            referencedColumnName = "message"
                    ),
                    @JoinColumn(
                            name = "type",
                            referencedColumnName = "type"
                    )
            }
    )
    private Notification notification;
    @ManyToOne(
            targetEntity = Borrow_Book.class,
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumns(
            value = {
                    @JoinColumn(
                            name = "student_id",
                            referencedColumnName = "student_id"
                    ),
                    @JoinColumn(
                            name = "isbn",
                            referencedColumnName = "isbn"
                    ),
                    @JoinColumn(
                            name = "borrow_id",
                            referencedColumnName = "borrow_id"
                    ),
                    @JoinColumn(
                            name = "slot_id",
                            referencedColumnName = "slot_id"
                    ),
                    @JoinColumn(
                            name = "copy_id",
                            referencedColumnName = "copy_id"
                    )
            }

    )
    private Borrow_Book borrowed_book;
}
