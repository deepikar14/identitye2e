package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    By valueYourCarButton = By.xpath("//*[@id=\"main\"]/div[1]/div/div[1]/div/div/div/div/section/form/button/span[1]");
    By enterRegistrationNumber = By.id("vrm-input");
    By makeModelHeader = By.cssSelector("#main > div.Hero__homepageHeroWrapper-tQXt > div > div.Hero__homepageContentWrapper-fG3K > div > div > div > div > div.HeroVehicle__component-Av9f > h1");
    By yearValue = By.xpath("//*[@id=\"main\"]/div[1]/div/div[1]/div/div/div/div/div[1]/ul/li[1]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Enter the registration number
    public void enterRegistrationNumber(String regNumber) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(enterRegistrationNumber)).sendKeys(regNumber);
    }

    // Click the "Value your car" button
    public void clickValueYourCar() {
        wait.until(ExpectedConditions.elementToBeClickable(valueYourCarButton)).click();
    }

    //Extract Make and Model
    public String getMakeModel() {
        try {
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(makeModelHeader));
            return heading.getText(); 
        } catch (Exception e) {
            System.out.println("Failed to get make/model: " + e.getMessage());
            return "Make/Model not found";
        }
    }

    //Extract Year
    public String getYear() {
        try {
            WebElement yearElement = wait.until(ExpectedConditions.visibilityOfElementLocated(yearValue));
            return yearElement.getText(); 
        } catch (Exception e) {
            System.out.println("Failed to get year: " + e.getMessage());
            return "Year not found";
        }
    }
}
