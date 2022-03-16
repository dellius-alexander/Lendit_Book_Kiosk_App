package com.library.lendit_book_kiosk.Role;

import java.io.Serializable;

public interface RoleInterface extends Serializable {
    Long getId();

    void setId(Long id);

   ROLE getRole();

    void setRole(ROLE name);

    void setDescription(String description);

    String getDescription();

    Role getRoleById(Long id);

    Role getRoleByName(ROLE name);

    Role getRoleByid(Long id);

    @Override
    boolean equals(Object o);

    boolean equals(ROLE name1, ROLE name2);

    boolean equals(Role role1, Role role2);

    @Override
    int hashCode();

    @Override
    String toString();
}
