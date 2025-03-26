package com.automate.cases;
import java.util.Set;
import com.automate.EdgeAutomate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DailyActivities {

    public static WebDriver driver = EdgeAutomate.driver;
    public static final String home = EdgeAutomate.homeTab;

    public static void nonInteractiveActivity(String element) throws InterruptedException {
        WebElement current = EdgeAutomate.waitForCompletion(element);
        current.click();
        Thread.sleep(1000);
        Set<String> tabs = driver.getWindowHandles();
        for (String tab : tabs) {
            if (!tab.equals(home)) {
                driver.switchTo().window(tab);
                break;
            }
        }
        Thread.sleep(1000);
        driver.close();
        Thread.sleep(1000);
        driver.switchTo().window(home);
    }

    public static void interactiveActivity(String element) throws InterruptedException {
        WebElement current = EdgeAutomate.waitForCompletion(element);
        String cardText = current.getText().toLowerCase();
        if (cardText.contains("quiz") || cardText.contains("poll")) {
            current.click();
            Thread.sleep(3000);
            Set<String> tabs = driver.getWindowHandles();
            for (String tab : tabs) {
                if (!tab.equals(home)) {
                    driver.switchTo().window(tab);
                    break;
                }
            }
            // Supersonic quiz
            if (cardText.contains("quiz")) {
                WebElement startQuiz = EdgeAutomate.waitForCompletion("//*[@id=\"rqStartQuiz\"]");
                startQuiz.click();
                Thread.sleep(3000);

                int problemsSolved = 0;
                while (problemsSolved < 3) {
                    WebElement firstChoice = EdgeAutomate.waitForCompletion("//*[@id=\"rqAnswerOption0\"]");
                    firstChoice.click();
                    Thread.sleep(3000);
                    WebElement toolTip = EdgeAutomate.waitForCompletion("//*[@id=\"rqAnsStatus\"]");
                    // Loop through answer choices
                    for (int i = 1; i <= 3; i++) {
                        if (toolTip.getText().toLowerCase().contains("oops, try again!")) {
                            Thread.sleep(2000);

                            WebElement nextChoice = EdgeAutomate.waitForCompletion(String.format("//*[@id=\"rqAnswerOption%d\"]", i));
                            nextChoice.click();
                        }
                    }

                    problemsSolved++;
                }
            }
            // Poll
            else if (cardText.contains("poll")) {
                WebElement pollChoice = EdgeAutomate.waitForCompletion("//*[@id=\"btoption0\"]");
                pollChoice.click();
            }
            Thread.sleep(1000);
            driver.close();
            Thread.sleep(1000);
            driver.switchTo().window(home);
            System.out.println("Current Window Handle: " + driver.getWindowHandle());
        }
    }

    public static void runner() throws InterruptedException {
        // Run for all 3 daily set cards
        for (int i = 1; i <= 3; i++) {
            nonInteractiveActivity(String.format("//*[@id=\"daily-sets\"]/mee-card-group[1]/div/mee-card[%d]/div/card-content/mee-rewards-daily-set-item-content/div/a", i));
            interactiveActivity(String.format("//*[@id=\"daily-sets\"]/mee-card-group[1]/div/mee-card[%d]/div/card-content/mee-rewards-daily-set-item-content/div/a", i));
        }
    }
}
