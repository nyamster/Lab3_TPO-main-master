
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Utils {
    private final String url = "https://www.multitran.com/";
    private final String login = "mishka798";
    private final String password = "mishka798";
    private final String defaultQuery = "Привет";

    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getDefaultQuery() {
        return defaultQuery;
    }

    public Utils() {
        System.setProperty("webdriver.gecko.driver","src\\main\\resources\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver","src\\main\\resources\\chromedriver.exe");
    }

    public void prepare(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(getUrl());
    }

    public boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String  doLogin(WebDriver driver, String login, String password) {
        String originalWindow = driver.getWindowHandle();

        driver.findElement(By.xpath("/html/body/div/div[3]/div/a[1]")).click();

        if(driver instanceof FirefoxDriver) {
            driver.findElement(By.xpath("/html/body/div/div[5]/form/table/tbody/tr[1]/td[2]/input")).sendKeys(login);
            driver.findElement(By.xpath("/html/body/div/div[5]/form/table/tbody/tr[2]/td[2]/input")).sendKeys(password);
            driver.findElement(By.xpath("/html/body/div/div[5]/form/table/tbody/tr[4]/td/input")).click();
        } else {
            driver.findElement(By.xpath("/html/body/div/div[4]/form/table/tbody/tr[1]/td[2]/input")).sendKeys(login);
            driver.findElement(By.xpath("/html/body/div/div[4]/form/table/tbody/tr[2]/td[2]/input")).sendKeys(password);
            driver.findElement(By.xpath("/html/body/div/div[4]/form/table/tbody/tr[4]/td/input")).click();
        }
        return  originalWindow;
    }

    public void doSearch(WebDriver driver, String query) {
        WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"s\"]"));
        searchBar.sendKeys(query);
        searchBar.sendKeys(Keys.ENTER);
    }

    public void clearField(WebDriver driver, WebElement textField) {
        textField.clear();
    }

    public HashMap<String, Boolean> selectBrowser(){
        HashMap<String, Boolean> driver_map = new HashMap<String, Boolean>();
        try {
            var driverType = Configuration.getProperty("driverType");
            if (driverType.equals("all")) {
                driver_map.put("chrome", true);
                driver_map.put("firefox", true);
            } else if (driverType.equals("firefox")) {
                driver_map.put("firefox", true);
            } else if (driverType.equals("chrome")) {
                driver_map.put("chrome", true);
            } else {
                throw new Exception("Wrong config param");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        finally {
            return driver_map;
        }
    }
}
