import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class eCommerceTest extends mobileBaseClass {
    @Test
    public void fillFromSucess() throws InterruptedException {
        Thread.sleep(5000);
        driverApp.findElement(AppiumBy.id("com.androidsample.generalstore:id/nameField")).sendKeys("TEST SAJA");
        driverApp.hideKeyboard();
        driverApp.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driverApp.findElement(By.id("android:id/text1")).click();
        driverApp.findElement((AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"));")));
        driverApp.findElement(By.xpath("//android.widget.TextView[@text='Argentina']")).click();
        driverApp.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        Thread.sleep(500);
        //select product from store
        driverApp.findElement((AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Jordan 6 Rings\"));")));
        //how to click on btn when many item present and i do not know index Dynamic SELECTION
        int sizeProduct = driverApp.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
        System.out.print(sizeProduct);
        for (int i = 0; i < sizeProduct; i++) {
            String productName = driverApp.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i).getText();
            if (productName.equalsIgnoreCase("Jordan 6 Rings")) {
                driverApp.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
            }
        }
        driverApp.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        Thread.sleep(2000);
//ensure we move to expected page
        WebDriverWait wait = new WebDriverWait(driverApp, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.attributeContains(driverApp.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")), "text", "Cart"));
        String lastPageProductName = driverApp.findElement(By.id("com.androidsample.generalstore:id/productName")).getText();
        Assert.assertEquals(lastPageProductName, "Jordan 6 Rings");
    }

    @Test
    public void fillFromError() throws InterruptedException {
        Thread.sleep(5000);
        driverApp.hideKeyboard();
        driverApp.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driverApp.findElement(By.id("android:id/text1")).click();
        driverApp.findElement((AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"));")));
        driverApp.findElement(By.xpath("//android.widget.TextView[@text='Argentina']")).click();
        driverApp.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        Thread.sleep(2000);
        String toastErrorMsg = driverApp.findElement(By.xpath("(//android.widget.Toast)[1]")).getAttribute("name");
        Assert.assertEquals(toastErrorMsg, "Please enter your name");
    }


    @Test
    public void sucessCheckAmount() throws InterruptedException {
        Thread.sleep(5000);
        driverApp.findElement(AppiumBy.id("com.androidsample.generalstore:id/nameField")).sendKeys("TEST SAJA");
        driverApp.hideKeyboard();
        driverApp.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driverApp.findElement(By.id("android:id/text1")).click();
        driverApp.findElement((AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"));")));
        driverApp.findElement(By.xpath("//android.widget.TextView[@text='Argentina']")).click();
        driverApp.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        Thread.sleep(1000);
        //add item
        driverApp.findElement(By.xpath("//android.widget.TextView[@text='ADD TO CART']")).click();
        driverApp.findElement((AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"ADD TO CART\"));")));
        driverApp.findElements(By.xpath("//android.widget.TextView[@text='ADD TO CART']")).get(0).click();
        //after click on first item , the text change for first item added to cart, so i will have only one card
        // add to cart
        driverApp.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        Thread.sleep(2000);
        //loop into item shows in cart page, get price,do calcs
        List<WebElement> productPrice = driverApp.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        int countProducts = productPrice.size();
        System.out.println("count of displayed prices =" + countProducts);
        double totalPrices = 0;
        for (int i = 0; i < countProducts; i++) {
            String priceText = productPrice.get(i).getText();
            Double price = formattedAmount(priceText);
            totalPrices = totalPrices + price;
        }
        System.out.println("calacuted price= " + totalPrices);
        String actualTotalPriceTXT = driverApp.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        Double actualPrice = formattedAmount(actualTotalPriceTXT);
        System.out.println("displayed prices= " + actualPrice);

        Assert.assertEquals(totalPrices, actualPrice);

        WebElement termsConditions = driverApp.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
        longPressForElement(termsConditions);
        driverApp.findElement(By.id("android:id/button1")).click();
        driverApp.findElement(By.className("android.widget.CheckBox")).click();
        driverApp.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
        Thread.sleep(8000);
        //driver go to web == google, we need to swich context from app to web
        Set<String> context= driverApp.getContextHandles();
        for(String contextName: context) { //loop in the collection to retrive all context
            System.out.println(contextName);
        }
        Thread.sleep(1000);
        driverApp.context("WEBVIEW_com.androidsample.generalstore");
        driverApp.findElement(By.name("q")).sendKeys("Jordan time now");
        driverApp.findElement(By.name("q")).sendKeys(Keys.ENTER);
        driverApp.pressKey(new KeyEvent((AndroidKey.BACK)));
        driverApp.context("NATIVE_APP");








    }
}
