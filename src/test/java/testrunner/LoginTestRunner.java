package testrunner;

import config.Setup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.LoginPage;

public class LoginTestRunner extends Setup {
    @Test(priority = 3,description = "Verify successful login with valid admin credentials")
    public void doLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("admin","admin123");
        WebElement imgProfile = driver.findElement(By.className("oxd-userdropdown-img"));
        Assert.assertTrue(imgProfile.isDisplayed());
    }
    @Test(priority = 2,description = "Verify unsuccessful login with invalid admin credentials")
    public void doLoginWithInvalidCred() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("wrong","wrong");
        String alertActual= driver.findElements(By.className("oxd-text")).get(1).getText();
        String alertExpected="Invalid credentials";
        SoftAssert softAssert=new SoftAssert(); //soft assertion
        softAssert.assertTrue(alertActual.contains(alertExpected));
        softAssert.assertAll();
    }

    @Test(priority = 1,description = "Verify unsuccessful login with empty admin credentials")
    public void doLoginWithEmptyCred() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("","");
        String alertActual= driver.findElements(By.className("oxd-text")).get(3).getText();
        String alertExpected="Required";
        SoftAssert softAssert=new SoftAssert(); //soft assertion
        softAssert.assertTrue(alertActual.contains(alertExpected));
        softAssert.assertAll();
    }
}
