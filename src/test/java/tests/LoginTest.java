package tests;

import base.BaseTest;
import pages.LoginPage;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

	@Test(groups = {"regression"})
	public void testEnterRegistrationAndClickValueYourCar() throws InterruptedException {
	    LoginPage loginPage = new LoginPage(driver);

	    // Step 1: Enter registration number before clicking the button
	    loginPage.enterRegistrationNumber("LG70AMV");

	    // Step 2: Click the “Value your car” button
	    loginPage.clickValueYourCar();
	    Thread.sleep(5000);
	    
	    loginPage.ConfirmMileageOfVehicle();
	    Thread.sleep(5000);

	}

    }
