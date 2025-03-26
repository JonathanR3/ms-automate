package com.automate.cases;

import com.automate.EdgeAutomate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Set;

public class MoreActivities {

    public static WebDriver driver = EdgeAutomate.driver;

    public static void search(String element) throws InterruptedException {
        WebElement current = EdgeAutomate.waitForCompletion(element);
        String cardText = current.getText().toLowerCase();
        if (cardText.contains("search") && cardText.contains("bing")) {
            // Click on card
            current.click();
            Set<String> tabs = driver.getWindowHandles();
            for (String tab : tabs) {
                if (!tab.equals(DailyActivities.home)) {
                    driver.switchTo().window(tab);
                    break;
                }
            }
            Thread.sleep(3000);

            WebElement search = EdgeAutomate.waitForCompletion("//*[@id=\"sb_form_q\"]");
            if (cardText.contains("shopping list")) {
                search.sendKeys("shopping list groceries");
            }
            else if (cardText.contains("symptoms")) {
                search.sendKeys("flu symptoms");
            }
            else if (cardText.contains("real-estate")) {
                search.sendKeys("houses near los angeles");
            }
            else if (cardText.contains("hotels")) {
                search.sendKeys("hotels near houston");
            }
            else if (cardText.contains("restaurant near")) {
                search.sendKeys("yummies close by");
            }
            else if (cardText.contains("latest currency")) {
                search.sendKeys("convert usd to yuan");
            }
            else if (cardText.contains("new recipes")) {
                search.sendKeys("pierogi recipe");
            }

            Thread.sleep(1000);
            driver.close();
            Thread.sleep(1000);
            driver.switchTo().window(DailyActivities.home);
        }
    }

    public static void runner() throws InterruptedException {
        String[][] activities = new String[7][4];
        int counter = 1;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                activities[i][j] = String.format("/html/body/div[1]/div[2]/main/div/ui-view/mee-rewards-dashboard/main/div/mee-rewards-more-activities-card/div/mee-card-group/div/mee-card[%d]/div/card-content/mee-rewards-more-activities-card-item/div/a", counter);
                counter++;
                // Perform all 3 types of activities
                search(activities[i][j]);
                DailyActivities.nonInteractiveActivity(activities[i][j]);
                DailyActivities.interactiveActivity(activities[i][j]);
            }
        }

    }
}
