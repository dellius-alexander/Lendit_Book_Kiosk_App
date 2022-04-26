package com.library.lendit_book_kiosk.Employee;

import com.library.lendit_book_kiosk.Student.Major;
import com.library.lendit_book_kiosk.User.User;
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
@Table(name = "employee")
public class Employee implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Employee.class);
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Column(
            name = "employee_id",
            unique = true

    )
    private Long id;
    private String jobTitle;
    @ManyToMany(
            targetEntity = User.class,
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_employee",
            joinColumns = @JoinColumn(name = "employee_id", nullable = false, table = "employee"),
            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false, table = "user")
    )
    private Set<User> users;
    /////////////////////////////////////////////////////////////////

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
