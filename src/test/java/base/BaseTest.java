package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.out.println(">>> [DEBUG] Inside setUp()");
        WebDriverManager.chromedriver().setup();  // Automatically downloads the correct version
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://motorway.com");
        System.out.println(">>> [DEBUG] Driver initialized: " + driver);
    }

    @AfterMethod
    public void tearDown() {
        System.out.println(">>> [DEBUG] Inside tearDown()");
        if (driver != null) {
            driver.quit();
            System.out.println(">>> [DEBUG] Driver quit successfully.");
        } else {
            System.out.println(">>> [DEBUG] Driver was null during tearDown.");
        }
    }
}
