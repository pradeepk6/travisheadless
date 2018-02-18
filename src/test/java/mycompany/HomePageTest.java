package mycompany;


import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class HomePageTest {

    WebDriver driver;

    @Test
    public void testGooglePage() {
        driver = WebDriverFactory.create();
    try {
        driver.get("http://www.google.com");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        WebElement queryBox = driver.findElement(By.name("q"));
        queryBox.sendKeys("headless firefox");
        WebElement searchBtn = driver.findElement(By.name("btnK"));
        searchBtn.submit();
        WebElement iresDiv = driver.findElement(By.id("ires"));
        iresDiv.findElements(By.tagName("a")).get(0).click();
        System.out.println(driver.getTitle());
    } finally {
        driver.quit();
    }
    }
}
