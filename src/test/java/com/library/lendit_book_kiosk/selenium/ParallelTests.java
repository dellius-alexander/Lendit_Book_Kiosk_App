package com.library.lendit_book_kiosk.selenium;


import com.library.lendit_book_kiosk.LendITBookKioskApplication;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.*;

public class ParallelTests extends BrowserOptions {

    public static final Logger log = LoggerFactory.getLogger(ParallelTests.class);

    public ParallelTests(){
        super();
    }
    /**
     * Runs test using parallel browsers
     */
    @Test(
            suiteName = "SeleniumGridDocker",
            threadPoolSize = 3
    )
    public void runSafariTest() {

        try{
        WebDriver driver = getDriver();
        driver.get("http://10.0.2.81:8081/login");
//        driver.navigate().to("http://10.0.2.81:8081/login");
        driver.findElement(By.id("UserIDField")).sendKeys("jane@gmail.com");
        driver.findElement(By.id("UserPasswordField")).sendKeys("password");
        driver.findElement(By.id("LoginSubmitButton")).click();
        log.info("Current URL: {}",driver.getCurrentUrl());
        driver.wait(20000);
        } catch (Exception e){
            log.info(e.getMessage());
        }

    }
///////////////////////////////////////////////////////////////////////////////
//    /**
//     * Run test using Chrome browser
//     * @throws InterruptedException
//     * @throws MalformedURLException
//     */
//    @Test
//    public void runChromeTest() throws InterruptedException, MalformedURLException
//    {
//        remoteWebDriver.get(url);
//        String pageTitle = remoteWebDriver.getTitle();
//
//		/*
//		////////////////////////////////////////////////////////////////////////////////////////////////
//		// test chrome browser
//		//WebDriver chrome = getChromeDriver(driverpath);
//		//WebDriver remoteWebDriver = getRemoteWebDriver("chrome");
//		TestAuto.runTestCasesOnInputField(url, srcstr, remoteWebDriver, searchBoxBy_Locator, searchResultsPageTitle);
//		for (int i = 0; i < footerXpath.length; i++) {
//			TestAuto.runTestCasesOnFooterElements(url, remoteWebDriver, footerXpath[i], footerPagesTitles[i], pageSourcesURLs[i]);
//		}
//		for (int i = 0; i < settingsPagesTitle.length; i++) {
//			TestAuto.runTestCasesForGoogleSettings(url, remoteWebDriver, settingsMenuXpathBy, settingsMenuOptionsXpath[i], settingsPagesTitle[i], settingsMenuOptionsLinkTexts[i]);
//		}
//		*/
//    }
//    /**
//     * Run tests using Firefox browser
//     * @throws InterruptedException
//     * @throws MalformedURLException
//     */
//    @Test
//    public void runFirefoxTest() throws InterruptedException, MalformedURLException
//    {
//        remoteWebDriver.get(url);
//        remoteWebDriver.findElement(By.name("q")).sendKeys("Selenium");
//        remoteWebDriver.findElement(By.linkText("www.selenium.dev")).click();
//        /**
//         ////////////////////////////////////////////////////////////////////////////////////////////////
//         //test firefox browser
//         //WebDriver firefox = getFirefoxDriver (driverpath);
//         //WebDriver remoteWebDriver = BrowserOptions.getRemoteWebDriver("firefox");
//         TestAuto.runTestCasesOnInputField(url, srcstr, remoteWebDriver, searchBoxBy_Locator, searchResultsPageTitle);
//         for (int i = 0; i < footerXpath.length; i++) {
//         TestAuto.runTestCasesOnFooterElements(url, remoteWebDriver, footerXpath[i], footerPagesTitles[i], pageSourcesURLs[i]);
//         }
//         for (int i = 0; i < settingsPagesTitle.length; i++) {
//         TestAuto.runTestCasesForGoogleSettings(url, remoteWebDriver, settingsMenuXpathBy, settingsMenuOptionsXpath[i], settingsPagesTitle[i], settingsMenuOptionsLinkTexts[i]);
//         }
//         */
//    }

}
