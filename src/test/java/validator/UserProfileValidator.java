package validator;

import config.ConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.PersonalInfoPage;

import java.util.List;

public class UserProfileValidator {
    private static final Logger logger = LogManager.getLogger(UserProfileValidator.class);
    private final WebDriver driver;
    private final PersonalInfoPage personalInfoPage;

    public UserProfileValidator(WebDriver driver) {
        this.driver = driver;
        this.personalInfoPage = new PersonalInfoPage(driver);
    }

    public void validatePersonalData(ConfigLoader configLoader) {
        try {
            personalInfoPage.navigateToPersonalProfile(); // Переход к профилю
            List<WebElement> inputFields = driver.findElements(By.cssSelector(".input"));
            validateFields(inputFields, configLoader);
            validateCheckbox();
        } catch (Exception e) {
            logger.error("Ошибка проверки данных: {}", e.getMessage());
            throw new RuntimeException("Ошибка проверки данных", e);
        }
    }

    private void validateFields(List<WebElement> inputFields, ConfigLoader configLoader) {
        for (int i = 0; i < Math.min(inputFields.size(), 15); i++) {
            WebElement inputField = inputFields.get(i);
            String expectedValue = getExpectedValue(i, configLoader);
            checkField(inputField, expectedValue, i);
        }
    }

    private String getExpectedValue(int index, ConfigLoader configLoader) {
        switch (index) {
            case 0:
                return configLoader.getFirstName(); // Имя
            case 1:
                return configLoader.getFirstNameEng(); // Имя (латиницей)
            case 2:
                return configLoader.getLastName(); // Фамилия
            case 3:
                return configLoader.getLastNameEng(); // Фамилия (латиницей)
            case 4:
                return configLoader.getNameInBlog(); // Имя в блоге
            case 5:
                return configLoader.getDateOfBirth(); // Дата рождения
            case 6:
                return "Россия"; // Ожидаемое значение для страны
            case 7:
                return "Ростов-на-Дону"; // Ожидаемое значение для города
            case 8:
                return "Начальный уровень (Beginner)"; // Ожидаемое значение для уровня английского
            case 9:
                return configLoader.getContactEmail(); // Эл. почта
            case 10:
                return configLoader.getContactPhone(); // Номер телефона
            case 11:
                return "Тelegram"; // Ожидаемое значение для предпочтительного способа связи
            case 12:
                return configLoader.getContactTg(); // Контакт в Telegram
            case 13:
                return configLoader.getCompany(); // Компания
            case 14:
                return configLoader.getPost(); // Должность
            default:
                return "";
        }
    }

    private void checkField(WebElement inputField, String expectedValue, int fieldIndex) {
        String actualValue;

        // Проверяем, является ли элемент текстовым полем или выпадающим списком
        if (inputField.getTagName().equals("input") || inputField.getTagName().equals("textarea")) {
            // Для текстового поля
            actualValue = inputField.getAttribute("value").trim();
        } else if (inputField.getTagName().equals("select")) {
            // Для стандартного выпадающего списка
            actualValue = inputField.findElement(By.cssSelector("option:checked")).getText().trim();
        } else if (inputField.getAttribute("class").contains("js-custom-select-option") ||
                inputField.getTagName().equals("button")) {
            // Для кастомного или обычного выпадающего списка
            actualValue = inputField.getAttribute("title") != null ?
                    inputField.getAttribute("title").trim() :
                    inputField.getText().trim();
        } else {
            // Если элемент другого типа
            actualValue = inputField.getText().trim(); // Используем текст, если это не текстовое поле / выпадающий список
        }

        logger.info("Проверка поля на индексе {}: фактическое значение '{}'", fieldIndex, actualValue);

        // Сравнение фактического значения с ожидаемым
        if (!actualValue.equals(expectedValue)) {
            logger.error("Ошибка проверки на индексе {}: ожидаемое '{}', фактическое '{}'", fieldIndex, expectedValue, actualValue);
            throw new AssertionError("Значение поля не совпадает");
        } else {
            logger.info("Поле на индексе {} успешно проверено: {}", fieldIndex, actualValue);
        }
    }

    private void validateCheckbox() {
        WebElement relocationCheckbox = driver.findElement(By.cssSelector("[type='radio'][name='ready_to_relocate'][value='False']"));
        String status = relocationCheckbox.isSelected() ? "отмечен" : "не отмечен";
        logger.info("Чекбокс 'Готовность к переезду - нет' {}", status);
    }
}