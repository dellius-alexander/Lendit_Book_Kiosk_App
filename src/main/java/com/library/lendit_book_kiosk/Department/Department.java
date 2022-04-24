package com.library.lendit_book_kiosk.Department;

import com.library.lendit_book_kiosk.Course.Course;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "department")
public class Department implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Department.class);
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Column(
            name = "department",
            unique = true
    )
    private String department;
    @OneToMany(
            mappedBy = "department"
    )
    private Set<Course> course;

    public Department(String department) {
        this.department = department;
    }
    public Department() {
    }
}
