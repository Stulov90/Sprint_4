package ru.yandex.praktikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class RentPageOneTest {

    private WebDriver driver;
    private final String name;
    private final String surname;
    private final String address;
    private final int dataIndex;
    private final String phone;

    public RentPageOneTest(String name, String surname, String address, int dataIndex, String phone) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.dataIndex = dataIndex;
        this.phone = phone;
    }

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

    @Parameterized.Parameters
    public static Object[][] orderTestData() {
        return new Object[][]{
                {"Гарри", "Поттер", "ул. Тисовая, д. 4", 5, "62426242624"},
                {"Фродо", "Бэггинс", "Ривенделл, д. 567", 7, "89057899955"}
        };
    }

    @Test
    public void orderFormTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickAcceptCookie();
        mainPage.waitMainPageHeader();
        mainPage.clickTopOrderButton();
        PersonalDataPage personalDataPage = new PersonalDataPage(driver);
        personalDataPage.checkHeader();
        personalDataPage.printName(name);
        personalDataPage.printSurname(surname);
        personalDataPage.printAddress(address);
        personalDataPage.clickMetroStation();
        personalDataPage.selectMetroStation(dataIndex);
        personalDataPage.printPhone(phone);
        personalDataPage.clickNextPageButton();
        assertTrue("Не удалось перейти на следующую страницу", personalDataPage.isOnAboutRentPage());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}