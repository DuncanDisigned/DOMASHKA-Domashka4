package interfaces;

import config.ConfigLoader;
import org.openqa.selenium.WebDriver;

public interface IPersonalInfoPage {
    void fillPersonalData(WebDriver driver, ConfigLoader configLoader);
}