package com.library.lendit_book_kiosk.User;

//import org.json.JSONObject;

import java.util.Objects;

public class RoleToUserForm {
    private String username;
    private String role;

    public RoleToUserForm(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public RoleToUserForm() {
    }

    public String getUsername() {
        return this.username;
    }

    public String getRole() {
        return this.role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof RoleToUserForm)) return false;
        final RoleToUserForm other = (RoleToUserForm) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (!Objects.equals(this$username, other$username)) return false;
        final Object this$role = this.getRole();
        final Object other$role = other.getRole();
        if (!Objects.equals(this$role, other$role)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RoleToUserForm;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        return result;
    }

    public String toString() {

        return "{\n"+
                "\"username\":\"" + this.getUsername() +
                "\",\n\"role\":\"" + this.getRole() +
                "\"\n}";
    }

}