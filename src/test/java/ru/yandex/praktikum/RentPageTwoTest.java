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
public class RentPageTwoTest {

    private WebDriver driver;
    private final String name;
    private final String surname;
    private final String address;
    private final int dataIndex;
    private final String phone;
    private final String date;
    private final String period;
    private final String colour;
    private final String comment;

    public RentPageTwoTest(String name, String surname, String address, int dataIndex, String phone, String date, String period, String colour, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.dataIndex = dataIndex;
        this.phone = phone;
        this.date = date;
        this.period = period;
        this.colour = colour;
        this.comment = comment;
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
                {"Гарри", "Поттер", "ул. Тисовая, д. 4", 5, "62426242624", "29.08.2024", "двое суток", "black", "Позвоните за час до приезда."},
                {"Фродо", "Бэггинс", "Ривенделл, д. 567", 7, "89057899955", "29.08.2024", "двое суток", "grey", "Не звоните в дверь, так как собака дома и она будет гавкать"},
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
        RentDetailsPage rentDetailsPage = new RentDetailsPage(driver);
        rentDetailsPage.printDateField(date);
        rentDetailsPage.clickRentPeriodField(period);
        rentDetailsPage.selectScooterColour(colour);
        rentDetailsPage.printComment(comment);
        rentDetailsPage.clickThisPageOrderButton();
        assertTrue("Всплывающее окно не появилось", rentDetailsPage.isYesButtonVisible());
        rentDetailsPage.clickYesButton();
        assertTrue("Заказ не оформлен", rentDetailsPage.checkOrder());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}