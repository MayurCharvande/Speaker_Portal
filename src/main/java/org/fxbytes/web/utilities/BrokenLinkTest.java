package org.fxbytes.web.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BrokenLinkTest{

	    public void brokenLinks(WebDriver driver){

			/*
			 * // Set the path to your ChromeDriver executable
			 * System.setProperty("webdriver.chrome.driver", "path_to_chromedriver.exe");
			 * 
			 * // Initialize ChromeDriver WebDriver driver = new ChromeDriver();
			 * 
			 * // Open the website you want to test driver.get("http://www.example.com");
			 */

	        // Find all links on the page
	        List<WebElement> links = driver.findElements(By.tagName("a"));

	        // Loop through each link and test if it's a broken link
	        for (WebElement link : links) {
	            String url = link.getAttribute("href");
	            if (url != null && !url.isEmpty()) {
	                // Use HttpURLConnection to check the status code of the link
	                int statusCode = getResponseStatus(url);
	                if (statusCode != 200) {
	                    System.out.println("Broken Link: " + url + " - Status Code: " + statusCode);
	                }
	            }
	        }

	        // Close the WebDriver
	   // driver.quit();
	    }

	    // Helper method to get the HTTP response status code
	    public static int getResponseStatus(String url) {
	        try {
	            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) new java.net.URL(url).openConnection();
	            connection.setRequestMethod("HEAD");
	            return connection.getResponseCode();
	        } catch (Exception e) {
	            return 404; // Return 404 if there's an exception (broken link)
	        }
	    }
	

}
