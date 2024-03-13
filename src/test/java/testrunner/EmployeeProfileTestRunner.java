package testrunner;

import config.Setup;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.LoginPage;
import utils.Utils;

import java.io.IOException;
import java.time.Duration;
import java.util.List;


public class EmployeeProfileTestRunner extends Setup {
    @Test(priority = 3, description = "User can login successfully")
    public void empLoginWithValidCred() throws IOException, ParseException, InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        JSONObject jsonObject = Utils.getUser();
        String username = (String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");
        loginPage.doLogin(username, password);

        WebElement imgProfile = driver.findElement(By.className("oxd-userdropdown-img"));
        Assert.assertTrue(imgProfile.isDisplayed());
    }

    @Test(priority = 2, description = "Verify that the employee's full name is displayed beside the profile icon after logging in")
    public void assertProfPic() {
        WebElement fullName = driver.findElement(By.className("oxd-userdropdown-name"));
        Assert.assertTrue(fullName.isDisplayed());
    }

    @Test(priority = 3, description = "Login as Newly Created Employee with Invalid Credentials")
    public void empLoginWithInvalidCred() throws IOException, ParseException, InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        String username = "invalid";
        String password = "invalid";
        loginPage.doLogin(username, password);

        String alertActual = driver.findElements(By.className("oxd-text")).get(1).getText();
        String alertExpected = "Invalid credentials";
        SoftAssert softAssert = new SoftAssert(); //soft assertion
        softAssert.assertTrue(alertActual.contains(alertExpected));
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Verify unsuccessful login as a newly created employee with empty credentials")
    public void empLoginWithEmptyCred() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("", "");
        String alertActual = driver.findElements(By.className("oxd-text")).get(3).getText();
        String alertExpected = "Required";
        SoftAssert softAssert = new SoftAssert(); //soft assertion
        softAssert.assertTrue(alertActual.contains(alertExpected));
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Verify successful update of gender")
    public void updateGender() {
        WebElement myInfo = driver.findElements(By.className("oxd-main-menu-item-wrapper")).get(2);
        myInfo.click();
        List<WebElement> title = driver.findElements(By.className("orangehrm-main-title"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOf(title.get(0)));
        WebElement gender = driver.findElements(By.className("oxd-radio-input")).get(0);
        gender.click();
    }
    @Test(priority = 6, description = "Verify successful update of blood group")


    public void updateBloodGroup() {
        WebElement myInfo = driver.findElements(By.className("oxd-main-menu-item-wrapper")).get(2);
        myInfo.click();
        Utils.doScroll(driver, 0, 400);
        WebElement selectBloodIcon = driver.findElements(By.className("oxd-select-text-input")).get(2);
        selectBloodIcon.click();
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.DOWN).perform();
        actions.sendKeys(Keys.DOWN).perform();
        actions.sendKeys(Keys.DOWN).perform();
        actions.sendKeys(Keys.DOWN).perform();
        actions.sendKeys(Keys.DOWN).perform();
        actions.sendKeys(Keys.ENTER).perform();

        WebElement saveBtn = driver.findElements(By.className("oxd-button")).get(1);
        saveBtn.click();
    }


}

