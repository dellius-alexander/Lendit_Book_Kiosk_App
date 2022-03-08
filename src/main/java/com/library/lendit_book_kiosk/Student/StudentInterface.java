package com.library.lendit_book_kiosk.Student;

import com.library.lendit_book_kiosk.User.User;

public interface StudentInterface {


    boolean canEqual(Object other);

    Long getId();

    java.util.Set<Major> getMajors();

//    User getUser();

    void setId(Long id);


    void setMajors(java.util.Set<Major> majors);

//    void setUser(User user);

    boolean equals(Object o);

    int hashCode();

    String toString();

    int compareTo(Student s);

    int compareTo(Object s);
}
