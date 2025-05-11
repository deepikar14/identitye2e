package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    // Define locators as class variables
    By valueYourCarButton = By.xpath("//*[@id=\"main\"]/div[1]/div/div[1]/div/div/div/div/section/form/button/span[1]");
    By enterRegistrationNumber = By.xpath("//*[@id=\"vrm-input\"]");
    By ConfirmMileage = By.xpath("//*[@id=\"main\"]/div[1]/div/div[1]/div/div/div/div/section/form/button/span[1]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    

    // Enters the registration number into the input field
    public void enterRegistrationNumber(String regNumber) {
        driver.findElement(enterRegistrationNumber).sendKeys(regNumber); // Enter the reg number
    }
    
    
 // Clicks the "Value your car" button
    public void clickValueYourCar() {
        driver.findElement(valueYourCarButton).click();
    }
    
    //Click the "Confirm Mileage" button
    public void ConfirmMileageOfVehicle() {
    	driver.findElement(ConfirmMileage).click();
    }
}
