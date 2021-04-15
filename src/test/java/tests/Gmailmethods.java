package tests;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Gmailmethods 
{
	public RemoteWebDriver driver;
	public String launch(String bn, String d, String c) throws Exception
	{
		if(bn.equalsIgnoreCase("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
		}
		else if(bn.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		}
		else if(bn.equalsIgnoreCase("edge"))
		{
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver();
		}
		else if(bn.equalsIgnoreCase("opera"))
		{
			WebDriverManager.operadriver().setup();
			driver=new OperaDriver();
		}
		else
		{
			//Set ie browser zoom level 100% manually
			WebDriverManager.iedriver().setup();
			driver=new InternetExplorerDriver();
		}	
		driver.manage().window().maximize();
		Thread.sleep(2000);
		driver.get("d");
		return("Done");
	}
	public String fill(String e, String d, String c) throws Exception
	{
		Thread.sleep(5000);
		driver.findElement(By.xpath(e)).sendKeys(d);
		return("Done");
	}
	public String click(String e, String d, String c) throws Exception
	{
		Thread.sleep(2000);
		driver.findElement(By.xpath(e)).click();
		return("Done");
	}
	public String validateUserID(String e, String d, String c) throws Exception
	{
		Thread.sleep(2000);
		try
		{
			if (c.equalsIgnoreCase("validate_userid") && driver.findElement(By.xpath("//*(@name='password')")).isDisplayed())
			{
				return("Passed");
			}
			else if(c.equalsIgnoreCase("invalide_userid") && driver.findElement
									(By.xpath("(//*[contains(text(),'find your Google Account')])[2])|(//div[contains(text(),'Enter a valid email')])")).isDisplayed())
			{
				return("Passed");
			}
			else if(c.equalsIgnoreCase("blank") && 
							driver.findElement(By.xpath("(//*[contains(text(),'Enter an email')])[2]")).isDisplayed())
	 	    {
	 		   return("Passed");
	 	    }
		    else
		    {
		    	String temp=this.screenshot();
				return("Test Failed & goto "+temp);
			}
		}
		catch(Exception ex)
		{
			String temp=this.screenshot();
			return("Test interrupted due to "+ex.getMessage()+" & goto "+temp);
		}
	}
	public String validate_pwd(String e, String d, String c) throws Exception
	   {
	       try
	       {
	    	   if(c.equalsIgnoreCase("valid") && driver.findElement(By.xpath("//*[text()='Compose']")).isDisplayed())
	    	   {
	    		   return("Passed"); 
	    	   }
	    	   else if (c.equalsIgnoreCase("blank") && driver.findElement(By.xpath("//*[text()='Enter a password']")).isDisplayed())
	    	   {
	    			   return("Passed");
	    	   }
	    	   else if(c.equalsIgnoreCase("invalid") && driver.findElement(By.xpath("//*[contains(text(),'Wrong password') or " + "contains(text(),'Your password was changed')]")).isDisplayed())
	    	   {
	    		   return("Passed");
	    	   }
	    	   else 
               {
             	  String temp=this.screenshot();
             	  return("Test Failed & goto "+temp);
               }
	       }
	       catch(Exception ex) 
	       {
	    	   String temp=this.screenshot();
			   return("Test interrupted due to "+ex.getMessage()+" & goto "+temp);
	       }
		
		}
	   public void close()
	   {
		   driver.quit();
	   }
	   private String screenshot() throws Exception
	   {
		   SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		   Date dt=new Date();
		   String fn=sf.format(dt)+".png";
		   File src=driver.getScreenshotAs(OutputType.FILE);
		   File dest=new File(fn);
		   FileHandler.copy(src,dest);
		   return(dest.getAbsolutePath());
	   }	
	
}
