package page;

import config.UsersModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.Utils;

import java.io.IOException;
import java.util.List;

public class PIMPage {
    @FindBy(className = "oxd-main-menu-item")
    List<WebElement> menuItems;
    @FindBy(className = "oxd-button")
    List<WebElement> button;
    @FindBy(className = "oxd-input")
    List<WebElement> inputField;

    @FindBy(tagName = "input")
    List<WebElement> employeeName;
    @FindBy(css = "[role=\"listbox\"]")
    WebElement selectRole;
    @FindBy(className = "oxd-switch-input")
    WebElement toggleButton;



    public PIMPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void registerUser(String firstName, String lastName, String username, String password) throws IOException, ParseException {
        menuItems.get(1).click();
        button.get(2).click();
        toggleButton.click();
        inputField.get(1).sendKeys(firstName);
        inputField.get(3).sendKeys(lastName);
        inputField.get(5).sendKeys(username);
        inputField.get(6).sendKeys(password);
        inputField.get(7).sendKeys(password);
        button.get(1).click();
    }

    public void searchUserByEmployeeId(String empId) {
        menuItems.get(1).click();
        inputField.get(1).sendKeys(empId);
        button.get(1).click();
    }

    public void searchUserByEmployeeName(String empName) throws InterruptedException {
        menuItems.get(0).click();
        employeeName.get(2).sendKeys(empName);
        Thread.sleep(3000);
        selectRole.click();
        button.get(1).click();

    }
}
