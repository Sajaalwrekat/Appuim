import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;

public class baseWebClass {
    String deviceName = "Honeywell CT60";
    public static AndroidDriver driverApp;

    @BeforeTest
    public void webBrowserSetup() throws InterruptedException, IOException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(deviceName);
        options.setChromedriverExecutable("C:\\Users\\s.alwraikat\\CourierApp\\AppiumLearning\\src\\main\\resources\\chromedriver.exe");
        options.setCapability("browserName", "Chrome");
        //android driver object
        driverApp = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        System.out.print("test before");
    }

    public Double formattedAmount(String amount) {
        Double price = Double.parseDouble(amount.substring(1));
        return price;
    }

    @AfterClass
    public void tearDown() {
        driverApp.quit();
    }
}

