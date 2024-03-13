package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage {

    @FindBy(name = "username")
    WebElement txtUsername;
    @FindBy(css = "[type=\"password\"]")
    WebElement txtPassword;

    @FindBy(css = "[type=\"submit\"]")
    WebElement loginBtn;

    @FindBy(className = "oxd-userdropdown-icon")
    WebElement loggedinUser;
    @FindBy(css = "[role =\"menuitem\"]")
    List<WebElement> logoutBtn;

    WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    public void doLogin(String username, String password){
        txtUsername.sendKeys(username);
        txtPassword.sendKeys(password);
        loginBtn.click();
    }

    public void doLogout(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loggedinUser.click();
        loginPage.logoutBtn.get(3).click();
    }
}
