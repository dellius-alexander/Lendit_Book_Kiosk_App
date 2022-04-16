package com.library.lendit_book_kiosk.selenium;



import com.library.lendit_book_kiosk.LendITBookKioskApplication;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.testng.annotations.*;

import java.util.Set;

import static org.apache.tomcat.util.net.SSLHostConfigCertificate.Type.EC;

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
    @Parameters( value = {"appURL"} )
    public void login(String appURL)
    {
        try{
            WebDriver driver = getDriver();
//            // just for google chrome
//            if (browserType.equalsIgnoreCase("chrome"))
//            {
//                driver.getWindowHandles().forEach(
//                    tab -> {
//                        driver.switchTo().window(tab);
//                        if (driver.getTitle().equalsIgnoreCase("What's New")) {
//                            driver.close();
//                        }
//                    }
//                );
//            }
            driver.get(appURL + "/login");
            WebElement element = new WebDriverWait(driver,30).until(
                    ExpectedConditions.presenceOfElementLocated((By.ById.id("UserIDField")))
            );
            driver.findElement(By.id("UserIDField")).sendKeys("adyos0@webs.com");
            driver.findElement(By.id("UserPasswordField")).sendKeys("NwvX65L9BWF9");
            driver.findElement(By.id("LoginSubmitButton")).submit();
            log.info("Current URL: {}",driver.getCurrentUrl());
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
    public void searchBook(){
        try{
            WebDriver driver = getDriver();
            log.info("Current URL: {}",driver.getCurrentUrl());
            WebElement element = new WebDriverWait(driver,30).until(
                    ExpectedConditions.presenceOfElementLocated((By.ById.id("SearchTab")))
            );
            element.click();
            driver.findElement(By.id("searchTitle")).sendKeys("potter");
            driver.findElement(By.id("submitButton")).submit();
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    ///////////////////////////////////////////////////////////////////////////
}
