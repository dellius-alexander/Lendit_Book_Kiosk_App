package com.library.lendit_book_kiosk.Book.Borrow;

import com.library.lendit_book_kiosk.Book.Kiosk.Kiosk;
import com.library.lendit_book_kiosk.Fines.Fines;
import com.library.lendit_book_kiosk.Notification.Email.EmailNotification;
import com.library.lendit_book_kiosk.Student.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "borrow_book")
public class Borrow_Book implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Borrow_Book.class);
    /////////////////////////////////////////////////////////////////
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Id
    @Column(
            name = "borrow_id",
            unique = true
    )
    private Long id;
    @ManyToOne(
            targetEntity = Kiosk.class
    )
    @JoinColumn(name = "slot_id")
    private Kiosk kiosk;
    @ManyToOne(
            targetEntity = Student.class
    )
    @JoinColumn(
            name = "student_id"
    )
    private Student student;
    @OneToMany(
            mappedBy = "borrowed_book"
    )
    private Set<Fines> fines;
    @OneToMany(
        mappedBy = "borrowed_book",
            fetch = FetchType.LAZY
    )
    private Set<EmailNotification> notifications;
    private Timestamp checkout_timestamp;
    private Timestamp checkin_timestamp;
    private String isbn;
    private Long copy_id;
    @Column(
            name = "duration"
    )
    private Double duration;
    /////////////////////////////////////////////////////////////////

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

//    public static void main(String[] args) throws InterruptedException {
//        Borrow_Book bb = new Borrow_Book();
//        bb.setCheckout_timestamp(Timestamp.valueOf(LocalDateTime.now()));
//        bb.setIsbn(Set.of("janjpsfainuf"));
//        Long elapsedTime = 0L;
//        int cnt = 0;
//        // Duration
//        while(cnt < 600){
//            Thread.sleep(1000L);
//            elapsedTime = (Timestamp.valueOf(LocalDateTime.now()).getTime() - bb.getCheckout_timestamp().getTime());
//            log.info("| Iteration: {} |---| Elapsed Time: {}",cnt,elapsedTime);
//            cnt++;
//        }
//        log.info("| Iteration: {} |---| Elapsed Time: {}",cnt,elapsedTime);
//    }

}
