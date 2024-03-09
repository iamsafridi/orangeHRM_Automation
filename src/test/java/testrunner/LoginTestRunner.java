package testrunner;

import config.Setup;
import org.testng.annotations.Test;
import page.LoginPage;

public class LoginTestRunner extends Setup {
    @Test
    public void doLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("admin","admin123");
    }
}
