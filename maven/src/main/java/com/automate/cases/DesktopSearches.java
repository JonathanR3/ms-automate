package com.automate.cases;

import com.automate.EdgeAutomate;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.*;

public class DesktopSearches {

    public static WebDriver driver = EdgeAutomate.driver;

    public void runner() throws FileNotFoundException, InterruptedException {
        driver.get("https://www.bing.com/");
        ClassLoader loader = getClass().getClassLoader();
        // Create input stream for resource file
        InputStream input = loader.getResourceAsStream("words.txt");
        if (input == null) {
            throw new IllegalArgumentException("File not found!");
        }
        // Scan input from input stream
        Scanner scan = new Scanner(new BufferedReader(new InputStreamReader(input)));
        ArrayList<String> wordList = new ArrayList<>();

        while (scan.hasNextLine()) {
            wordList.add(scan.nextLine());
        }
        scan.close();
        Collections.shuffle(wordList);

        List<String> subList = wordList.subList(0, 30);

        WebElement mainSearchBar = EdgeAutomate.waitForCompletion("//*[@id=\"sb_form_q\"]");
        mainSearchBar.sendKeys(subList.getFirst());
        mainSearchBar.sendKeys(Keys.ENTER);
        for (int i = 1; i < subList.size(); i++) {
            try {
                Random r = new Random();
                Thread.sleep(5000 + r.nextInt(5000));
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
