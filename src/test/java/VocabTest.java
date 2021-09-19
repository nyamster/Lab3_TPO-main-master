import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.HashMap;

import static org.junit.Assert.*;

public class VocabTest {
    Utils utils;
    WebDriverWait wait;
    HashMap<String, Boolean> map;

    private void checkVocab(WebDriver driver){
        wait = new WebDriverWait(driver, 10);
        utils.prepare(driver);
        if (driver instanceof FirefoxDriver) {
            driver.findElement(By.xpath("/html/body/div/div[5]/table/tbody/tr[3]/td[1]/a[1]")).click(); // нажатие на англо русский словарь
            driver.findElement(By.xpath("/html/body/div/div[5]/table[2]/tbody/tr[105]/td[1]/a")).click(); // нажатие на раздел Бразилия
            driver.findElement(By.xpath("/html/body/div/div[5]/table[2]/tbody/tr[16]/td[2]/a")).click(); // нажатие на слово Бразилия
            wait.until(ExpectedConditions.textToBe(By.xpath("/html/body/div/div[5]/table[1]/tbody/tr[3]/td[2]/a[1]"), "remuneration"));
        } else {
            driver.findElement(By.xpath("/html/body/div/div[5]/table/tbody/tr[3]/td[1]/a[1]")).click(); // нажатие на англо русский словарь
            driver.findElement(By.xpath("/html/body/div[1]/div[4]/table[2]/tbody/tr[105]/td[1]/a")).click(); // нажатие на раздел Bibliography
            driver.findElement(By.xpath("/html/body/div/div[4]/table[2]/tbody/tr[16]/td[2]/a")).click(); // нажатие на слово оклад
            wait.until(ExpectedConditions.textToBe(By.xpath("/html/body/div/div[4]/table[1]/tbody/tr[3]/td[2]/a[1]"), "remuneration"));
        }
        driver.quit();
    }

    @Before
    public void setUp() {
        utils = new Utils();
        map = utils.selectBrowser();
    }

    @Test
    public void commentTest() {
        if (map.get("chrome") != false) {
            checkVocab(new ChromeDriver());
        }
        if (map.get("firefox") != false) {
            checkVocab(new FirefoxDriver());
        }
    }
}