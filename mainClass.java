import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.example.App;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;

public class mainClass {
    String deviceName = "Honeywell CT60";
    public static AndroidDriver driverApp;
    String demoAppPath = "C:\\Users\\s.alwraikat\\CourierApp\\AppiumLearning\\src\\main\\resources\\ApiDemos-debug.apk";
    String storeAppPath ="C:\\Users\\s.alwraikat\\CourierApp\\AppiumLearning\\src\\main\\resources\\General-Store.apk";
    @BeforeTest
    public void mobileAppSetup() throws InterruptedException, IOException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(deviceName);
        //options.setApp(demoAppPath);
        options.setApp(storeAppPath);
        //android driver object
        driverApp = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        System.out.print("test before");
    }

    @Test
    public void longPress() throws InterruptedException {
        driverApp.findElement(AppiumBy.accessibilityId("Views")).click();
        driverApp.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc=\"Expandable Lists\"]")).click();
        driverApp.findElement(AppiumBy.accessibilityId("1. Custom Adapter")).click();
        WebElement elem = driverApp.findElement(AppiumBy.xpath("//android.widget.TextView[@text='People Names']"));
        ((JavascriptExecutor) driverApp).executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) elem).getId(), "duration", 2000));
        Thread.sleep(2000);


    }

    @Test
    public void scrollingTest() {
        driverApp.findElement(AppiumBy.accessibilityId("Views")).click();
        //scroll until find text
        // driverApp.findElement((AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Tabs\"));")));
// scroll down until end

        boolean canScrollMore;
        do {
            canScrollMore = (Boolean) ((JavascriptExecutor) driverApp).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 200,
                    "direction", "down",
                    "percent", 3.0
            ));

        } while (canScrollMore);

    }

    @Test
    public void swappingTest() {
        driverApp.findElement(AppiumBy.accessibilityId("Views")).click();
        driverApp.findElement(AppiumBy.accessibilityId("Gallery")).click();
        driverApp.findElement(By.xpath("//android.widget.TextView[@content-desc=\"1. Photos\"]")).click();
        WebElement firstPhoto = driverApp.findElement(By.xpath("//android.widget.ImageView[1]"));
        Assert.assertEquals(firstPhoto.getAttribute("focusable"), "true");
        ((JavascriptExecutor) driverApp).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) firstPhoto).getId(),
                "direction", "left",
                "percent", 0.75
        ));

        Assert.assertEquals(firstPhoto.getAttribute("focusable"), "false");
    }

    @Test
    public void swapWithParams(WebElement eles, String directions) {
        driverApp.findElement(AppiumBy.accessibilityId("Views")).click();
        driverApp.findElement(AppiumBy.accessibilityId("Gallery")).click();
        driverApp.findElement(By.xpath("//android.widget.TextView[@content-desc=\"1. Photos\"]")).click();
        ((JavascriptExecutor) driverApp).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) eles).getId(),
                "direction", directions,
                "percent", 0.75
        ));

        Assert.assertEquals(eles.getAttribute("focusable"), "false");


    }

    @Test
    public void dragAndDrop() throws InterruptedException {
        driverApp.findElement((AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Views\"));")));
        driverApp.findElement(AppiumBy.accessibilityId("Views")).click();
        driverApp.findElement(AppiumBy.accessibilityId("Drag and Drop")).click();

        WebElement drageElem = driverApp.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        ((JavascriptExecutor) driverApp).executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) drageElem).getId(),
                "endX", 619,
                "endY", 690
        ));

        Thread.sleep(2000);
        String dropText = driverApp.findElement(By.id("io.appium.android.apis:id/drag_result_text")).getText();
        Assert.assertEquals(dropText, "Dropped!");
    }


    @Test
    public void miscelloTest() throws InterruptedException {
        driverApp.findElement(AppiumBy.accessibilityId("Preference")).click();
        driverApp.findElement(AppiumBy.accessibilityId("3. Preference dependencies")).click();
        driverApp.findElement(AppiumBy.id("android:id/checkbox")).click();
        Thread.sleep(200);
        DeviceRotation landScape = new DeviceRotation(0, 0, 90);
        //driverApp.rotate(landScape);
        driverApp.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.RelativeLayout/android.widget.TextView")).click();
        String alertText = driverApp.findElement(AppiumBy.id("android:id/alertTitle")).getText();
        Assert.assertEquals(alertText, "WiFi settings");
        driverApp.setClipboardText("TEST TYPING SAJA");
        driverApp.findElement(AppiumBy.id("android:id/edit")).sendKeys(driverApp.getClipboardText());
        driverApp.pressKey(new KeyEvent(AndroidKey.ENTER));
        //driverApp.findElement(AppiumBy.id("android:id/edit")).sendKeys("Saja Test");
        driverApp.findElement(AppiumBy.id("android:id/button1")).click();
        driverApp.pressKey(new KeyEvent(AndroidKey.BACK));
        driverApp.pressKey(new KeyEvent(AndroidKey.HOME));


    }

    @Test
    public void appPackage(){
        Activity openPreference= new Activity("io.appium.andriod.apis","io.appium.andriod.apis.preference.PreferenceDependencies");
        driverApp.startActivity(openPreference);
    }
}

