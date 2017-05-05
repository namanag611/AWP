package Core;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class CreateDriver {
	
	ResourceBundle rb=ResourceBundle.getBundle("Elements");
	public WebDriver fd;
	Logger log = Logger.getLogger("devpinoyLogger");
	
	@Parameters("browser")
	@BeforeTest
	public void start(String browser)
	{
		String dir = System.getProperty("user.dir");
		String file_path = dir+"\\Drivers";
		String ChromeDriverPath= file_path+"\\chromedriver.exe";
		String FirefoxDriverPath = file_path+"\\geckodriver.exe";
		String IEDriverPath = file_path+"\\IEDriverServer.exe";
		if(browser.equalsIgnoreCase("firefox-WOP"))
		{
			ProfilesIni profile = new ProfilesIni();
			FirefoxProfile myprofile = profile.getProfile("TE-WOP");
			fd=new FirefoxDriver(myprofile);
			log.debug("Firefox browser started.");
		}
		else if(browser.equalsIgnoreCase("firefox-WP"))
		{
			ProfilesIni profile = new ProfilesIni();
			FirefoxProfile myprofile = profile.getProfile("TE-WP");
			fd=new FirefoxDriver(myprofile);
			log.debug("Firefox browser started.");
		}
		else if(browser.equalsIgnoreCase("chrome")){
			//Open Chrome
			System.setProperty("webdriver.chrome.driver", ChromeDriverPath);
			fd = new ChromeDriver();
			log.debug("Chrome browser started.");
		}
		else if(browser.equalsIgnoreCase("ie")){
			//Open IE
			System.setProperty("webdriver.ie.driver", IEDriverPath);
			fd = new InternetExplorerDriver();
			log.debug("Internet explorer browser started.");
		}
		fd.manage().window().maximize();
		
	}
	
	@AfterTest
	public void end()
	{
		fd.quit();
		log.debug("Browser closed.");
	}
	

}
