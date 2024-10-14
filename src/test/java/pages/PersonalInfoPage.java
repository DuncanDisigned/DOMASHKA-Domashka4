package pages;

import config.ConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PersonalInfoPage {
    private static final Logger logger = LogManager.getLogger(PersonalInfoPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;

    public PersonalInfoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToPersonalProfile() {
        // Переход в раздел 'О себе'
        WebElement elementWindow = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".sc-199a3eq-0.fJMWHf")));

        // Кликаем по элементу с помощью JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementWindow);

        // Ожидаем, что элемент 'О себе' станет видимым и кликаем по нему
        WebElement personalProfileLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='https://otus.ru/lk/biography/personal']")));

        // Кликаем по элементу с помощью JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", personalProfileLink);

        logger.info("Переход в раздел 'О себе'");
    }

    public void fillPersonalData(ConfigLoader configLoader) {
        //Перебераем селектор в цикле, чтобы не искать каждый селектор отдельно
        try {
            List<WebElement> inputFields = driver.findElements(By.cssSelector(".input"));
            int elementsInProcessing = Math.min(inputFields.size(), 15);

            for (int i = 0; i < elementsInProcessing; i++) {              //Цикл
                WebElement inputData = inputFields.get(i);
                String expectedValue;
                if (i < 6 || i > 11) {
                    inputData.clear();
                }
                switch (i) {
                    case 0:
                        expectedValue = configLoader.getFirstName(); // Имя
                        inputData.sendKeys(expectedValue);
                        logger.info("Поле 'Имя' заполнено значением: {}", expectedValue);
                        break;
                    case 1:
                        expectedValue = configLoader.getFirstNameEng(); // Имя (Eng)
                        inputData.sendKeys(expectedValue);
                        logger.info("Поле 'Имя (Eng)' заполнено значением: {}", expectedValue);
                        break;
                    case 2:
                        expectedValue = configLoader.getLastName(); // Фамилия
                        inputData.sendKeys(expectedValue);
                        logger.info("Поле 'Фамилия' заполнено значением: {}", expectedValue);
                        break;
                    case 3:
                        expectedValue = configLoader.getLastNameEng(); // Фамилия (Eng)
                        inputData.sendKeys(expectedValue);
                        logger.info("Поле 'Фамилия (Eng)' заполнено значением: {}", expectedValue);
                        break;
                    case 4:
                        expectedValue = configLoader.getNameInBlog(); // Имя в блоге
                        inputData.sendKeys(expectedValue);
                        logger.info("Поле 'Имя в блоге' заполнено значением: {}", expectedValue);
                        break;
                    case 5:
                        expectedValue = configLoader.getDateOfBirth(); // Дата рождения
                        inputData.sendKeys(expectedValue);
                        logger.info("Поле 'Дата рождения' заполнено значением: {}", expectedValue);
                        break;
                    case 6:
                        expectedValue = "Россия"; // Страна
                        inputData.click(); // Открытие выпадающего списка
                        WebElement countryOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Россия']")));
                        countryOption.click();
                        logger.info("Поле 'Страна' заполнено значением: {}", expectedValue);
                        Thread.sleep(3000);
                        break;
                    case 7:
                        expectedValue = "Ростов-на-Дону"; // Город
                        inputData.click(); // Открытие выпадающего списка
                        WebElement cityOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Ростов-на-Дону']")));
                        cityOption.click();
                        logger.info("Поле 'Город' заполнено значением: {}", expectedValue);
                        break;
                    case 8:
                        expectedValue = "Начальный уровень (Beginner)"; // Уровень
                        inputData.click(); // Открытие выпадающего списка
                        WebElement englishLevelOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Начальный уровень (Beginner)']")));
                        englishLevelOption.click();
                        logger.info("Поле 'Уровень' заполнено значением: {}", expectedValue);
                        break;
                    case 9:
                        // Пропускаем поле "Эл. почта"
                        logger.info("Поле 'Эл. почта' пропущено.");
                        break;
                    case 10:
                        // Пропускаем поле "Номер телефона"
                        logger.info("Поле 'Номер телефона' пропущено.");
                        break;
                    case 11:
                        expectedValue = "Тelegram"; // Способ связи
                        inputData.click(); // Открытие выпадающего списка
                        Thread.sleep(1000);
                        WebElement communicationMethodOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-value='telegram']")));
                        communicationMethodOption.click();
                        logger.info("Поле 'Способ связи' заполнено значением: {}", expectedValue);
                        break;
                    case 12:
                        expectedValue = configLoader.getContactTg(); // Контакт в Telegram
                        inputData.sendKeys(expectedValue);
                        logger.info("Поле 'Контакт в Telegram' заполнено значением: {}", expectedValue);
                        break;
                    case 13:
                        expectedValue = configLoader.getCompany(); // Компания
                        inputData.sendKeys(expectedValue);
                        logger.info("Поле 'Компания' заполнено значением: {}", expectedValue);
                        break;
                    case 14:
                        expectedValue = configLoader.getPost(); // Должность
                        inputData.sendKeys(expectedValue);
                        logger.info("Поле 'Должность' заполнено значением: {}", expectedValue);
                        break;
                }
            }
            WebElement saveButton = driver.findElement(By.cssSelector("[title='Сохранить и продолжить']"));
            saveButton.click();
            logger.info("Данные успешно сохранены.");

        } catch (Exception e) {
            logger.error("Ошибка заполнения или сохранения личных данных: {}", e.getMessage());
            throw new RuntimeException("Ошибка заполнения или сохранения личных данных", e);
        }
    }

}
