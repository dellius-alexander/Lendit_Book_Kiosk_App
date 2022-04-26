package com.library.lendit_book_kiosk.Selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;


@ContextConfiguration({"classpath:testng-context.xml"})
public abstract class BrowserOptions extends AbstractTestNGSpringContextTests {
    private static final Logger log = LoggerFactory.getLogger(BrowserOptions.class);
    // ThreadLocal will be used to manage each driver instance and process Id's associated with drivers
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Sets up the remote web driver instance
     * @param browserType represents browser type
     * @param hubURL url to Selenium hub url
     * @return	The RemoteWebDriver if successfully created
     */
    @BeforeClass(alwaysRun = true)
    @Parameters(value = {"browserType","hubURL","appURL"})
    public void setupThread(String browserType, String hubURL, String appURL)
    {
        log.info("BrowserType: {}, HubURL: {}",browserType,hubURL);
        DesiredCapabilities desiredCapabilities = null;
        try {
            if (browserType.equalsIgnoreCase("firefox")) {
//                // create now FirefoxProfile
                FirefoxProfile profile = new FirefoxProfile();
                // Add the WebDriver proxy capability.
                // httpProxy – the proxy host, expected format is hostname:1234
//                Proxy proxy = new Proxy();
//                proxy.setHttpProxy(appURL);
                profile.setAcceptUntrustedCertificates(true);
                profile.setAssumeUntrustedCertificateIssuer(false);
                //Use No Proxy Settings
//                profile.setPreference("network.proxy.type", 0);
                //Set Firefox profile to capabilities
                // create FirefoxOptions and add the profile to FirefoxOptions
                FirefoxOptions options = new FirefoxOptions();
//                options.setProxy(proxy);
                // Enable screen recording
                options.setCapability("node_screen_recording", true);
                // Set the recorder timeout to 60 seconds, The Screen recording will be stopped after 60 seconds
                options.setCapability("node_recording_timeout", 120);
                options.setProfile(profile);
                options.setCapability("webSocketUrl",true);
                options.setCapability("se:noVncPort", "7900");
                options.setCapability("se:vncEnabled",true);
                options.setCapability("se:recordVideo", true);
                options.setCapability("se:timeZone", "america/new_york");
                options.setCapability("se:screenResolution", "1920x1080" );
                options.setCapability("se:probesize", "256" );
                options.setAcceptInsecureCerts(true);
                options.setPlatformName("Linux");
                options.setBrowserVersion("99.0");
                driver.set(new RemoteWebDriver(new URL(hubURL),options));
            }
            else if(browserType.equalsIgnoreCase("chrome")){
                ChromeOptions options = new ChromeOptions();
//                // Add the WebDriver proxy capability.
//                Proxy proxy = new Proxy();
//                proxy.setHttpProxy(appURL);
//                options.setCapability("proxy", proxy);
                // Enable screen recording
                options.setCapability("node_screen_recording", true);
                // Set the recorder timeout to 60 seconds, The Screen recording will be stopped after 60 seconds
                options.setCapability("node_recording_timeout", 120);
                options.setCapability("platform", Platform.LINUX);
                // start chrome maximized and create set temp user profile
                options.addArguments(Arrays.asList("start-maximized","user-data-dir=/tmp/temp_profile"));
                options.setAcceptInsecureCerts(true);
                options.setPlatformName("Linux");
                options.setBrowserVersion("100.0");
                options.setCapability("se:noVncPort", 7900);
                options.setCapability("se:recordVideo", true);
                options.setCapability("se:timeZone", "america/new_york");
                options.setCapability("se:screenResolution", "1920x1080" );
                options.setCapability("se:probesize", "256" );
//                options.addArguments(whitelistedIps="");

                driver.set(new RemoteWebDriver(new URL(hubURL), options));
            }
            else if (browserType.equalsIgnoreCase("safari")){
                desiredCapabilities = new DesiredCapabilities(new SafariOptions());
                desiredCapabilities.setBrowserName("safari");
                desiredCapabilities.setAcceptInsecureCerts(true);
                desiredCapabilities.setJavascriptEnabled(true);
                desiredCapabilities.setPlatform(Platform.MAC);
                desiredCapabilities.setVersion("");
                // Enable screen recording
                desiredCapabilities.setCapability("node_screen_recording", true);
                // Set the recorder timeout to 60 seconds, The Screen recording will be stopped after 60 seconds
                desiredCapabilities.setCapability("node_recording_timeout", 120);

                driver.set(new RemoteWebDriver(new URL(hubURL), new SafariOptions()));
            }
            else if (browserType.equalsIgnoreCase("edge")) {

                EdgeOptions options = new EdgeOptions();
                // Enable screen recording
                options.setCapability("node_screen_recording", true);
                // Set the recorder timeout to 60 seconds, The Screen recording will be stopped after 60 seconds
                options.setCapability("node_recording_timeout", 120);
                options.setPageLoadStrategy(PageLoadStrategy.EAGER.toString());
                options.setCapability("se:recordVideo", true);
                options.setCapability("se:timeZone", "america/new_york");
                options.setCapability("se:screenResolution", "1920x1080" );
                options.setCapability("se:probesize", "256" );

                driver.set(new RemoteWebDriver(new URL(hubURL), options));
            }
            if (driver.get() == null){
                throw new NullArgumentException("RemoteWebDriver instance is null...\n " + driver.get().toString());
            }
            log.info( browserType.toUpperCase() + " RemoteWebDriver was created successfully..............!!!\n" );
            log.info(driver.get().toString());
            log.info( "Starting Remote Session..............!!!\n" );

        }catch (NullArgumentException nae) {
            log.error(nae.getMessage());
            nae.printStackTrace();
        } catch (SecurityException se) {
            log.error(se.getMessage());
            se.printStackTrace();
        } catch (NullPointerException npe) {
            log.error(npe.getMessage());
            npe.printStackTrace();
        } catch (IllegalArgumentException iae) {
            log.error(iae.getMessage());
            iae.printStackTrace();
        } catch (MalformedURLException mue) {
            log.error(mue.getMessage());
            mue.printStackTrace();
        }
    }

    /**
     * Get the webdriver
     * @return
     */
    public WebDriver getDriver() throws InterruptedException {
        log.info("Getting WebDriver.........");
        return driver.get();
    }

    /**
     * Teardown treads
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown()
    {
        try{
            driver.get().quit();
            log.info("Tearing down session.........");
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }

    @AfterSuite(alwaysRun = true)
    public void remove()
    {
        driver.remove();
        log.info("Session terminated successfully.........");
    }


}
