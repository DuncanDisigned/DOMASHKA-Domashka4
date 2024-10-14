package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;


public class ConfigLoader {
    private static final Logger logger = LogManager.getLogger(ConfigLoader.class);

    private String url;
    private String username;
    private String password;
    private String firstName;
    private String firstNameEng;
    private String lastName;
    private String lastNameEng;
    private String nameInBlog;
    private String contactTg;
    private String contactEmail;
    private String contactPhone;
    private String communicationWithMe;
    private String company;
    private String post;
    private String dateOfBirth;

    public ConfigLoader() {
        loadProperties();
    }

    private void loadProperties() {
        // Используем InputStreamReader для загрузки с кодировкой UTF-8
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream("src/test/resources/config.properties"), StandardCharsets.UTF_8)) {
            Properties props = new Properties();
            props.load(reader);

            // Загружаем значения свойств
            url = props.getProperty("url");
            username = props.getProperty("username");
            password = props.getProperty("password");
            firstName = props.getProperty("firstName");
            firstNameEng = props.getProperty("firstNameEng");
            lastName = props.getProperty("lastName");
            lastNameEng = props.getProperty("lastNameEng");
            nameInBlog = props.getProperty("nameInBlog");
            contactTg = props.getProperty("contactTg");
            contactPhone = props.getProperty("contactPhone");
            contactEmail = props.getProperty("contactEmail");
            communicationWithMe = props.getProperty("communicationWithMe");
            company = props.getProperty("company");
            post = props.getProperty("post");
            dateOfBirth = props.getProperty("dateOfBirth");
        } catch (IOException e) {
            logger.error("Ошибка загрузки файла конфигурации: {}", e.getMessage());
            throw new RuntimeException("Ошибка загрузки файла конфигурации", e);
        }
    }

    // Методы для получения значений
    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFirstNameEng() {
        return firstNameEng;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLastNameEng() {
        return lastNameEng;
    }

    public String getNameInBlog() {
        return nameInBlog;
    }

    public String getContactTg() {
        return contactTg;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getCommunicationWithMe() {
        return communicationWithMe;
    }

    public String getCompany() {
        return company;
    }

    public String getPost() {
        return post;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
