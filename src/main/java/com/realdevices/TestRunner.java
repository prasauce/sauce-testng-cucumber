package com.realdevices;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.openqa.selenium.MutableCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources",
        glue = "com.realdevices"
)
public class TestRunner extends AbstractTestNGCucumberTests {

    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    @Parameters({"deviceName","platformVersion"})
    @BeforeClass
    public void setUp(String deviceName, String platformVersion) throws MalformedURLException {
        // This method can be used to set up any additional configurations if needed
        {
            MutableCapabilities caps = new MutableCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:app", "storage:filename=mda-2.0.1-22.apk");
            caps.setCapability("appium:deviceName", deviceName);
            caps.setCapability("appium:platformVersion", platformVersion);
            caps.setCapability("appium:deviceOrientation", "portrait");
            caps.setCapability("appium:automationName", "UiAutomator2");

            MutableCapabilities sauceOptions = new MutableCapabilities();
            sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
            sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
            sauceOptions.setCapability("build", "Cucumber-Examples");
            sauceOptions.setCapability("name", "Cucumber Android Test");
            sauceOptions.setCapability("appiumVersion", "latest");

            caps.setCapability("sauce:options", sauceOptions);
            URL url = new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub");
            driver.set(new AndroidDriver(url, caps));
        }

    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (getDriver() != null) {
            getDriver().executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
            getDriver().quit();
            driver.remove();
        }
    }

}
