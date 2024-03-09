package testrunner;

import com.github.javafaker.Faker;
import config.Setup;
import config.UsersModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import page.LoginPage;
import page.PIMPage;
import utils.Utils;

import java.io.IOException;
import java.util.List;


public class PIMTestRunner extends Setup {
    @BeforeTest
    public void doLogin(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("admin","admin123");
    }
    @Test(priority = 1)
    public void employeeRegistration() throws InterruptedException, IOException, ParseException {
        PIMPage pimPage = new PIMPage(driver);
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String username = faker.name().username();
        String password = Utils.generateRandomPassword();

        pimPage.registerUser(firstName,lastName,username,password);
        Utils.doScroll(driver,0,500);
        Thread.sleep(7000);


        List<WebElement> id = driver.findElements(By.className("oxd-input"));
        String empID = id.get(4).getAttribute("value");


        UsersModel usersModel = new UsersModel();
        usersModel.setFirstName(firstName);
        usersModel.setLastName(lastName);
        usersModel.setEmpID(empID);
        usersModel.setUsername(username);
        usersModel.setPassword(password);
        Utils.saveUsers(usersModel);
    }

    @Test(priority = 2)
    public void searchUser() throws IOException, ParseException, InterruptedException {
        PIMPage pimPage = new PIMPage(driver);
        JSONObject jsonObject = Utils.getUser();
        String empID = (String) jsonObject.get("empID");
        pimPage.searchUserByEmployeeId(empID);
        Utils.doScroll(driver, 0, 500);

        String empFirstName = (String) jsonObject.get("firstName");
        String empLastName = (String) jsonObject.get("lastName");
        String empName = empFirstName + " " + empLastName;
        System.out.println(empName);
        pimPage.searchUserByEmployeeName(empName);

    }
}
