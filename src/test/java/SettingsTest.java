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

public class SettingsTest {
    Utils utils;
    WebDriverWait wait;
    HashMap<String, Boolean> map;

    private void changeSetting(WebDriver driver){
        wait = new WebDriverWait(driver, 10);
        utils.prepare(driver);
        utils.doLogin(driver, utils.getLogin(), utils.getPassword());
        if (driver instanceof FirefoxDriver) {
            driver.findElement(By.xpath("/html/body/div/div[3]/div/a[4]")).click(); // нажатие на кнопку настройки
            driver.findElement(By.xpath("/html/body/div/div[5]/form/table/tbody/tr[11]/td/input")).click(); // нажатие на чекбокс Отображать фоновый рисунок
            driver.findElement(By.xpath("/html/body/div/div[5]/form/table/tbody/tr[18]/td/input")).click(); // нажатие на кнопку сохранить
        } else {
            driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/a[4]")).click(); // нажатие на кнопку настройки
            driver.findElement(By.xpath("/html/body/div[1]/div[4]/form/table/tbody/tr[11]/td/input")).click(); // нажатие на чекбокс Отображать фоновый рисунок
            driver.findElement(By.xpath("/html/body/div[1]/div[4]/form/table/tbody/tr[18]/td/input")).click(); // нажатие на кнопку сохранить
        }
        var bg = driver.findElement(By.xpath("/html/body"));
        wait.until(ExpectedConditions.attributeToBe(bg,"style", "background-image: none;"));

        driver.quit();
    }

    private void returnSetting(WebDriver driver){
        wait = new WebDriverWait(driver, 10);
        utils.prepare(driver);
        utils.doLogin(driver, utils.getLogin(), utils.getPassword());
        if (driver instanceof FirefoxDriver) {
            driver.findElement(By.xpath("/html/body/div/div[3]/div/a[4]")).click(); // нажатие на кнопку настройки
            driver.findElement(By.xpath("/html/body/div/div[5]/form/table/tbody/tr[11]/td/input")).click(); // нажатие на чекбокс Отображать фоновый рисунок
            driver.findElement(By.xpath("/html/body/div/div[5]/form/table/tbody/tr[18]/td/input")).click(); // нажатие на кнопку сохранить
        } else {
            driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/a[4]")).click(); // нажатие на кнопку настройки
            driver.findElement(By.xpath("/html/body/div[1]/div[4]/form/table/tbody/tr[11]/td/input")).click(); // нажатие на чекбокс Отображать фоновый рисунок
            driver.findElement(By.xpath("/html/body/div[1]/div[4]/form/table/tbody/tr[18]/td/input")).click(); // нажатие на кнопку сохранить
        }
        var bg = driver.findElement(By.xpath("/html/body"));
        wait.until(ExpectedConditions.attributeToBe(bg,"style", "background-image: url(\"/gif/bg.gif\");"));

        driver.quit();
    }

    @Before
    public void setUp() {
        utils = new Utils();
        map = utils.selectBrowser();
    }

    @Test
    public void settingsTest() {
        if (map.get("chrome") != false) {
            changeSetting(new ChromeDriver());
            returnSetting(new ChromeDriver());
        }
        if (map.get("firefox") != false) {
            changeSetting(new FirefoxDriver());
            returnSetting(new FirefoxDriver());
        }
    }
}