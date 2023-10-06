package org.fxbytes.web.base;

import java.io.IOException;
import java.time.Duration;

import org.fxbytes.web.utilities.BrokenLinkTest;
import org.fxbytes.web.utilities.FindAndPrintBrokenLinks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class BaseClass extends BrokenLinkTest{
	public WebDriver driver;
	@Test
	public void baseTest() throws InterruptedException, IOException{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Fxbytes\\eclipse-workspace\\Fxbytes\\src\\main\\resources\\chromedriver.exe");
		ChromeOptions co = new ChromeOptions();
		co.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(co);
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.navigate().to("https://speaker10.legalops.com/admin");
		/*Rescrticted Page handling*/
		driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys("user13");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("agoSLTeE");
		driver.findElement(By.xpath("//button[@id='submit_login']")).click();
		driver.manage().window().maximize();
		//Thread.sleep(2000);
		driver.navigate().to("https://speaker10.legalops.com/admin");
		driver.findElement(By.xpath("//input[@name='user_email']")).sendKeys("mayur.charvande+1001@fxbytes.com");
		driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys("Mayur@123");
		Thread.sleep(15000);
		driver.findElement(By.xpath("//button[@id='submit_login']")).click();
		Thread.sleep(60000);
		/*Now you are going to add the MFA code and captcha and then you manually have to click on the login button 
		 * because captch again reseting in the 30 sec*/
	
		  System.out.println("a1"); 
		  BrokenLinkTest bl = new BrokenLinkTest();
		  bl.brokenLinks(driver); Thread.sleep(2000);
	
		System.out.println("One Broken Links test Done & now...");
		FindAndPrintBrokenLinks pbl = new FindAndPrintBrokenLinks();
		pbl.printBrokenLink(driver);
		System.out.println("a2");
		
	}
}
