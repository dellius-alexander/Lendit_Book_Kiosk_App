package com.library.lendit_book_kiosk.Student;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

// Tells Hibernate to make a table out of this class
@Entity  // Tells Hibernate to make a table out of this class
@Table(name = "major")
public class Major implements Serializable {
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
            // strategy = AUTO
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Column(
            name = "major_id",
            unique = true,
            columnDefinition = "bigint",
            nullable = false)
    private Long id;
    @Column(
            name = "major",
            columnDefinition = "varchar(224)"
//            unique = true
    )
    private String major;
    @ManyToMany( mappedBy = "majors"
            )
    private Set<Student> student;
    ///////////////////////////////////////////////////////
    public Major(Long id, String major){
        this.id = id;
        this.major = major;
    }
    public Major(String major){
        this.major = major;
    }

    public Major() {
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(this.id, this.major);
//    }

    public Long getId() {
        return this.id;
    }

    public String getMajor() {
        return this.major;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Major)) return false;
        final Major other = (Major) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$major = this.getMajor();
        final Object other$major = other.getMajor();
        if (!Objects.equals(this$major, other$major)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Major;
    }
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $major = this.getMajor();
        result = result * PRIME + ($major == null ? 43 : $major.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\":\"" + this.getId() +
                ",\n\"major\":\"" + this.getMajor() + "\""+
                "\n}";
    }
}
