package com.library.lendit_book_kiosk.Role;

import java.io.Serializable;

public interface RoleInterface extends Serializable {
    Long getId();

    void setId(Long id);

    String getRole();

    void setRole(String name);

    void setDescription(String description);

    String getDescription();

    Role getRoleById(Long id);

    Role getRoleByName(String name);

    Role getRoleByid(Long id);

    @Override
    boolean equals(Object o);

    boolean equals(String name1, String name2);

    boolean equals(Role role1, Role role2);

    @Override
    int hashCode();

    @Override
    String toString();
}
