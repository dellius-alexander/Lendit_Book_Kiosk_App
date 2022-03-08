package com.library.lendit_book_kiosk.Student;

public interface StudentInterface {

    String getName();

    boolean canEqual(Object other);

    Long getId();

    java.util.Set<Major> getMajors();

    java.util.Set<com.library.lendit_book_kiosk.User.User> getUser();

    void setId(Long id);


    void setMajors(java.util.Set<Major> majors);

    void setUser(java.util.Set<com.library.lendit_book_kiosk.User.User> user);

    boolean equals(Object o);

    int hashCode();

    String toString();
}
