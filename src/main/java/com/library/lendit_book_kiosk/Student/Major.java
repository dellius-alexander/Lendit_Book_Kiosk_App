package com.library.lendit_book_kiosk.Student;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

// Tells Hibernate to make a table out of this class
@Entity  // Tells Hibernate to make a table out of this class
@Table(name = "majors")
public class Major implements Serializable {
    ///////////////////////////////////////////////////////
    // Table outline/fields

//    @SequenceGenerator(
//            name = "major_sequence",
//            sequenceName = "major_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            // strategy = AUTO
//            strategy = GenerationType.SEQUENCE,
//            generator = "major_sequence"
//    )
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "major_id",
            unique = true
    )
    private Long id;
    @Column(
            name = "major",
            columnDefinition = "varchar(224)"
//            unique = true
    )
//    @ManyToOne(
////            targetEntity = Student.class,
////            fetch = FetchType.EAGER,
////            cascade = CascadeType.ALL
//    )
    private String major;
    @ManyToMany(
            targetEntity = Student.class,
            mappedBy = "majors",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<Student> students;
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

    @Override
    public String toString(){
        return          "{\n"+
                        "\n\"Id\":" + getId() +
                        "\n\"major\":\"" + getMajor() + "\"" +
                        "\n}";
    }

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

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $major = this.getMajor();
        result = result * PRIME + ($major == null ? 43 : $major.hashCode());
        return result;
    }
}
