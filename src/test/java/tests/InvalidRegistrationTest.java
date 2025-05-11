package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class InvalidRegistrationTest extends BaseTest
{
	@Test(groups = {"regression"})
public void InvalidInput() throws InterruptedException {
{
	System.out.println("Driver is: " + driver);
	LoginPage login = new LoginPage(driver);
	
	// Step 1: Enter registration number before clicking the button
    login.enterRegistrationNumber("LG");

    // Step 2: Click the “Value your car” button
    login.clickValueYourCar();
    Thread.sleep(5000);
    
    login.ConfirmMileageOfVehicle();
    Thread.sleep(5000);

}
}}
