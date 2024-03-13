package testrunner;

import com.github.javafaker.Faker;
import config.Setup;
import config.UsersModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.LoginPage;
import page.PIMPage;
import utils.Utils;

import java.io.IOException;
import java.time.Duration;
import java.util.List;


public class PIMTestRunner extends Setup {
    @BeforeTest
    public void doLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("admin", "admin123");
    }

    @Test(priority = 1, description = "Verify successful creation of a new employee with valid details")
    public void employeeRegistration() throws InterruptedException, IOException, ParseException {
        PIMPage pimPage = new PIMPage(driver);
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String username = faker.name().username();
        String password = Utils.generateRandomPassword();

        pimPage.registerUser(firstName, lastName, username, password);
        Utils.doScroll(driver, 0, 500);
        Thread.sleep(7000);
//        List<WebElement> title = driver.findElements(By.className("orangehrm-main-title"));
//        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(40));
//        wait.until(ExpectedConditions.visibilityOf(title.get(0)));
        String message = driver.findElements(By.className("orangehrm-main-title")).get(0).getText();
        Assert.assertTrue(message.contains("Personal Details"));

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

    @Test(priority = 2, description = "Verify that the employee's created successfully")
    public void employeeCreatedSuccessfully() {
        String message = driver.findElements(By.className("orangehrm-main-title")).get(0).getText();
        Assert.assertTrue(message.contains("Personal Details"));
    }

    @Test(priority = 3, description = "Verify unsuccessful creation of a new employee with missing details")
    public void employeeRegistrationWithMissingDetails() throws InterruptedException, IOException, ParseException {
        PIMPage pimPage = new PIMPage(driver);
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = "";
        String username = faker.name().username();
        String password = Utils.generateRandomPassword();

        pimPage.registerUser(firstName, lastName, username, password);
        Utils.doScroll(driver, 0, 500);

        String alertActual = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div[2]/div[1]/div[1]/div/div/div[2]/div[3]/span")).getText();
        String alertExpected = "Required";

        SoftAssert softAssert = new SoftAssert(); //soft assertion
        softAssert.assertTrue(alertActual.contains(alertExpected));
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Verify unsuccessful creation of a new employee with a duplicate employee ID or username.")
    public void employeeRegistrationWithDuplicateUsername() throws InterruptedException, IOException, ParseException {
        PIMPage pimPage = new PIMPage(driver);
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String username = "huong.king";
        String password = Utils.generateRandomPassword();

        pimPage.registerUser(firstName, lastName, username, password);
        Utils.doScroll(driver, 0, 500);

        String alertActual = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div[2]/div[3]/div/div[1]/div/span")).getText();
        String alertExpected = "Username already exists";

        SoftAssert softAssert = new SoftAssert(); //soft assertion
        softAssert.assertTrue(alertActual.contains(alertExpected));
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Search Employee by Valid ID")
    public void searchUserWithValidEmpId() throws IOException, ParseException, InterruptedException {
        PIMPage pimPage = new PIMPage(driver);
        JSONObject jsonObject = Utils.getUser();
        String empID = (String) jsonObject.get("empID");
        pimPage.searchUserByEmployeeId(empID);
//        String alertActual = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[2]/div/span")).getText();
//        String alertExpected = "(1) Record Found";
//
//        SoftAssert softAssert = new SoftAssert(); //soft assertion
//        softAssert.assertTrue(alertActual.contains(alertExpected));
//        softAssert.assertAll();
    }

    @Test(priority = 6, description = "Search Employee by invalid ID")
    public void searchUserWithInvalidEmpId() throws IOException, ParseException, InterruptedException {
        PIMPage pimPage = new PIMPage(driver);
        String empID = "2342";
        pimPage.searchUserByEmployeeId(empID);
        String alertActual = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[2]/div/span")).getText();
        String alertExpected = "No Records Found";

        SoftAssert softAssert = new SoftAssert(); //soft assertion
        softAssert.assertTrue(alertActual.contains(alertExpected));
        softAssert.assertAll();
    }

    @Test(priority = 7, description = "Search Employee by valid Name")
    public void searchUserWithValidName() throws IOException, ParseException, InterruptedException {
        PIMPage pimPage = new PIMPage(driver);
        JSONObject jsonObject = Utils.getUser();
        String empName = (String) jsonObject.get("firstName");
        pimPage.searchUserByEmployeeName(empName);
        Thread.sleep(2000);
        String alertActual = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div/div[1]/div/span")).getText();
        String alertExpected = "(1) Record Found";

        SoftAssert softAssert = new SoftAssert(); //soft assertion
        softAssert.assertTrue(alertActual.contains(alertExpected));
        softAssert.assertAll();
    }

    @Test(priority = 8, description = "Search Employee by invalid name")
    public void searchUserWithInvalidName() throws IOException, ParseException, InterruptedException {
        PIMPage pimPage = new PIMPage(driver);
        String empName = "unknown";
        pimPage.searchUserByEmployeeName(empName);
        String alertActual = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/span")).getText();
        String alertExpected = "Invalid";

        SoftAssert softAssert = new SoftAssert(); //soft assertion
        softAssert.assertTrue(alertActual.contains(alertExpected));
        softAssert.assertAll();
    }

}
