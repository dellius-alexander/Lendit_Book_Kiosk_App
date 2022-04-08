package com.library.lendit_book_kiosk.selenium;


import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.testng.annotations.*;


public class ParallelTests extends BrowserOptions {

    public static final Logger log = LoggerFactory.getLogger(ParallelTests.class);

    public ParallelTests(){
        super();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // google settings menu link text
    static String[] settingsMenuOptionsLinkTexts = {"Search settings","Advanced search","Your data in Search","History",
            "Search help","Send feedback"};
    // google settings XPATH menu options
    static String[] settingsMenuOptionsXpath = {"//*[@id=\'fsett\']/a[1]","//*[@id=\'fsett\']/a[2]","//*[@id=\'fsett\']/a[3]",
            "//*[@id=\'fsett\']/a[4]","//*[@id=\'fsett\']/a[5]","//*[@id=\'dk2qOd\']","//*[@id=\'dk2qOd\']"};
    // google settings page title
    static String[] settingsPagesTitle = { "Search Settings", "Google Advanced Search", "Your data in Search",
            "Google - Search Customization", "Google Search Help","Google"};
    static String xpathSettingsMenu = "//*[@id=\"fsettl\"]";
    static String xpathSearchBoxString = "//*[@id=\'fakebox-input\']";
    static String driverpath = "D:\\SynologyDrive\\Clayton_State_University\\Semester\\2020_Spring\\CSCI_3601\\Drivers";
    static String url = "https://www.google.com";
    static String srcstr = "Selenium";
    static String searchResultsPageTitle = srcstr + " - Google Search";
    static By searchBoxBy_Locator = By.name("q");
    static By settingsMenuXpathBy = By.xpath(xpathSettingsMenu);

    ////////////////////////////////////////////////////////////////////////////////////////////////
//    /**
//     * Setup RemoteWebDriver for parallel testing
//     * @throws MalformedURLException
//     */
//    @BeforeClass
//    @Parameters(value = {"browserType","port"})
//    public void setup(String browserType, String port) throws MalformedURLException
//    {
//        log.info("Creating browser...............!!!");
//        remoteWebDriver =  BrowserOptions.getRemoteWebDriver(browserType,port);
//        log.info("\nREMOTE WEB DRIVER: {}\n",remoteWebDriver.toString());
//    }
    /**
     * Runs test using parallel browsers
     */
    @Test(
            suiteName = "SeleniumGridDocker"
    )
    public void runSafariTest() {
        WebDriver driver = getDriver();
//        driver.get("http://10.0.2.81:8081/login");
        driver.navigate().to("http://10.0.2.81:8081/login");
        driver.findElement(By.id("UserIDField")).sendKeys("jane@gmail.com");
        driver.findElement(By.id("UserPasswordField")).sendKeys("password");
        driver.findElement(By.id("LoginSubmitButton")).submit();
//        driver.close();

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
