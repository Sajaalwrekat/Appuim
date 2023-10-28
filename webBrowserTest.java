import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

public class webBrowserTest extends baseWebClass {

    @Test
    public  void  browserTest(){
        driverApp.get("https://google.com/");
        System.out.println(driverApp.getTitle());
        driverApp.findElement(By.name("q")).sendKeys("rahulshettyacademy");
        driverApp.findElement(By.name("q")).sendKeys(Keys.ENTER);
    }

    public void scrollBrowser(){
        driverApp. get("https://rahulshettyacademy.com/angularAppdemo/");
        driverApp.findElement(By.xpath("//span[@class='navbar-toggler-icon']")).click();
        driverApp.findElement(By.cssSelector(".nav-link[routerlink='/products']")).click();
        ((JavascriptExecutor)driverApp).executeScript("Window.scrollBy(0,1000)","");
        String text = driverApp.findElement(By.cssSelector("a[href='/angularAppdemo/products/3']")).getText();
        Assert.assertEquals(text,"Devops");
    }
}
