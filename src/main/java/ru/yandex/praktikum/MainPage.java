package ru.yandex.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    // Изображение элемента на главной странице сайта (для проверки загрузки страницы)
    private final By mainPageHeader = By.className("Home_Header__iJKdX");
    // Кнопка "Заказать" вверху страницы
    private final By topOrderButton = By.xpath(".//button[@class='Button_Button__ra12g' and text()='Заказать']");
    // Кнопка "Заказать" внизу страницы
    private final By bottomOrderButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");
    // Кнопка "да все привыкли" для принятия куки
    private final By acceptCookie = By.id("rcc-confirm-button");
    // Блок "Вопросы о важном"
    private final By questionBlock = By.className("Home_FourPart__1uthg");

    // Делаем конструктор для инициализации драйвера и ожидания
    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Методы взаимодействия с элементами главной страницы сайта

    // 1. Открываем главную страницу сайта
    public void openMainPage() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    // 2. Дожидаемся загрузки изображения элемента
    public void waitMainPageHeader() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPageHeader)).isDisplayed();
    }

    // 3. Находим на странице блок "Вопросы о важном"
    public void findQuestionBlock() {
        WebElement element = driver.findElement(questionBlock);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // 4. Нажимаем на кнопку "да все привыкли" для принятия куки
    public void clickAcceptCookie() {
        driver.findElement(acceptCookie).click();
    }

    // 5. Проверяем текст ответа на вопрос в блоке "Вопросы о важном"
    public boolean checkAnswerText(int questionNumber, String expectedAnswer) {

        // Находим вопрос по номеру
        By questionButtonLocator = By.xpath(String.format(".//div[@id='accordion__heading-%s']", questionNumber));
        WebElement questionElement = driver.findElement(questionButtonLocator);

        // Ждем пока стрелочка вопроса для раскрытия ответа станет кликабельной и нажимаем на нее
        wait.until(ExpectedConditions.elementToBeClickable(questionElement)).click();
        // Получаем ответ на вопрос
        By answerTextLocator = By.xpath(String.format(".//div[@id='accordion__panel-%s']", questionNumber));
        WebElement answerElement = driver.findElement(answerTextLocator);
        String actualAnswer = wait.until(ExpectedConditions.visibilityOf(answerElement)).getText();

        // Проверяем, чтобы полученный ответ на вопрос соответствовал ожидаемому
        return actualAnswer.equals(expectedAnswer);
    }

    // 6. Нажимаем на кнопку "Заказать" (вверху страницы)
    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    // 7. Нажимаем на кнопку "Заказать" (внизу страницы)
    public void clickBottomOrderButton() {
        WebElement element = driver.findElement(bottomOrderButton);
        // Скроллим до кнопки "Заказать" внизу страницы
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        // Ожидаем пока кнопка станет кликабельной и нажимаем на нее
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}