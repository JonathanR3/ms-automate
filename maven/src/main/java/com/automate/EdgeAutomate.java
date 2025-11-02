package com.automate;

import com.automate.cases.DailyActivities;
import com.automate.cases.DesktopSearches;
import com.automate.cases.MoreActivities;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;

public class EdgeAutomate {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Actions actions;
    public static String homeTab;

    public static final String EDGE_USER_DATA_DIR = ""; // Include user data dir
    public static final String EDGE_PROFILE_DIR = "Default";


    public static WebElement waitForCompletion(String element) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
    }

    /**
     * Browser Configs
     */
    public static void initialize() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/141.0.0.0 Safari/537.36 Edg/142.0.3595.53");
        options.addArguments("user-data-dir=" + EDGE_USER_DATA_DIR);
        options.addArguments("profile-directory=" + EDGE_PROFILE_DIR);

        driver = new EdgeDriver(options);
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driver.get("https://rewards.bing.com/");
    }

    public static void main(String[] args) {
        initialize();
        homeTab = driver.getWindowHandle();
        try {
            DailyActivities.runner();
            MoreActivities.runner();
            DesktopSearches search = new DesktopSearches();
            search.runner();
        }
        catch (Exception e) {
            System.out.printf("Failed to proceed: %s", e);
        }
        driver.quit();
    }
}
