package com.library.lendit_book_kiosk_test;

/////////////////////////////////////////////////////////////////////
import static org.assertj.core.api.Assertions.assertThat;

import com.library.lendit_book_kiosk.WebApp.Login.LoginController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
/////////////////////////////////////////////////////////////////////
@SpringBootTest(classes = {LoginController.class})
public class LenditBookKioskApplicationTests {

    @Autowired
    private LoginController loginController;

    @Test
    void contextLoads() {
        assertThat(loginController).isNotNull();
    }

}