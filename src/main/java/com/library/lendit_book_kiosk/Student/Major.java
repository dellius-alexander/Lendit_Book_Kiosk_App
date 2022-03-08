package com.library.lendit_book_kiosk.Student;

import com.library.lendit_book_kiosk.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

// Tells Hibernate to make a table out of this class
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity  // Tells Hibernate to make a table out of this class
@Table(name = "Major")
public class Major {

    // Table outline/fields
    @Id
    @Column(name = "major_id")
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            // strategy = AUTO
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    ///////////////////////////////////////////////////////
    private Long id;
    private String major;
    @ManyToMany(
            targetEntity = Student.class,
            mappedBy = "majors",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<Student> students;

    public Major(Long id, String major){
        this.id = id;
        this.major = major;
    }
    public Major(String major){
        this.major = major;
    }

}
