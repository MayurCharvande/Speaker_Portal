package org.fxbytes.web.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FindAndPrintBrokenLinks {
    public WebDriver printBrokenLink(WebDriver driver) {
        List<WebElement> linkslist = driver.findElements(By.tagName("a"));
        linkslist.addAll(driver.findElements(By.tagName("img")));
        System.out.println("Size of full links & images: " + linkslist.size());

        List<WebElement> activelinks = new ArrayList<WebElement>();

        for (WebElement link : linkslist) {
            String href = link.getAttribute("href");
            if (href != null && !href.contains("javascript")) {
                activelinks.add(link);
            }
        }

        System.out.println("Size of active links and images: " + activelinks.size());

        for (WebElement activelink : activelinks) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(activelink.getAttribute("href")).openConnection();
                connection.setConnectTimeout(5000); // 5 seconds timeout
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the content of the page
                    String content = getResponseContent(connection);

                    // Perform content-based validation here
                    if (!isValidContent(content)) {
                        System.out.println("Broken link: " + activelink.getAttribute("href"));
                    }
                } else {
                    System.out.println("Non-OK response for URL: " + activelink.getAttribute("href") + " (Response Code: " + responseCode + ")");
                }
                connection.disconnect();
            } catch (MalformedURLException e) {
                System.out.println("Malformed URL: " + activelink.getAttribute("href"));
            } catch (IOException e) {
                System.out.println("IOException for URL: " + activelink.getAttribute("href"));
            }
        }

        return driver;
    }

    private String getResponseContent(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        }
    }

    private boolean isValidContent(String content) {
        // Perform content-based validation here
        // You can use libraries like Jsoup to parse the content and check for specific elements or keywords.
        // Example:
        Document doc = Jsoup.parse(content);
        Elements validationElements = doc.select("some-selector");
        return !validationElements.isEmpty();
    }
}
