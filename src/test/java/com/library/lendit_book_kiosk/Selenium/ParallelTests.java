package com.library.lendit_book_kiosk.Selenium;



import com.library.lendit_book_kiosk.LendITBookKioskApplication;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.*;

import java.time.LocalDateTime;

@SpringBootTest(classes = {LendITBookKioskApplication.class})
public class ParallelTests extends BrowserOptions {

    public static final Logger log = LoggerFactory.getLogger(ParallelTests.class);

    public ParallelTests(){
        super();
    }
    ///////////////////////////////////////////////////////////////////////////


    /**
     * Login to LendIT Book Kiosk
     * @param appURL
     */
    @Test(
            suiteName = "SeleniumGridDocker",
            priority = 1)
    @Parameters( value = {"appURL","username","password"} )
    public void login(String appURL, String username, String password)
    {
        boolean waiting = true;
        try{
            WebDriver driver = getDriver();
            driver.get(appURL + "/login");
            // pageload helper

                log.info("Still Waiting for page to load: {}", LocalDateTime.now());
                log.info("Current URL: {}",driver.getCurrentUrl());
                Thread.sleep(1000L);

            WebElement element = new WebDriverWait(driver,30).until(
                    ExpectedConditions.presenceOfElementLocated((By.ById.id("UserIDField")))
            );
            element.sendKeys(username);
            driver.findElement(By.id("UserPasswordField")).sendKeys(password);
            driver.findElement(By.id("LoginSubmitButton")).click();

                log.info("Still Waiting for page to load: {}", LocalDateTime.now());
                log.info("Current URL: {}",driver.getCurrentUrl());
                Thread.sleep(5000L);


        } catch (Exception e){
            log.info(e.getMessage());
        } finally {
            log.info("Finished Login Test..........");
        }
    }

    /**
     * Search for books
     */
    @Test(
            suiteName = "SeleniumGridDocker",
            priority = 2)
    @Parameters( value = {"appURL","username","password"} )
    public void searchBook(String appURL, String username, String password){
        boolean waiting = true;
        try{
            WebDriver driver = getDriver();
            driver.navigate().refresh();
            WebElement element = new WebDriverWait(driver,60).until(
                    ExpectedConditions.presenceOfElementLocated((By.ById.id("search-tab-3")))
            );
            element.click();
            driver.findElement(By.id("title")).sendKeys("potter");
            driver.findElement(By.id("submit-button-3")).submit();

                log.info("Still Waiting for page to load: {}", LocalDateTime.now());
            log.info("Window handleL: {}",driver.getWindowHandle());
                Thread.sleep(1000L);

            //to perform Scroll on application using Selenium
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,350)", "");
            // go back to previous search page
            driver.findElement(By.id("go-back-to-book-search")).click();

                log.info("Still Waiting for page to load: {}", LocalDateTime.now());
                log.info("Current URL: {}",driver.getCurrentUrl());
                Thread.sleep(5000L);

        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    ///////////////////////////////////////////////////////////////////////////
}
