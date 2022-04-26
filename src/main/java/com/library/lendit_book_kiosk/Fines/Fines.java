package com.library.lendit_book_kiosk.Fines;

import com.library.lendit_book_kiosk.Book.Borrow.Borrow_Book;
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
@Table(name = "fines")
public class Fines implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Fines.class);
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Column(
            name = "fine_id",
            unique = true
    )
    private Long id;
    private Double fine;
    private Double balance;
    private Double amount_paid_year_to_date;
    private boolean paid_in_full;
    @ManyToOne(
            targetEntity = Borrow_Book.class
    )
    @JoinColumn(name = "borrow_id")
    private Borrow_Book borrowed_book;
    ///////////////////////////////////////////////////////

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
