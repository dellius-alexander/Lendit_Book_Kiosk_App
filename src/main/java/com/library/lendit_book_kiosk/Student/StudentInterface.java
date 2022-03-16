package com.library.lendit_book_kiosk.Student;

//import com.library.lendit_book_kiosk.User.User;

import com.library.lendit_book_kiosk.User.User;

import java.io.Serializable;
import java.util.Set;
//import java.util.UUID;

public interface StudentInterface extends Serializable {


    boolean canEqual(Object other);

    Long getId();

    java.util.Set<Major> getMajors();

//    Set<User> getUsers();
//
//    User getUser();
//
//    void setUsers(Set<User> users);

    void setEnrolled(boolean enrolled);

    boolean getEnrolled();

    void setId(Long id);

    void setMajors(java.util.Set<Major> majors);

//    void setUser(User user);

    boolean equals(Object o);

    int hashCode();

    String toString();

    int compareTo(Student s);

    int compareTo(Object s);
}
