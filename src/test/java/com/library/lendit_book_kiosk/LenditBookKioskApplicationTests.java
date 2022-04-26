package com.library.lendit_book_kiosk;
/////////////////////////////////////////////////////////////////////
import static org.assertj.core.api.Assertions.assertThat;

import com.library.lendit_book_kiosk.Listener.CustomListener;
import com.library.lendit_book_kiosk.Student.StudentController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.ITestNGListener;
import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;

/////////////////////////////////////////////////////////////////////
@SpringBootTest
class LenditBookKioskApplicationTests {

    @Autowired
    private StudentController studentController;

    @Test
    public void contextLoads() {
        assertThat(studentController).isNotNull();
    }

    public static void main(String[] args) {

        TestNG testng = new TestNG();
        testng.setTestClasses(
                new Class[] {
                        com.library.lendit_book_kiosk.Selenium.ParallelTests.class,

                });
        List<Class<? extends ITestNGListener>> listeners = new ArrayList<>();
        listeners.add(CustomListener.class);
        testng.setListenerClasses(listeners);
        testng.run();
    }
}