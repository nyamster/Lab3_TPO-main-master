import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;

public class ChangeInfoTest {
    Utils utils;
    WebDriverWait wait;
    HashMap<String, Boolean> map;

    private void changeInfo(WebDriver driver, String info) {
        wait = new WebDriverWait(driver, 20);
        utils.prepare(driver);
        utils.doLogin(driver, utils.getLogin(), utils.getPassword()); // залогиниваемся на сайт
        if (driver instanceof FirefoxDriver) {
            driver.findElement(By.xpath("/html/body/div/div[3]/div/a[1]")).click(); // заходим в личный кабинет
            driver.findElement(By.xpath("/html/body/div/div[5]/div[2]/a")).click(); // нажимаем на изменение описания

            WebElement input = driver.findElement(By.xpath("/html/body/div/div[5]/form/table/tbody/tr[1]/td[2]/textarea")); // вводим информацию
            input.sendKeys(info);

            WebElement inputPass = driver.findElement(By.xpath("/html/body/div/div[5]/form/table/tbody/tr[2]/td[2]/input")); // вводим пароль
            inputPass.sendKeys(utils.getPassword());

            driver.findElement(By.xpath("/html/body/div/div[5]/form/table/tbody/tr[4]/td[2]/input")).click(); // нажатие на кнопку сохранить
            wait.until(ExpectedConditions.textToBe(By.xpath("/html/body/div/div[5]/div[3]"), info));
        } else {
            driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/a[1]")).click();
            driver.findElement(By.xpath("/html/body/div/div[4]/div[2]/a")).click();

            WebElement input = driver.findElement(By.xpath("/html/body/div/div[4]/form/table/tbody/tr[1]/td[2]/textarea"));
            input.sendKeys(info);

            WebElement inputPass = driver.findElement(By.xpath("/html/body/div/div[4]/form/table/tbody/tr[2]/td[2]/input"));
            inputPass.sendKeys(utils.getPassword());

            driver.findElement(By.xpath("/html/body/div/div[4]/form/table/tbody/tr[4]/td[2]/input")).click();
            wait.until(ExpectedConditions.textToBe(By.xpath("/html/body/div/div[4]/div[3]"), info));
        }
        driver.quit();
    }

    private void clearInfo(WebDriver driver) {
        wait = new WebDriverWait(driver, 20);
        utils.prepare(driver);
        utils.doLogin(driver, utils.getLogin(), utils.getPassword()); // вход на сайт
        if (driver instanceof FirefoxDriver) {
            driver.findElement(By.xpath("/html/body/div/div[3]/div/a[1]")).click(); // вход в личный кабинет
            driver.findElement(By.xpath("/html/body/div/div[5]/div[3]/a")).click(); // нажиматие на изменение описания

            WebElement input = driver.findElement(By.xpath("/html/body/div/div[5]/form/table/tbody/tr[1]/td[2]/textarea")); // очистка поля информации
            utils.clearField(driver, input);

            WebElement inputPass = driver.findElement(By.xpath("/html/body/div/div[5]/form/table/tbody/tr[2]/td[2]/input")); // ввод пароля
            inputPass.sendKeys(utils.getPassword());

            driver.findElement(By.xpath("/html/body/div/div[5]/form/table/tbody/tr[4]/td[2]/input")).click();
            wait.until(ExpectedConditions.textToBe(By.xpath("/html/body/div/div[5]/div[1]/font"), "Запись сохранена")); // нажатие на кнопку сохранить
        } else {
            driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/a[1]")).click();
            driver.findElement(By.xpath("/html/body/div/div[4]/div[3]/a")).click();

            WebElement input = driver.findElement(By.xpath("/html/body/div/div[4]/form/table/tbody/tr[1]/td[2]/textarea"));
            utils.clearField(driver, input);

            WebElement inputPass = driver.findElement(By.xpath("/html/body/div/div[4]/form/table/tbody/tr[2]/td[2]/input"));
            inputPass.sendKeys(utils.getPassword());

            driver.findElement(By.xpath("/html/body/div/div[4]/form/table/tbody/tr[4]/td[2]/input")).click();
            wait.until(ExpectedConditions.textToBe(By.xpath("/html/body/div/div[4]/div[1]/font"), "Запись сохранена"));
        }
        driver.quit();
    }

    @Before
    public void setUp() {
        utils = new Utils();
        map = utils.selectBrowser();
    }

    @Test
    public void changeInfoTest() {
        if (map.get("chrome") != false) {
            changeInfo(new ChromeDriver(), "Hello");
            clearInfo(new ChromeDriver());
        }
        if (map.get("firefox") != false) {
            changeInfo(new FirefoxDriver(), "Hello");
            clearInfo(new FirefoxDriver());
        }
    }
}