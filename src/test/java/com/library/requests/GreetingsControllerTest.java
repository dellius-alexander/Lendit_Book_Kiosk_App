package com.library.requests;


import com.hackerrank.test.utility.Order;
import com.hackerrank.test.utility.OrderedTestRunner;
import com.hackerrank.test.utility.TestWatchman;
// Import your application 
import com.library.lendit_book_kiosk.LenditBookKioskApplication;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(OrderedTestRunner.class)
@SpringBootTest(classes = LenditBookKioskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
// @SpringBootTest(classes = LenditBookKioskApplication.class)
@AutoConfigureMockMvc
public class GreetingsControllerTest {
    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Rule
    public TestWatcher watchman = TestWatchman.watchman;

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void setUpClass() {
        TestWatchman.watchman.registerClass(GreetingsControllerTest.class);
    }

    @AfterClass
    public static void tearDownClass() {
        TestWatchman.watchman.createReport(GreetingsControllerTest.class);
    }

    /**
     * It tests response to be "Hello Spring!"
     * @throws Exception
     */
    @Test
    @Order(1)
    public void greetSpring() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        Assert.assertEquals(response, "<h1 style='margin:auto; width:80%; padding:15px;' >Welcome to the LendIT Book Kiosk Application! </h1></br><p style='font-size:1.5rem; margin:auto; width:80%; padding:15px;' >You have successfully setup your development environment...</p>");
    }
    /**
     * It tests response to be "Hello Java!"
     * @throws Exception
     */
    @Test
    @Order(2)
    public void greetJava() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student/Jane Doe"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Assert.assertEquals(response,  "[{\"id\":1,\"name\":\"Jane Doe\",\"email\":\"JaneDoe@gmail.com\",\"dob\":\"1989-01-06\",\"age\":35,\"major\":\"CIS\"}]");
    }

    /**
     * It tests response to be "Hello RodJohnson!"
     * @throws Exception
     */
    @Test
    @Order(3)
    public void greetRodJohnson() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student/Rod Johnson"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        Assert.assertEquals(response, "[{\"id\":1,\"name\":\"Rod Johnson\",\"email\":\"RodJohnson@gmail.com\",\"dob\":\"1989-01-06\",\"age\":35,\"major\":\"CIS\"}]");
    }
}
