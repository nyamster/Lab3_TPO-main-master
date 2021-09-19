import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SearchTest {
    Utils utils;
    WebDriverWait wait;
    HashMap<String, Boolean> map;

    private final String searchResult = "";


    private void doNormalSearch(WebDriver driver){
        wait =  new WebDriverWait(driver, 10);
        utils.prepare(driver);

        WebElement selectElem1 = driver.findElement(By.xpath("/html/body/div/div[5]/form/select[1]")); // выбираем русский язык из выпадающего списка
        Select select1 = new Select(selectElem1);
        select1.selectByVisibleText("Russian");

        WebElement selectElem2 = driver.findElement(By.xpath("/html/body/div/div[5]/form/select[2]")); // выбираем испанский язык из выпадающего списка
        Select select2 = new Select(selectElem2);
        select2.selectByVisibleText("Spanish");

        utils.doSearch(driver, utils.getDefaultQuery());

        WebElement result;
        if (driver instanceof FirefoxDriver) {
            result = driver.findElement(By.xpath("/html/body/div[1]/div[5]/table[1]/tbody/tr[7]/td[2]/a"));
        } else {
            result = driver.findElement(By.xpath("/html/body/div[1]/div[4]/table[1]/tbody/tr[7]/td[2]/a"));
        }
        assertEquals("memoria", result.getText());
        driver.quit();
    }

    private void doNoResultSearch(WebDriver driver) {
        wait =  new WebDriverWait(driver, 10);
        utils.prepare(driver);
        utils.doSearch(driver, "zxczxczxc");
        WebElement result;
        if (driver instanceof FirefoxDriver) {
            result = driver.findElement(By.xpath("/html/body/div[1]/div[5]/div[1]/div[1]/a[1]"));
        } else {
            result = driver.findElement(By.xpath("/html/body/div[1]/div[5]/div[1]/a[1]"));
        }
        assertEquals("Add", result.getText());
        driver.quit();
    }

    @Before
    public void setUp() {
        utils = new Utils();
        map = utils.selectBrowser();
    }


    @Test
    public void normalSearchTest() {
        if (map.get("chrome") != false) {
            doNormalSearch(new ChromeDriver());
        }
        if (map.get("firefox") != false) {
            doNormalSearch(new FirefoxDriver());
        }
    }

    @Test
    public void noResultsTest() {
        if (map.get("chrome") != false) {
            doNoResultSearch(new ChromeDriver());
        }
        if (map.get("firefox") != false) {
            doNoResultSearch(new FirefoxDriver());
        }
    }
}