package POM;

import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Page1 {

	ResourceBundle rb= ResourceBundle.getBundle("Elements");
	Logger log = Logger.getLogger("devpinoyLogger");
	WebDriver wdr;
	public Page1(WebDriver any_wdr)
	{
		wdr=any_wdr;
	}

	public void EnterUserName(String username)
	{
		wdr.findElement(By.xpath(rb.getString("username"))).sendKeys(username);
		log.debug("Entered Username: "+username);
		System.out.println("Entered Username: "+username);
	}
	public void EnterPassword(String password)
	{
		wdr.findElement(By.xpath(rb.getString("password"))).sendKeys(password);
		log.debug("Entered Password: ******");
		System.out.println("Entered Password: ******");
	}

	public void ClickLogin()
	{
		wdr.findElement(By.xpath(rb.getString("login"))).click();
		log.debug("Click on Login");
		System.out.println("Clicked on Login");
	}

	public void findtasknumber(String tasknumber)
	{
		String xpathoftask_01 = "//*[contains(text(),'";
		String xpathoftask_02 = tasknumber+"')]";
		String xpathoftask = xpathoftask_01 + xpathoftask_02;
		wdr.findElement(By.xpath(xpathoftask)).click();
		log.debug("Finding task number: "+tasknumber);
	}
	
	public String getcategory()
	{
		List <WebElement> category= wdr.findElements(By.xpath(rb.getString("category")));
		String categoryname=category.get(0).getText();
		log.debug("Got Category text: "+categoryname);
		return categoryname;
	}
	
	public String getsummary()
	{
		List <WebElement> summary= wdr.findElements(By.xpath(rb.getString("summary")));
		String summaryname=summary.get(2).getText();
		log.debug("Got Summary text: "+summaryname);
		return summaryname;
	}

}

