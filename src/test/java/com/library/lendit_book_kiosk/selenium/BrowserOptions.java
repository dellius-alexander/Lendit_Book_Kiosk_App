package com.library.lendit_book_kiosk.selenium;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.lang.NullArgumentException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

public abstract class BrowserOptions {

    public static final Logger log = LoggerFactory.getLogger(BrowserOptions.class);
    protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

    /**
     * Sets up the remote web driver instance
     * @param browserType 	Supply a single name: [ "chrome", "firefox", "iexplore" ]
     * @return				The RemoteWebDriver if successfully created
     */
    @BeforeMethod
    @BeforeClass
    @Parameters(value = {"browserType","port","hubURL"})
    public void setupThread(String browserType, String port, String hubURL)
    {
       log.info("\nBROWSER TYPE: {}\nHUB-URL: {}\n", browserType, hubURL);
       DesiredCapabilities desiredCapabilities = null;
        try {
            if(browserType.equalsIgnoreCase("chrome")){
                ChromeOptions chromeOptions = new ChromeOptions();
                // Add the WebDriver proxy capability.
//                Proxy proxy = new Proxy();
//                proxy.setHttpProxy(appURL);
//                chromeOptions.setCapability("proxy", proxy);
                chromeOptions.setCapability("platform", Platform.LINUX);
                // start chrome maximized and create set temp user profile
                chromeOptions.addArguments(Arrays.asList("start-maximized","user-data-dir=/tmp/temp_profile"));
                chromeOptions.setAcceptInsecureCerts(true);
                chromeOptions.setPlatformName("Linux");
                chromeOptions.setBrowserVersion("100.0");
                chromeOptions.setCapability("se:noVncPort", 7900);

//                chromeOptions.addArguments(whitelistedIps="");
                // Add a ChromeDriver-specific capability.
//                chromeOptions.addExtensions(new File("/path/to/extension.crx"));
//                ChromeDriver driver = new ChromeDriver(chromeOptions);

                driver.set(new RemoteWebDriver(new URL(hubURL), chromeOptions));
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
                // create now FirefoxProfile
                FirefoxProfile profile = new FirefoxProfile();
//                // Add the WebDriver proxy capability.
//                Proxy proxy = new Proxy();
//                proxy.setHttpProxy(appURL);
                profile.setAcceptUntrustedCertificates(true);
                profile.setAssumeUntrustedCertificateIssuer(false);
                // create FirefoxOptions and add the profile to FirefoxOptions
                FirefoxOptions options = new FirefoxOptions();
//                options.setProxy(proxy);
                options.setProfile(profile);
//                options.setCapability("se:noVncPort", "7900");
//                options.setCapability("se:vncEnabled",true);
                options.setAcceptInsecureCerts(true);
                options.setPlatformName("Linux");
                options.setBrowserVersion("99.0");
                driver.set(new RemoteWebDriver(new URL(hubURL), options));
            }
            else if (browserType.equalsIgnoreCase("ie")) {
                desiredCapabilities = new DesiredCapabilities(new InternetExplorerOptions());
                desiredCapabilities.setBrowserName("internet explorer");
                desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                desiredCapabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, "accept");
                desiredCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                desiredCapabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
                desiredCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                desiredCapabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
                desiredCapabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
                desiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
                desiredCapabilities.setCapability("javascriptEnabled", true);
                desiredCapabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
                desiredCapabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "about:blank");
                desiredCapabilities.setCapability(InternetExplorerDriver.LOG_LEVEL, "DEBUG");
                desiredCapabilities.setPlatform(Platform.WINDOWS);

                driver.set(new RemoteWebDriver(new URL(hubURL), desiredCapabilities));
            }
            if (driver.get() == null){
                throw new NullArgumentException("RemoteWebDriver instance is null...\n " + driver.get().toString());
            }
            log.info(browserType.toUpperCase() + " RemoteWebDriver was created successfully..............!!!\n"+
                    "Launching Grid..............!!!\n");
            log.info("\nWEB DRIVER: {}\n", driver.get().toString());
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

    public RemoteWebDriver getDriver(){
        log.info("Getting WebDriver.........");
        return driver.get();}

    @AfterClass
    public void tearDown()
    {
        driver.get().quit();
        log.info("Tearing down session.........");
    }

    @AfterClass
    public void remove() {
        driver.remove();
        log.info("Session terminated successfully.........");
    }


}
