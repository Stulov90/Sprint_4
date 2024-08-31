package ru.yandex.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RentDetailsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    // Поле "Когда привезти самокат"
    private final By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    // Поле "Срок аренды"
    private final By rentPeriodField = By.className("Dropdown-arrow");
    // Чек-бокс "Чёрный жемчуг"
    private final By blackCheckbox = By.xpath(".//input[@id='black']");
    // Чек-бокс "Серая безысходность"
    private final By greyCheckbox = By.xpath(".//input[@id='grey']");
    // Поле "Комментарий для курьера"
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    // Кнопка "Заказать"
    private final By thisPageOrderButton = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");
    // Кнопка всплывающего окна
    private final By yesButton = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Да']");
    // Кнопка "Посмотреть статус"
    private final By statusButton = By.xpath(".//button[text()='Посмотреть статус']");

    // Делаем конструктор для инициализации драйвера и ожидания
    public RentDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Методы взаимодействия с элементами страницы "Про аренду"

    // 1. Заполняем поле "Когда привезти самокат"
    public void printDateField(String date) {
        driver.findElement(dateField).sendKeys(date);
    }

    // 2. Кликаем по полю "Срок аренды" и выбираем значение
    public void clickRentPeriodField(String period) {
        driver.findElement(rentPeriodField).click();
        // Создаем локатор для значения из выпадающего списка
        By rentPeriodLocator = By.xpath(String.format(".//div[@class='Dropdown-option' and text()='%s']", period));
        // Находим нужный элемент на странице и кликаем на него
        WebElement rentPeriodElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(rentPeriodLocator));
        rentPeriodElement.click();
    }

    // 3. Выбираем цвет самоката
    public void selectScooterColour(String color) {
        By checkboxLocator;
        switch (color.toLowerCase()) {
            case "black":
                checkboxLocator = blackCheckbox;
                break;
            case "grey":
                checkboxLocator = greyCheckbox;
                break;
            default:
                throw new IllegalArgumentException("Неизвестный цвет: " + color);
        }
        driver.findElement(checkboxLocator).click();
    }

    // 4. Заполняем поле "Комментарий для курьера"
    public void printComment(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    // 5. Кликаем по кнопке "Заказать"
    public void clickThisPageOrderButton() {
        driver.findElement(thisPageOrderButton).click();
    }

    // 6. Проверяем появление всплывающего окна
    public boolean isYesButtonVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(yesButton)).isDisplayed();
    }

    // 7. Кликаем по кнопке "Да" во всплывающем окне
    public void clickYesButton() {
        driver.findElement(yesButton).click();
    }

    // 8. Проверяем оформление заказа
    public boolean checkOrder() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(statusButton)).isDisplayed();
    }
}