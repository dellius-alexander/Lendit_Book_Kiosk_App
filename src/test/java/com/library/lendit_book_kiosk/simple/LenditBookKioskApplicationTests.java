package com.library.lendit_book_kiosk.simple;
/////////////////////////////////////////////////////////////////////
import static org.assertj.core.api.Assertions.assertThat;

import com.library.lendit_book_kiosk.Student.StudentController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
/////////////////////////////////////////////////////////////////////
@SpringBootTest
class LenditBookKioskApplicationTests {

    @Autowired
    private StudentController studentController;

    @Test
    public void contextLoads() {
        assertThat(studentController).isNotNull();
    }

}