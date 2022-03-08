package com.library.lendit_book_kiosk.User;


import lombok.AllArgsConstructor;
import lombok.Data;
//import org.json.JSONObject;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Table;

// Tells Hibernate to make a table out of this class
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLoginDetails {
    private static final Logger log = LoggerFactory.getLogger(UserLoginDetails.class);
    private String username;
    private String password;


}