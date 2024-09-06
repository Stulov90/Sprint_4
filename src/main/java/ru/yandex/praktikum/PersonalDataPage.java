package ru.yandex.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalDataPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    // Страница "Для кого самокат" (проверка загрузки)
    private final By personalDataPageHeader = By.className("Order_Header__BZXOb");
    // Поле "Имя"
    private final By nameField = By.xpath(".//input[@placeholder='* Имя']");
    // Поле "Фамилия"
    private final By surnameField = By.xpath(".//input[@placeholder='* Фамилия']");
    // Поле "Адрес: куда привезти заказ"
    private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    // Поле "Станция метро"
    private final By metroStationField = By.xpath(".//input[@placeholder='* Станция метро']");
    // Поле "Телефон: на него позвонит курьер"
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    // Кнопка "Далее"
    private final By nextPageButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");
    // Страница "Про аренду" (для проверки перехода)
    private final By aboutRentPageHeader = By.className("Order_Header__BZXOb");

    // Делаем конструктор для инициализации драйвера и ожидания
    public PersonalDataPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Методы взаимодействия с элементами страницы "Для кого самокат"

    // 1. Проверяем загрузку страницы
    public void checkHeader() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(personalDataPageHeader)).isDisplayed();
    }

    // 2. Заполняем поле "Имя"
    public void printName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    // 3. Заполняем поле "Фамилия"
    public void printSurname(String surname) {
        driver.findElement(surnameField).sendKeys(surname);
    }

    // 4. Заполняем поле "Адрес: куда привезти заказ"
    public void printAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    // 5. Кликаем по полю "Станция метро"
    public void clickMetroStation() {
        driver.findElement(metroStationField).click();
    }

    // 6. Выбираем станцию метро из выпадающего списка
    public void selectMetroStation(int dataIndex) {
        // Создаем локатор для станции метро из выпадающего списка
        By metroStationLocator = By.cssSelector(String.format("[data-index='%s']", dataIndex));
        // Находим нужный элемент на странице и кликаем на него
        WebElement metroStationElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(metroStationLocator));
        metroStationElement.click();
    }

    // 7. Заполняем поле "Телефон: на него позвонит курьер"
    public void printPhone(String phone) {
        driver.findElement(phoneField).sendKeys(phone);
    }

    // 8. Нажимаем кнопку "Далее" для перехода на страницу "Про аренду"
    public void clickNextPageButton() {
        driver.findElement(nextPageButton).click();
    }

    // 9. Проверяем переход на следующую страницу
    public boolean isOnAboutRentPage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(aboutRentPageHeader)).isDisplayed();
    }
}