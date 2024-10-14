import config.ConfigLoader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;
import pages.PersonalInfoPage;
import validator.UserProfileValidator;

public class AutotestOtusTest {
    private static final Logger logger = LogManager.getLogger(AutotestOtusTest.class);
    private WebDriver driver;
    private ConfigLoader configLoader;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        logger.info("Браузер запустился в режиме полного экрана");

        driver = new ChromeDriver(options);
        configLoader = new ConfigLoader();
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Закрытие браузера");
    }

    @Test
    public void testUserProfileAndValidation() {
        // 1. Открыть сайт по URL из ConfigLoader
        driver.get(configLoader.getUrl());
        logger.info("Переходим на сайт");

        // 2. Логинимся на сайте
        LoginPage loginPage = new LoginPage(configLoader, driver);
        loginPage.login();
        logger.info("Логинимся на сайте");

        // 3. Войти в личный кабинет и заполнить данные
        PersonalInfoPage personalInfoPage = new PersonalInfoPage(driver);
        personalInfoPage.navigateToPersonalProfile(); // Переход в профиль
        personalInfoPage.fillPersonalData(configLoader); // Заполнение данных
        logger.info("Данные успешно заполнены");

        // Закрываем текущий браузер
        teardown();

        // Открываем новый браузер для проверки
        setup();
        // Заново настраиваем браузер и конфигурацию
        driver.get(configLoader.getUrl());
        logger.info("Переходим на сайт для проверки");

        // Логинимся на сайте для проверки
        LoginPage loginPageForValidation = new LoginPage(configLoader, driver);
        loginPageForValidation.login();
        logger.info("Логинимся на сайте для проверки");

        // Проверка личной информации
        UserProfileValidator userProfileValidator = new UserProfileValidator(driver);
        userProfileValidator.validatePersonalData(configLoader);
        logger.info("Проверка заполнения личной информации ");
    }
}