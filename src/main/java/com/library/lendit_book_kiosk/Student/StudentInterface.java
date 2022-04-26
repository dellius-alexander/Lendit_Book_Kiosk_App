package com.library.lendit_book_kiosk.Student;



import com.library.lendit_book_kiosk.User.User;

import java.io.Serializable;

public interface StudentInterface extends Serializable {


    boolean canEqual(Object student);

    Long getStudentId();

    void setStudentId(Long Id);

    java.util.Set<Major> getMajors();

    void setEnrolled(boolean enrolled);

    boolean getEnrolled();

    void setMajors(java.util.Set<Major> majors);

    boolean equals(Object o);

    int hashCode();

    String toString();

    int compareTo(Student s);

    int compareTo(Object s);
}
