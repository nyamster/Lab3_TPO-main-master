import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;

import static org.junit.Assert.*;

public class LoginTest {
    Utils utils;
    WebDriverWait wait;
    HashMap<String, Boolean> map;

    private final String accountNameF = "//*[@id=\"start\"]/div[3]/div/a[1]";
    private final String accountNameC = "/html/body/div[1]/div[2]/div/a[1]";
    private final String logoutBtnF = "//*[@id=\"start\"]/div[3]/div/a[2]";
    private final String logoutBtnC = "/html/body/div/div[2]/div/a[2]";

    private void doCorrectLogin(WebDriver driver){
        wait = new WebDriverWait(driver, 10);
        utils.prepare(driver);
        utils.doLogin(driver, utils.getLogin(), utils.getPassword());
        if (driver instanceof FirefoxDriver) {
            wait.until(ExpectedConditions.textToBe(By.xpath(accountNameF), "mishka798")); // проверяем инициалы аккаунта в правом верхнем углу
            driver.findElement(By.xpath(accountNameF)).click(); // идем на страницу аккаунта
            WebElement exitBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(logoutBtnF))); // жмем кнопку выйти
            exitBtn.click();
        } else {
            wait.until(ExpectedConditions.textToBe(By.xpath(accountNameC), "mishka798")); // проверяем инициалы аккаунта в правом верхнем углу
            driver.findElement(By.xpath(accountNameC)).click(); // идем на страницу аккаунта
            WebElement exitBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(logoutBtnC))); // жмем кнопку выйти
            exitBtn.click();
        }
        driver.quit();
    }

    private void doIncorrectLogin(WebDriver driver){
        utils.prepare(driver);
        String window = utils.doLogin(driver, utils.getLogin(), "sfgjyfghjfcghcf");
        driver.switchTo().window(window);
        if (driver instanceof FirefoxDriver) {
            assertTrue(utils.isElementPresent(driver, By.xpath("/html/body/div/div[5]/table/tbody/tr[1]/td/strong/font")));
        } else {
            assertTrue(utils.isElementPresent(driver, By.xpath("/html/body/div/div[4]/table/tbody/tr[1]/td/strong/font")));
        }
        driver.quit();
    }

    @Before
    public void setUp() {
        utils = new Utils();
        map = utils.selectBrowser();
    }

    @Test
    public void successfulLogin() {
        if (map.get("chrome") != false) {
            doCorrectLogin(new ChromeDriver());
        }
        if (map.get("firefox") != false) {
            doCorrectLogin(new FirefoxDriver());
        }
    }

    @Test
    public void wrongPassword() {
        if (map.get("chrome") != false) {
            doIncorrectLogin(new ChromeDriver());
        }
        if (map.get("firefox") != false) {
            doIncorrectLogin(new FirefoxDriver());
        }
    }
}