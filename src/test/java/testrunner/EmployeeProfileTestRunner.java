package testrunner;

import config.Setup;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.LoginPage;
import utils.Utils;

import java.io.IOException;


public class EmployeeProfileTestRunner extends Setup {
    @Test
    public void doLogin() throws IOException, ParseException, InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        JSONObject jsonObject = Utils.getUser();
        String username = (String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");
        loginPage.doLogin(username, password);

        WebElement fullName =driver.findElement(By.className("oxd-userdropdown-name"));
        Assert.assertTrue(fullName.isDisplayed());

        WebElement myInfo = driver.findElements(By.className("oxd-main-menu-item-wrapper")).get(2);
        myInfo.click();
        WebElement gender = driver.findElements(By.className("oxd-radio-input")).get(0);
        gender.click();

        Utils.doScroll(driver,0,500);

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
