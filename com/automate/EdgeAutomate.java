package com.automate;

import com.automate.cases.DesktopSearches;
import com.automate.cases.MoreActivities;
import com.automate.cases.DailyActivities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EdgeAutomate {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Actions actions;
    public static String homeTab;


    public static WebElement waitForCompletion(String element) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
    }

    /**
     * Browser Configs
     */
    public static void initialize() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--guest");
        driver = new EdgeDriver(options);
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    /**
     * Log into Edge
     */
    public static void login() {
        driver.get("https://rewards.bing.com/");
        try {
            WebElement loginBanner = waitForCompletion("//*[@id=\"usernameTitle\"]");

            // Log in if user is not logged in
            if (loginBanner.isDisplayed()) {
                WebElement username = waitForCompletion("//*[@id=\"i0116\"]");
                username.sendKeys("");

                WebElement enter = waitForCompletion("//*[@id=\"idSIButton9\"]");
                enter.click();

                WebElement password = waitForCompletion("//*[@id=\"i0118\"]");
                password.sendKeys("");

                WebElement signIn = waitForCompletion("//*[@id=\"idSIButton9\"]");
                signIn.click();

                // Fill stay signed in prompt
                if (!driver.findElements(By.xpath("//*[@id=\"kmsiTitle\"]")).isEmpty()) {
                    WebElement checkBox = driver.findElement(By.xpath("//*[@id=\"checkboxField\"]"));
                    checkBox.click();
                    WebElement yesSign = driver.findElement(By.xpath("//*[@id=\"acceptButton\"]"));
                    yesSign.click();
                }
            }
        }
        catch (Exception e) {
            System.out.printf("Failed to proceed: %s", e);
        }
    }

    public static void main(String[] args) {
        System.setProperty("webdriver.edge.driver", "com/automate/drivers/msedgedriver");
        initialize();
        login();
        homeTab = driver.getWindowHandle();
        try {
            DailyActivities.runner();
            MoreActivities.runner();
            DesktopSearches.runner();
        }
        catch (Exception e) {
            System.out.printf("Failed to proceed: %s", e);
        }
    }
}
