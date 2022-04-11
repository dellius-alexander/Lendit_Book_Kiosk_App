package com.library.lendit_book_kiosk.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import com.library.lendit_book_kiosk.LendITBookKioskApplication;
import org.apache.commons.lang.NullArgumentException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.springframework.boot.test.context.SpringBootTest;


@ContextConfiguration({"classpath:testng-context.xml"})
@SpringBootTest(classes = {LendITBookKioskApplication.class})
public abstract class BrowserOptions extends AbstractTestNGSpringContextTests {
    private static final Logger log = LoggerFactory.getLogger(BrowserOptions.class);

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Sets up the remote web driver instance
     * @param browserType represents browser type
     * @param hubURL url to selenium hub url
     * @return	The RemoteWebDriver if successfully created
     */
    @BeforeClass(alwaysRun = true)
    @Parameters(value = {"browserType","hubURL"})
    public void setupThread(String browserType, String hubURL)
    {
        log.info("BrowserType: {}, HubURL: {}",browserType,hubURL);
        if(browserType == null){browserType = "chrome";}
        if(hubURL == null){hubURL = "http://127.0.0.1:4444/wd/hub";}
        log.info("BrowserType: {}, HubURL: {}",browserType,hubURL);
        DesiredCapabilities desiredCapabilities = null;
        try {
            if(browserType.equalsIgnoreCase("chrome")){
                ChromeOptions options = new ChromeOptions();
//                // Add the WebDriver proxy capability.
//                Proxy proxy = new Proxy();
//                proxy.setHttpProxy(appURL);
//                options.setCapability("proxy", proxy);
                options.setCapability("platform", Platform.LINUX);
                // start chrome maximized and create set temp user profile
                options.addArguments(Arrays.asList("start-maximized","user-data-dir=/tmp/temp_profile"));
                options.setAcceptInsecureCerts(true);
                options.setPlatformName("Linux");
                options.setBrowserVersion("100.0");
                options.setCapability("se:noVncPort", 7900);
                options.setCapability("se:recordVideo", true);
                options.setCapability("se:timeZone", "US/Pacific");
                options.setCapability("se:screenResolution", "1920x1080" );
//                options.addArguments(whitelistedIps="");
                // Add a ChromeDriver-specific capability.
//                options.addExtensions(new File("/path/to/extension.crx"));
//                ChromeDriver driver = new ChromeDriver(options);
                driver.set(new RemoteWebDriver(new URL(hubURL), options));
            }
            else if (browserType.equalsIgnoreCase("safari")){
                desiredCapabilities = new DesiredCapabilities(new SafariOptions());
                desiredCapabilities.setBrowserName("safari");
                desiredCapabilities.setAcceptInsecureCerts(true);
                desiredCapabilities.setJavascriptEnabled(true);
                desiredCapabilities.setPlatform(Platform.MAC);
                desiredCapabilities.setVersion("");

                driver.set(new RemoteWebDriver(new URL(hubURL), new SafariOptions()));
            }
            else if (browserType.equalsIgnoreCase("firefox")) {
//                // create now FirefoxProfile
                FirefoxProfile profile = new FirefoxProfile();
//                // Add the WebDriver proxy capability.
//                // httpProxy – the proxy host, expected format is hostname:1234
//                Proxy proxy = new Proxy();
//                proxy.setHttpProxy(hubURL);
                profile.setAcceptUntrustedCertificates(true);
                profile.setAssumeUntrustedCertificateIssuer(false);
                //Use No Proxy Settings
                profile.setPreference("network.proxy.type", 0);
                //Set Firefox profile to capabilities
                // create FirefoxOptions and add the profile to FirefoxOptions
                FirefoxOptions options = new FirefoxOptions();
                options.setProfile(profile);
                options.setCapability("webSocketUrl",true);
                options.setCapability("se:noVncPort", "7900");
                options.setCapability("se:vncEnabled",true);
                options.setCapability("se:recordVideo", true);
                options.setCapability("se:timeZone", "US/Pacific");
                options.setCapability("se:screenResolution", "1920x1080" );
                options.setAcceptInsecureCerts(true);
                options.setPlatformName("Linux");
                options.setBrowserVersion("99.0");
                driver.set(new RemoteWebDriver(new URL(hubURL),options));
            }
            else if (browserType.equalsIgnoreCase("edge")) {

//                desiredCapabilities = new DesiredCapabilities(new InternetExplorerOptions());
//                desiredCapabilities.setBrowserName("internet explorer");
//                desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//                desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//                desiredCapabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, "accept");
//                desiredCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
//                desiredCapabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
//                desiredCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
//                desiredCapabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
//                desiredCapabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
//                desiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
//                desiredCapabilities.setCapability("javascriptEnabled", true);
//                desiredCapabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
//                desiredCapabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "about:blank");
//                desiredCapabilities.setCapability(InternetExplorerDriver.LOG_LEVEL, "DEBUG");
//                desiredCapabilities.setPlatform(Platform.LINUX);
//                driver.set(new RemoteWebDriver(new URL(hubURL), desiredCapabilities));

                EdgeOptions options = new EdgeOptions();
                options.setPageLoadStrategy(PageLoadStrategy.EAGER.toString());
                options.setCapability("se:recordVideo", true);
                options.setCapability("se:timeZone", "US/Pacific");
                options.setCapability("se:screenResolution", "1920x1080" );
                driver.set(new RemoteWebDriver(new URL(hubURL), options));
            }
            if (driver.get() == null){
                throw new NullArgumentException("RemoteWebDriver instance is null...\n " + driver.get().toString());
            }
            log.info(browserType.toUpperCase() +
                    " RemoteWebDriver was created successfully..............!!!\n"+
                    "Launching Grid..............!!!\n"
            );
            log.info(driver.get().toString());
        }catch (NullArgumentException nae) {
            log.error(nae.getMessage());
        } catch (SecurityException se) {
            log.error(se.getMessage());
        } catch (NullPointerException npe) {
            log.error(npe.getMessage());
        } catch (IllegalArgumentException iae) {
            log.error(iae.getMessage());
        } catch (MalformedURLException mue) {
            log.error(mue.getMessage());
        }
    }

    public WebDriver getDriver()
    {
        log.info("Getting WebDriver.........");
        return driver.get();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown()
    {
        driver.get().quit();
        log.info("Tearing down session.........");
    }

    @AfterSuite(alwaysRun = true)
    public void remove()
    {
        driver.remove();
        log.info("Session terminated successfully.........");
    }


}
