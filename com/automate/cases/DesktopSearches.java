package com.automate.cases;

import com.automate.EdgeAutomate;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DesktopSearches {

    public static WebDriver driver = EdgeAutomate.driver;

    public static void runner() throws FileNotFoundException, InterruptedException {
        driver.get("https://www.bing.com/");
        Scanner scan = new Scanner(new File("com/automate/words.txt"));
        ArrayList<String> wordList = new ArrayList<>();

        while (scan.hasNextLine()) {
            wordList.add(scan.nextLine());
        }
        scan.close();
        Collections.shuffle(wordList);

        List<String> subList = wordList.subList(0, 30);

        WebElement mainSearchBar = EdgeAutomate.waitForCompletion("//*[@id=\"sb_form_q\"]");
        mainSearchBar.sendKeys(subList.get(0));
        mainSearchBar.sendKeys(Keys.ENTER);
        for (int i = 1; i < subList.size(); i++) {
            try {
                Thread.sleep(10000);
                mainSearchBar = EdgeAutomate.waitForCompletion("//*[@id=\"sb_form_q\"]");
                mainSearchBar.clear();
                mainSearchBar.sendKeys(subList.get(i));
                mainSearchBar.sendKeys(Keys.ENTER);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
