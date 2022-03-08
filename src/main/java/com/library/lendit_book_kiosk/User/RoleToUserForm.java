package com.library.lendit_book_kiosk.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleToUserForm {
    private String username;
    private String role;

//    public String getUsername() {
//        return this.username;
//    }
//
//    public String getRoleName(){
//        return getRoleByRolename;
//    }
}