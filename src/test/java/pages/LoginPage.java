package pages;

import config.ConfigLoader;
import interfaces.ILoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage implements ILoginPage {
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    private final ConfigLoader configLoader;
    private final WebDriver driver;

    public LoginPage(ConfigLoader configLoader, WebDriver driver) {
        this.configLoader = configLoader;
        this.driver = driver; // Инициализация driver
    }

    public void login() {
        logger.info("Начало процесса входа");
        String username = configLoader.getUsername();
        String password = configLoader.getPassword();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            logger.info("Нажимаем кнопку 'Войти'");
            WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".sc-mrx253-0.enxKCy.sc-945rct-0.iOoJwQ")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableElement);

            logger.info("Вводим логин! ");
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sc-rq8xzv-4[name='email']")));
            emailField.click();
            emailField.clear();
            emailField.sendKeys(username);

            logger.info("Вводим пароль! ");
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sc-rq8xzv-4[type='password']")));
            passwordField.click();
            passwordField.clear();
            passwordField.sendKeys(password);

            logger.info("Нажимаем кнопку 'Отправить'");
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".sc-9a4spb-0.eQlGvH.sc-11ptd2v-2-Component.cElCrZ"))).click();

            logger.info("Вход выполнен успешно");
        } catch (NoSuchElementException e) {
            logger.error("Ошибка входа: Элемент не найден - {}", e.getMessage());
            throw new RuntimeException("Ошибка входа: Элемент не найден", e);
        } catch (Exception e) {
            logger.error("Ошибка входа: {}", e.getMessage());
            throw new RuntimeException("Ошибка входа", e);
        }
    }

    @Override
    public void login(WebDriver driver) {

    }
}