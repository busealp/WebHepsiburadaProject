package org.example;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BaseTest {

    public static WebDriver driver;
    String baseUrl = "https://www.hepsiburada.com/";

    @BeforeScenario
    public void beforeScenario(){

        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("test-type");
        chromeOptions.addArguments("disable-popup-blocking");
        chromeOptions.addArguments("ignore-certificate-errors");
        chromeOptions.addArguments("disable-translate");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.setExperimentalOption("w3c", false);
        chromeOptions.merge(capabilities);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().fullscreen();
        driver.get(baseUrl);
    }

    @AfterScenario
    public void afterScenario(){

        if(driver != null){
            //driver.quit();
        }
    }
}
