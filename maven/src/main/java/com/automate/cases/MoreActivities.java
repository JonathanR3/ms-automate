package com.automate.cases;

import com.automate.EdgeAutomate;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Random;
import java.util.Set;

public class MoreActivities {

    public static WebDriver driver = EdgeAutomate.driver;

    public static void search(String element) throws InterruptedException {
        WebElement current = EdgeAutomate.waitForCompletion(element);
        String cardText = current.getText().toLowerCase();
        if (cardText.contains("search on bing") || cardText.contains("search using bing")) {
            if (!cardText.contains("available on")) {
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
                } else if (cardText.contains("symptoms")) {
                    search.sendKeys("flu symptoms");
                } else if (cardText.contains("real-estate")) {
                    search.sendKeys("houses near los angeles");
                } else if (cardText.contains("hotels")) {
                    search.sendKeys("hotels near houston");
                } else if (cardText.contains("restaurant near")) {
                    search.sendKeys("yummies close by");
                } else if (cardText.contains("latest currency")) {
                    search.sendKeys("convert usd to yuan");
                } else if (cardText.contains("new recipes")) {
                    search.sendKeys("pierogi recipe");
                } else if (cardText.contains("craft supplies")) {
                    search.sendKeys("DIY airplanes");
                } else if (cardText.contains("fragrance product")) {
                    search.sendKeys("mens cologne");
                } else if (cardText.contains("next book")) {
                    search.sendKeys("fantasy books");
                }

                search.sendKeys(Keys.ENTER);
                Random r = new Random();
                Thread.sleep(5000 + r.nextInt(5000));
                driver.close();
                Thread.sleep(1000);
                driver.switchTo().window(DailyActivities.home);
            }
        }
    }

    public static void runner() throws InterruptedException {
        String[] exploreActivities = new String[10];
        String[] moreActivities = new String[14];

        for (int i = 0; i < 10; i++) {
            int value = i + 1;
            exploreActivities[i] = String.format("//*[@id=\"explore-on-bing\"]/mee-card-group/div/mee-card[%d]", value);
            search(exploreActivities[i]);
        }

        for (int i = 0; i < 13; i++) {
            int value = i + 1;
            moreActivities[i] = String.format("//*[@id=\"more-activities\"]/div/mee-card[%d]/div/card-content/mee-rewards-more-activities-card-item/div/a", value);
            DailyActivities.activity(moreActivities[i]);
        }
    }
}
