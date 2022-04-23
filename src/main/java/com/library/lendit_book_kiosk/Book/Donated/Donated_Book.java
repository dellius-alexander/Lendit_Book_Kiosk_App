package com.library.lendit_book_kiosk.Book.Donated;

import com.library.lendit_book_kiosk.Book.Book;
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
@Table(name = "donated_book")
public class Donated_Book implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Donated_Book.class);
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Column(
            name = "id",
            unique = true

    )
    private Long id;
    @ManyToOne(
            targetEntity = Book.class
    )
    @JoinColumn(name="isbn", nullable=false)
    private Book donated_books;
    private String doner_name;
    private Double estimated_value;
    /////////////////////////////////////////////////////////////////
}
