package ru.yandex.praktikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class OrderButtonsTest {
    private WebDriver driver;

    @Before
    public void initDriver() {
        String browser = System.getProperty("browser", "chrome");
        if ("firefox".equals(browser)) {
            startFirefox();
        } else {
            startChrome();
        }
    }

    public void startChrome() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public void startFirefox() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void topOrderButtonTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickAcceptCookie();
        mainPage.waitMainPageHeader();
        mainPage.clickTopOrderButton();
        PersonalDataPage personalDataPage = new PersonalDataPage(driver);
        personalDataPage.checkHeader();
    }

    @Test
    public void LowerOrderButtonTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickAcceptCookie();
        mainPage.waitMainPageHeader();
        mainPage.clickBottomOrderButton();
        PersonalDataPage personalDataPage = new PersonalDataPage(driver);
        personalDataPage.checkHeader();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}