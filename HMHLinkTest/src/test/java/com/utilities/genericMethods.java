package com.utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class genericMethods  {
	public static WebDriver driver;
	static List<WebElement> elements;
	static WebDriverWait wait30;
	WebDriverWait wait10;
	public static String browserTitle, textbyxpath, currURL, uri, resmess, parentWindow, alertText, guid, date;
	static WebElement ele_xpath;
	public static Properties object,prop;
	public static Actions action;
	public static JavascriptExecutor executor = (JavascriptExecutor)driver;
	public static WebElement element = null;
	public static List<WebElement> itemsbyxpath;
	public static int statusCode;
	public Alert alert;

	public static boolean returnval;
	public static String Prog_title, inp_location, inp_disp_title, inp_desc, inp_compo, inp_uri, inp_inst, inp_std, inp_assign,attribute, textValue,attributeValue, FileName;



	/**
	 * To load properties from properties file
	 * 
	 */
	public static Properties loadPropertiesFile(String propFilePath) {
		Properties properties = null;
		try {
			properties = new Properties();
			FileInputStream fis = new FileInputStream(propFilePath);
			properties.load(fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * To read the locator properties from config file
	 * 
	 */
	public static String[] readlocators(String locatorKey) {

		String objectFileName = "src/test/resources/config/locators.properties";
		object = loadPropertiesFile(objectFileName);
		String[] locatorMethodName = null;
		try {
			locatorKey = locatorKey.replace(" ", "").replace(":", "");
			String objectValue = object.getProperty(locatorKey);
			locatorMethodName = objectValue.split("#");

		}
		catch(NoSuchElementException e) 
		{
			ReportGenerate.writeResult("readlocators1.0 ", "readlocators", "readlocators", " Validate readlocators",
					" Validate readlocators", "Unable to readlocators: "+e, "Fail", " ");
		} 

		return locatorMethodName;
	}

	/**
	 * To load data from property file
	 * 
	 */
	public static Properties dataPropertiesFile() {
		try {
			prop = new Properties();
			FileInputStream con = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\config\\data.properties");
			prop.load(con);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}


	

	
	public void login() throws InterruptedException, IOException {
		try {
			invokeApplication(Constants.browser, Constants.URL);
			Thread.sleep(3000);

			waitForElementToBeClickable("username");
			SendValue("username", Constants.username);

			waitForElementToBeClickable("password");
			SendValue("password", Constants.password);

			waitForElementToBeClickable("logIn");
			ClickElement("logIn");
			waitUntilPageLoad();
			ReportGenerate.writeResult("Sigin", "Validate sigin in", " "," ", "", "Sigin in successfully", "Sigin in successfully", "PASS");
			Reports.Pass("Login Successful",  Constants.username + " " + Constants.password);
		}

		catch(Exception e)
		{
			Reports.Pass("Login Unsuccessful",  "Logined unsuccessful "+e);
		}
		finally
		{ 
			Reports.after();
		}
	}

	public static void Browserquit() throws IOException, ParseException
	{
		try {

			driver.quit();
		} 
		catch (WebDriverException exe)
		{
			ReportGenerate.writeResult("Close the browser ", "Close browser", "Closing all browser", " Validate browser are closing",
					" Validate browser are closing", "Unable to close the browser", "Fail", " ");
		} 	

	}

	public static void BrowserClose() throws IOException, ParseException
	{
		try {

			driver.close();
		} 
		catch (WebDriverException exe)
		{
			ReportGenerate.writeResult("Close the browser ", "Close browser", "Closing all browser", " Validate browser are closing",
					" Validate browser are closing", "Unable to close the browser", "Fail", " ");
		} 	

	}

	public static String addScreenCapture() throws IOException, InterruptedException {
		Thread.sleep(2000);

		Date dat = new Date();

		FileName = dat.toString().replace(":", "").replace(" ", "") + ".png";
		TakesScreenshot scrnshot = ((TakesScreenshot) driver);
		File scrFile = scrnshot.getScreenshotAs(OutputType.FILE);

		File Dest = new File(System.getProperty("user.dir") + "\\Reports\\screenshot\\"+ FileName);
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}


	/**
	 * Switch to default frame
	 */
	public static void defaultContentFrame() {
		try {
			driver.switchTo().defaultContent();
			ReportGenerate.writeResult("default Content Frame1.0 ", " default Content Frame", "default Content Frame", " Validate default Content Frame",
					" Validate default Content Frame", "Able to switch default Content Frame ", "Pass", " ");
		}  catch (Exception e) {
			ReportGenerate.writeResult("default Content Frame1.0 ", " default Content Frame", "default Content Frame", " Validate default Content Frame",
					" Validate default Content Frame", "Unable to switch default Content Frame "+ e, "Fail", " ");
		}
	}

	/**
	 * Switch to webelement frame
	 */
	public static void switchToFrameWebElement(String locatorkey) {

		WebElement element=null;
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);
		try {
			String[] locatorMethodName = readlocators(locatorkey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {

			System.out.println("Issue on locator properties: "+ e);
		}

		try {
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
				element = driver.findElement(By.id((locatorValue)));
				driver.switchTo().frame(element);
				break;
			case "name":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
				element = driver.findElement(By.name((locatorValue)));
				driver.switchTo().frame(element);
				break;
			case "class":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.className(locatorValue)));
				element = driver.findElement(By.className((locatorValue)));
				driver.switchTo().frame(element);
				break;
			case "linkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
				element = driver.findElement(By.linkText((locatorValue)));
				driver.switchTo().frame(element);
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
				element = driver.findElement(By.partialLinkText((locatorValue)));
				driver.switchTo().frame(element);
				break;
			case "tagname":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.tagName(locatorValue)));
				element = driver.findElement(By.tagName((locatorValue)));
				driver.switchTo().frame(element);
				break;
			case "css":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
				element = driver.findElement(By.cssSelector((locatorValue)));
				driver.switchTo().frame(element);
				break;
			case "xpath":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
				element = driver.findElement(By.xpath((locatorValue)));
				driver.switchTo().frame(element);
				break;

			default:
				break;
			}
		}
		catch(NoSuchElementException e) 
		{
			ReportGenerate.writeResult("switch To Frame WebElement1.0 ", "switch To Frame WebElement", "switch To Frame WebElement"," ", " Validate switch To Frame WebElement", 
					" Validate switch To Frame WebElement", "Unable to switch To Frame WebElement: "+e, "Fail");

		}
		catch (WebDriverException e)
		{
			ReportGenerate.writeResult("switch To Frame WebElement1.0 ", "switch To Frame WebElement", "switch To Frame WebElement"," Validate switch To Frame WebElement", 
					" Validate switch To Frame WebElement", "Unable to switch To Frame WebElement: "+e, "Fail", " ");
		} 

	}



	/*
	 * 
	 * Switch to window using title
	 */
	public static void SwitchUsingTitle(String title) throws InterruptedException {
		Thread.sleep(3000);
		Set<String> totalWindow = driver.getWindowHandles();

		if (totalWindow.size() >= 1) {
			for (String loopWindow : totalWindow) {
				System.out.println(loopWindow);
				driver.switchTo().window(loopWindow);
				System.out.println(driver.getTitle().trim() + "******" + title);
				if (driver.getTitle().trim().contains(title)) {
					break;
				}
			}
		} else {
			System.out.println("No Windows was Found");
		}
	}


	/**
	 * Wait for an element
	 * 
	 */
	public static WebElement getWebElement(String locatorKey) throws InterruptedException {

		WebElement element = null;
		String locatorMethod = null;
		String locatorValue = null;

		try {
			String[] locatorMethodName = readlocators(locatorKey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {
			ReportGenerate.writeResult("readlocatorst1.0 ", "getWebElementWait", "getWebElementWait", " Validate getWebElementWait",
					" Validate getWebElementWait", "Unable to getWebElementWait: "+e, "Fail", " ");
		}
		try {
			switch (locatorMethod) {
			case "id":
				element = driver.findElement(By.id((locatorValue)));
				break;
			case "name":
				element = driver.findElement(By.name((locatorValue)));
				break;
			case "class":
				element = driver.findElement(By.className((locatorValue)));
				break;
			case "linkText":
				element = driver.findElement(By.linkText((locatorValue)));
				break;
			case "partiallinkText":
				element = driver.findElement(By.partialLinkText((locatorValue)));
				break;
			case "tagname":
				element = driver.findElement(By.tagName((locatorValue)));
				break;
			case "css":
				element = driver.findElement(By.cssSelector((locatorValue)));
				break;
			case "xpath":
				element = driver.findElement(By.xpath((locatorValue)));
				break;

			default:
				break;
			}
		} catch (NoSuchWindowException e) {
			ReportGenerate.writeResult("getWebElementWait1.0 ", "getWebElementWait", "getWebElementWait", " Validate getWebElementWait",
					" Validate getWebElementWait", "Unable to getWebElementWait: "+e, "Fail", " ");
		} catch (Exception e) {
			ReportGenerate.writeResult("getWebElementWait1.0 ", "ClickEnter", "getWebElementWait", " Validate getWebElementWait",
					" Validate getWebElementWait", "Unable to getWebElementWait: "+e, "Fail", " ");
		}
		return element;
	}

	/**
	 * Wait for an element
	 * 
	 */
	public static WebElement waitVisibleWebElement(String locatorKey) throws InterruptedException {

		WebElement element = null;
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);

		try {
			String[] locatorMethodName = readlocators(locatorKey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {
			ReportGenerate.writeResult("readlocators1.0 ", "waitVisibleWebElement", "waitVisibleWebElement", " Validate waitVisibleWebElement",
					" Validate waitVisibleWebElement", "Unable to waitVisibleWebElement: "+e, "Fail", " ");
		}
		try {
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
				element = driver.findElement(By.id((locatorValue)));
				break;
			case "name":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
				element = driver.findElement(By.name((locatorValue)));
				break;
			case "class":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
				element = driver.findElement(By.className((locatorValue)));
				break;
			case "linkText":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
				element = driver.findElement(By.linkText((locatorValue)));
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
				element = driver.findElement(By.partialLinkText((locatorValue)));
				break;
			case "tagname":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
				element = driver.findElement(By.tagName((locatorValue)));
				break;
			case "css":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
				element = driver.findElement(By.cssSelector((locatorValue)));
				break;
			case "xpath":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
				element = driver.findElement(By.xpath((locatorValue)));
				break;

			default:
				break;
			}
		} catch (NoSuchWindowException e) {
			System.out.println("Window Already closed and elment is not visible further ...");
			ReportGenerate.writeResult("waitVisibleWebElement1.0 ", "waitVisibleWebElement", "waitVisibleWebElement", " Validate waitVisibleWebElement",
					" Validate waitVisibleWebElement", "Unable to waitVisibleWebElement: "+e, "Fail", " ");
		} catch (Exception e) {
			ReportGenerate.writeResult("waitVisibleWebElement1.0 ", "waitVisibleWebElement", "waitVisibleWebElement", " Validate waitVisibleWebElement",
					" Validate waitVisibleWebElement", "Unable to waitVisibleWebElement: "+e, "Fail", " ");
		}
		return element;
	}


	/**
	 * Verify element is displayed
	 */
	public static boolean verifyElementIsDisplay(String locatorKey) throws InterruptedException {
		Thread.sleep(3000);
		element = getWebElement(locatorKey);
		boolean status = element.isDisplayed();
		try {
			if (status==true)
			{
				return true;
			}
			else
				return false;
		} catch (NoSuchElementException e) {

			return false;
		}

	}


	/**
	 * wait and click the element
	 * @throws InterruptedException 
	 * 
	 */
	public static void ClickElement(String locatorkey) throws InterruptedException {
		//WebElement element = null;
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);
		try {
			String[] locatorMethodName = readlocators(locatorkey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {

			System.out.println("Issue on locator properties: "+ e);
		}

		try {
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
				driver.findElement(By.id((locatorValue))).click();
				break;
			case "name":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
				driver.findElement(By.name((locatorValue))).click();
				break;
			case "class":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.className(locatorValue)));
				driver.findElement(By.className((locatorValue))).click();
				break;
			case "linkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
				driver.findElement(By.linkText((locatorValue))).click();
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
				driver.findElement(By.partialLinkText((locatorValue))).click();
				break;
			case "tagname":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.tagName(locatorValue)));
				driver.findElement(By.tagName((locatorValue))).click();
				break;
			case "css":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
				driver.findElement(By.cssSelector((locatorValue))).click();
				break;
			case "xpath":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
				driver.findElement(By.xpath((locatorValue))).click();

				break;

			default:
				break;
			}
		}
		catch(NoSuchElementException e) 
		{
			ReportGenerate.writeResult("ClickElement1.0 ", "ClickEnter", "ClickEnter", " Validate ClickEnter",
					" Validate ClickEnter", "Unable to ClickEnter: "+e, "Fail", " ");
			String details = e.getMessage();
			Reports.Warning(details, "");

		}

	}

	public static void waitForElementToBeClickable(String locatorkey) {
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);
		try {
			String[] locatorMethodName = readlocators(locatorkey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {

			System.out.println("Issue on locator properties: "+ e);
		}

		try {
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.elementToBeClickable(By.id(locatorValue)));
				break;
			case "name":
				wait30.until(ExpectedConditions.elementToBeClickable(By.name(locatorValue)));
				break;
			case "class":
				wait30.until(ExpectedConditions.elementToBeClickable(By.className(locatorValue)));
				break;
			case "linkText":
				wait30.until(ExpectedConditions.elementToBeClickable(By.linkText(locatorValue)));
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(locatorValue)));
				break;
			case "tagname":
				wait30.until(ExpectedConditions.elementToBeClickable(By.tagName(locatorValue)));
				break;
			case "css":
				wait30.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locatorValue)));
				break;
			case "xpath":
				wait30.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorValue)));
				break;

			default:
				break;
			}
		} catch (Exception e) {
			ReportGenerate.writeResult("waitToBeclickable","waitToBeclickable" ,
					" ",
					"The Locator Method and Value should be available in the page", e.getMessage(), "Fail", " Find Element By " + locatorMethod + " with Value " + locatorValue +" in the webpage"," ");
		}

	}

	/**
	 * wait and send the value
	 */
	public static void SendValue(String locatorkey,String value) {
		WebElement element = null;
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);
		try {
			String[] locatorMethodName = readlocators(locatorkey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {

			System.out.println("Issue on locator properties: "+ e);
		}

		try {
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
				driver.findElement(By.id((locatorValue))).sendKeys(value);
				break;
			case "name":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
				driver.findElement(By.name((locatorValue))).sendKeys(value);
				break;
			case "class":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.className(locatorValue)));
				driver.findElement(By.className((locatorValue))).sendKeys(value);
				break;
			case "linkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
				driver.findElement(By.linkText((locatorValue))).sendKeys(value);
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
				driver.findElement(By.partialLinkText((locatorValue))).sendKeys(value);
				break;
			case "tagname":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.tagName(locatorValue)));
				driver.findElement(By.tagName((locatorValue))).sendKeys(value);
				break;
			case "css":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
				driver.findElement(By.cssSelector((locatorValue))).sendKeys(value);
				break;
			case "xpath":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
				driver.findElement(By.xpath((locatorValue))).sendKeys(value);
				break;

			default:
				break;
			}
		}
		catch(NoSuchElementException e) 
		{
			ReportGenerate.writeResult("SendValue1.0 ", "ClickEnter", "ClickEnter", " Validate ClickEnter",
					" Validate ClickEnter", "Unable to ClickEnter: "+e, "Fail", " ");

			//System.out.println("Error in click element");
		}

	}



	/**
	 * get the attribute value
	 */
	public static String GetAttributeValue(String locatorkey,String value) {

		WebElement element=null;
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);
		try {
			String[] locatorMethodName = readlocators(locatorkey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {

			System.out.println("Issue on locator properties: "+ e);
		}

		try {
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
				element = driver.findElement(By.id((locatorValue)));
				attributeValue = element.getAttribute(value);
				break;
			case "name":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
				element = driver.findElement(By.name((locatorValue)));
				attributeValue = element.getAttribute(value);
				break;
			case "class":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.className(locatorValue)));
				element = driver.findElement(By.className((locatorValue)));
				attributeValue = element.getAttribute(value);
				break;
			case "linkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
				element = driver.findElement(By.linkText((locatorValue)));
				attributeValue = element.getAttribute(value);
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
				element = driver.findElement(By.partialLinkText((locatorValue)));
				attributeValue = element.getAttribute(value);
				break;
			case "tagname":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.tagName(locatorValue)));
				element = driver.findElement(By.tagName((locatorValue)));
				attributeValue = element.getAttribute(value);
				break;
			case "css":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
				element = driver.findElement(By.cssSelector((locatorValue)));
				attributeValue = element.getAttribute(value);
				break;
			case "xpath":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
				element = driver.findElement(By.xpath((locatorValue)));
				attributeValue = element.getAttribute(value);
				break;

			default:
				break;
			}
		}
		catch(NoSuchElementException e) 
		{
			ReportGenerate.writeResult("GetAttributeValue1.0 ", "GetAttributeValue", "GetAttributeValue", " Validate GetAttributeValue",
					" Validate GetAttributeValue", "Unable to GetAttributeValue: "+e, "Fail", " ");

		}

		return attributeValue;

	}


	/**
	 * get the attribute value
	 */
	public static String javaScriptClick(String locatorkey) {

		WebElement element=null;
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);
		try {
			String[] locatorMethodName = readlocators(locatorkey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {

			System.out.println("Issue on locator properties: "+ e);
		}

		try {
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
				element = driver.findElement(By.id((locatorValue)));
				executor.executeScript("arguments[0].click();", element);
				break;
			case "name":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
				element = driver.findElement(By.name((locatorValue)));
				executor.executeScript("arguments[0].click();", element);
				break;
			case "class":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.className(locatorValue)));
				element = driver.findElement(By.className((locatorValue)));
				executor.executeScript("arguments[0].click();", element);
				break;
			case "linkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
				element = driver.findElement(By.linkText((locatorValue)));
				executor.executeScript("arguments[0].click();", element);
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
				element = driver.findElement(By.partialLinkText((locatorValue)));
				executor.executeScript("arguments[0].click();", element);
				break;
			case "tagname":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.tagName(locatorValue)));
				element = driver.findElement(By.tagName((locatorValue)));
				executor.executeScript("arguments[0].click();", element);
				break;
			case "css":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
				element = driver.findElement(By.cssSelector((locatorValue)));
				executor.executeScript("arguments[0].click();", element);
				break;
			case "xpath":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
				element = driver.findElement(By.xpath((locatorValue)));
				executor.executeScript("arguments[0].click();", element);
				break;

			default:
				break;
			}
		}
		catch(NoSuchElementException e) 
		{
			ReportGenerate.writeResult("Click by javascript1.0 ", "Click by javascript", "Click by javascript", " Validate Click by javascript",
					" Validate Click by javascript", "Unable to Click by javascript: "+e, "Fail", " ");

		}

		return attributeValue;

	}

	/**
	 * Mose hover action
	 */
	public static void mouseHoverAction(String locatorkey) {

		WebElement element=null;
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);
		try {
			String[] locatorMethodName = readlocators(locatorkey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {

			System.out.println("Issue on locator properties: "+ e);
		}

		try {
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
				element = driver.findElement(By.id((locatorValue)));
				action.moveToElement(element).perform();
				break;
			case "name":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
				element = driver.findElement(By.name((locatorValue)));
				action.moveToElement(element).perform();
				break;
			case "class":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.className(locatorValue)));
				element = driver.findElement(By.className((locatorValue)));

				action.moveToElement(element).perform();
				break;
			case "linkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
				element = driver.findElement(By.linkText((locatorValue)));
				action.moveToElement(element).perform();
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
				element = driver.findElement(By.partialLinkText((locatorValue)));
				action.moveToElement(element).perform();
				break;
			case "tagname":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.tagName(locatorValue)));
				element = driver.findElement(By.tagName((locatorValue)));
				action.moveToElement(element).perform();
				break;
			case "css":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
				element = driver.findElement(By.cssSelector((locatorValue)));
				action.moveToElement(element).perform();
				break;
			case "xpath":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
				element = driver.findElement(By.xpath((locatorValue)));
				action.moveToElement(element).perform();
				break;

			default:
				break;
			}
		}
		catch(NoSuchElementException e) 
		{
			ReportGenerate.writeResult("mouse Hover Action1.0 ", "mouse Hover Action", "mouse Hover Action", " Validate mouse Hover Action",
					" Validate mouse Hover Action", "Unable to mouse hover Action: "+e, "Fail", " ");

		}


	}



	/**
	 * Webelement
	 */
	public static WebElement webElement(String locatorkey) {

		WebElement element=null;
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);
		try {
			String[] locatorMethodName = readlocators(locatorkey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {

			System.out.println("Issue on locator properties: "+ e);
		}

		try {
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
				element = driver.findElement(By.id((locatorValue)));
				break;
			case "name":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
				element = driver.findElement(By.name((locatorValue)));
				break;
			case "class":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.className(locatorValue)));
				element = driver.findElement(By.className((locatorValue)));
				break;
			case "linkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
				element = driver.findElement(By.linkText((locatorValue)));
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
				element = driver.findElement(By.partialLinkText((locatorValue)));
				break;
			case "tagname":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.tagName(locatorValue)));
				element = driver.findElement(By.tagName((locatorValue)));
				break;
			case "css":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
				element = driver.findElement(By.cssSelector((locatorValue)));
				break;
			case "xpath":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
				element = driver.findElement(By.xpath((locatorValue)));
				break;

			default:
				break;
			}
		}
		catch(NoSuchElementException e) 
		{
			ReportGenerate.writeResult("webElement1.0 ", "webElement", "GetAttributeValue", " Validate webElement",
					" Validate webElement", "Unable to webElement: "+e, "Fail", " ");

		}
		catch (WebDriverException e)
		{
			ReportGenerate.writeResult("webElement1.0 ", "GetAttributeValue", "webElement", " Validate webElement",
					" Validate webElement", "Unable to webElement: "+e, "Fail", " ");
		} 

		return element;

	}


	public static List<WebElement> ListgetWebElements(String locatorKey) {
		List<WebElement> element = null;
		String locatorMethod = null;
		String locatorValue = null;
		try {
			String[] locatorMethodName = readlocators(locatorKey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {
			System.out.println("Issue on locator properties: "+ e);
		}
		try {
			switch (locatorMethod) {
			case "id":
				element = driver.findElements(By.id((locatorValue)));
				break;
			case "name":
				element = driver.findElements(By.name((locatorValue)));
				break;
			case "class":
				element = driver.findElements(By.className((locatorValue)));
				break;
			case "linkText":
				element = driver.findElements(By.linkText((locatorValue)));
				break;
			case "partiallinkText":
				element = driver.findElements(By.partialLinkText((locatorValue)));
				break;
			case "tagname":
				element = driver.findElements(By.tagName((locatorValue)));
				break;
			case "css":
				element = driver.findElements(By.cssSelector((locatorValue)));
				break;
			case "xpath":
				element = driver.findElements(By.xpath((locatorValue)));
				break;

			default:
				break;
			}
		} catch(NoSuchElementException e) 
		{
			ReportGenerate.writeResult("webElement1.0 ", "webElement", " list getelement", " Validate webElement",
					" Validate list webElement", "Unable to webElement: "+e, "Fail", " ");

		}

		return element;
	}


	/**
	 * get text value
	 */
	public static String GetText(String locatorkey) {

		WebElement element=null;
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);
		try {
			String[] locatorMethodName = readlocators(locatorkey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {

			System.out.println("Issue on locator properties: "+ e);
		}

		try {
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
				element = driver.findElement(By.id((locatorValue)));
				textValue = element.getText();
				break;
			case "name":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
				element = driver.findElement(By.name((locatorValue)));
				textValue = element.getText();
				break;
			case "class":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.className(locatorValue)));
				element = driver.findElement(By.className((locatorValue)));
				textValue = element.getText();
				break;
			case "linkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
				element = driver.findElement(By.linkText((locatorValue)));
				textValue = element.getText();
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
				element = driver.findElement(By.partialLinkText((locatorValue)));
				textValue = element.getText();
				break;
			case "tagname":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.tagName(locatorValue)));
				element = driver.findElement(By.tagName((locatorValue)));
				textValue = element.getText();
				break;
			case "css":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
				element = driver.findElement(By.cssSelector((locatorValue)));
				textValue = element.getText();
				break;
			case "xpath":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
				element = driver.findElement(By.xpath((locatorValue)));
				textValue = element.getText();
				break;

			default:
				break;
			}
		}
		catch(NoSuchElementException e) 
		{
			ReportGenerate.writeResult("GetText1.0 ", "GetTextValue", "GetTextValue", " Validate Get Text Value",
					" Validate Get Text Value", "Unable to  Get Text Value: "+e, "Fail", " ");

		}


		return textValue;

	}

	public static void waitUntilPageLoad() throws InterruptedException {

		wait30 = new WebDriverWait(driver, 30);
		Thread.sleep(4000);
		wait30.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState"
						).equals("complete");
			}
		});
	}

	public static void DragSliderRight(String locatorkey,int value)
	{

		WebElement element = null;
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);
		action = new Actions(driver);
		try {
			String[] locatorMethodName = readlocators(locatorkey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {
			ReportGenerate.writeResult("readlocators1.0 ", "DragSliderRight", "DragSliderRight", " Validate DragSliderRight",
					" Validate DragSliderRight", "Unable to DragSliderRight: "+e, "Fail", " ");

		}
		try {

			Thread.sleep(3000);
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
				element = driver.findElement(By.id((locatorValue)));
				action.dragAndDropBy(element,value,0).perform();
				break;
			case "name":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
				element = driver.findElement(By.name((locatorValue)));
				action.dragAndDropBy(element,value,0).perform();
				break;
			case "class":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.className(locatorValue)));
				element = driver.findElement(By.className((locatorValue)));
				action.dragAndDropBy(element,value,0).perform();
				break;
			case "linkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
				element = driver.findElement(By.linkText((locatorValue)));
				action.dragAndDropBy(element,value,0).perform();
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
				element = driver.findElement(By.partialLinkText((locatorValue)));
				action.dragAndDropBy(element,value,0).perform();
				break;
			case "tagname":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.tagName(locatorValue)));
				element = driver.findElement(By.tagName((locatorValue)));
				action.dragAndDropBy(element,value,0).perform();
				break;
			case "css":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
				element = driver.findElement(By.cssSelector((locatorValue)));
				action.dragAndDropBy(element,value,0).perform();
				break;
			case "xpath":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
				element = driver.findElement(By.xpath((locatorValue)));
				action.dragAndDropBy(element,value,0).perform();
				break;

			default:
				break;
			}
		} catch (NoSuchWindowException e) {
			System.out.println("Window Already closed and elment is not visible further ...");
		} catch (Exception e) {
			ReportGenerate.writeResult("DragSliderRight1.0 ", "DragSliderRight", "DragSliderRight", " Validate DragSliderRight",
					" Validate DragSliderRight", "Unable to DragSliderRight: "+e, "Fail", " ");
		}


	}


	public static void DragSliderVertical(String locatorkey,int value)
	{

		WebElement element = null;
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);
		action = new Actions(driver);
		try {
			String[] locatorMethodName = readlocators(locatorkey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {
			ReportGenerate.writeResult("readlocators1.0 ", "DragSliderVertical", "DragSliderVertical", " Validate DragSliderVertical",
					" Validate DragSliderVertical", "Unable to DragSliderVertical: "+e, "Fail", " ");

		}
		try {

			Thread.sleep(3000);
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
				element = driver.findElement(By.id((locatorValue)));
				action.dragAndDropBy(element,0,value).build().perform();
				break;
			case "name":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
				element = driver.findElement(By.name((locatorValue)));
				action.dragAndDropBy(element,0,value).build().perform();
				break;
			case "class":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
				element = driver.findElement(By.className((locatorValue)));
				action.dragAndDropBy(element,0,value).perform();
				break;
			case "linkText":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
				element = driver.findElement(By.linkText((locatorValue)));
				action.dragAndDropBy(element,0,value).build().perform();
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
				element = driver.findElement(By.partialLinkText((locatorValue)));
				action.dragAndDropBy(element,0,value).build().perform();
				break;
			case "tagname":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
				element = driver.findElement(By.tagName((locatorValue)));
				action.dragAndDropBy(element,0,value).build().perform();
				break;
			case "css":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
				element = driver.findElement(By.cssSelector((locatorValue)));
				action.dragAndDropBy(element,0,value).build().perform();
				break;
			case "xpath":
				wait30.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
				element = driver.findElement(By.xpath((locatorValue)));
				action.dragAndDropBy(element, 0 ,value).build().perform();
				break;

			default:
				break;
			}
		} catch (NoSuchWindowException e) {
			System.out.println("Window Already closed and elment is not visible further ...");
		} catch (Exception e) {
			ReportGenerate.writeResult("DragSliderVertical1.0 ", "DragSliderVerticalt", "DragSliderVertical", " Validate DragSliderVertical",
					" Validate DragSliderVertical", "Unable to DragSliderVertical: "+e, "Fail", " ");
		}


	}


	/**
	 * wait and clear the value and send the value
	 */
	public static void ClearSendValue(String locatorkey,String value) {
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);
		try {
			String[] locatorMethodName = readlocators(locatorkey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {

			System.out.println("Issue on locator properties: "+ e);
		}

		try {
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
				driver.findElement(By.id((locatorValue))).clear();
				driver.findElement(By.id((locatorValue))).sendKeys(value);
				break;
			case "name":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
				driver.findElement(By.name((locatorValue))).clear();
				driver.findElement(By.name((locatorValue))).sendKeys(value);
				break;
			case "class":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.className(locatorValue)));
				driver.findElement(By.className((locatorValue))).clear();
				driver.findElement(By.className((locatorValue))).sendKeys(value);
				break;
			case "linkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
				driver.findElement(By.linkText((locatorValue))).clear();
				driver.findElement(By.linkText((locatorValue))).sendKeys(value);
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
				driver.findElement(By.partialLinkText((locatorValue))).clear();
				driver.findElement(By.partialLinkText((locatorValue))).sendKeys(value);
				break;
			case "tagname":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.tagName(locatorValue)));
				driver.findElement(By.tagName((locatorValue))).clear();
				driver.findElement(By.tagName((locatorValue))).sendKeys(value);
				break;
			case "css":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
				driver.findElement(By.cssSelector((locatorValue))).clear();
				driver.findElement(By.cssSelector((locatorValue))).sendKeys(value);
				break;
			case "xpath":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
				driver.findElement(By.xpath((locatorValue))).clear();
				driver.findElement(By.xpath((locatorValue))).sendKeys(value);
				break;

			default:
				break;
			}
		}
		catch(NoSuchElementException e) 
		{
			ReportGenerate.writeResult("ClearSendValue1.0 ", "ClearSendValue", "ClearSendValue", " Validate ClearSendValue",
					" Validate ClearSendValue", "Unable to ClearSendValue: "+e, "Fail", " ");

			//System.out.println("Error in click element");
		}
		catch (WebDriverException e)
		{
			ReportGenerate.writeResult("ClearSendValue1.0 ", "ClearSendValue", "ClearSendValue", " Validate ClearSendValue",
					" Validate ClearSendValue", "Unable to ClearSendValue: "+e, "Fail", " ");
		} 

	}



	/**
	 * wait and click on enter button
	 */
	public static void ClickEnter(String locatorkey) {
		String locatorMethod = null;
		String locatorValue = null;
		wait30 =  new WebDriverWait(driver, 30);
		try {
			String[] locatorMethodName = readlocators(locatorkey);
			locatorMethod = locatorMethodName[0];
			locatorValue = locatorMethodName[1];
		} catch (Exception e) {

			System.out.println("Issue on locator properties: "+ e);
		}

		try {
			switch (locatorMethod) {
			case "id":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
				driver.findElement(By.id((locatorValue))).sendKeys(Keys.ENTER);
				break;
			case "name":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
				driver.findElement(By.name((locatorValue))).sendKeys(Keys.ENTER);
				break;
			case "class":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.className(locatorValue)));
				driver.findElement(By.className((locatorValue))).sendKeys(Keys.ENTER);
				break;
			case "linkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
				driver.findElement(By.linkText((locatorValue))).sendKeys(Keys.ENTER);
				break;
			case "partiallinkText":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
				driver.findElement(By.partialLinkText((locatorValue))).sendKeys(Keys.ENTER);
				break;
			case "tagname":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.tagName(locatorValue)));
				driver.findElement(By.tagName((locatorValue))).sendKeys(Keys.ENTER);
				break;
			case "css":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
				driver.findElement(By.cssSelector((locatorValue))).sendKeys(Keys.ENTER);
				break;
			case "xpath":
				wait30.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
				driver.findElement(By.xpath((locatorValue))).sendKeys(Keys.ENTER);
				break;

			default:
				break;
			}
		}
		catch(NoSuchElementException e) 
		{
			ReportGenerate.writeResult("ClickEnter1.0 ", "ClickEnter", "ClickEnter", " Validate ClickEnter",
					" Validate ClickEnter", "Unable to ClickEnter: "+e, "Fail", " ");

		}
		catch (WebDriverException e)
		{
			ReportGenerate.writeResult("ClickEnter1.0 ", "ClickEnter", "ClickEnter", " Validate ClickEnter",
					" Validate ClickEnter", "Unable to ClickEnter: "+e, "Fail", " ");
		} 

	}


	/**
	 * click by xpath
	 * @throws InterruptedException 
	 * 
	 */
	public static void ClickByXpath(String locatebyxpath) throws InterruptedException
	{
		try {
			/*Wait<WebDriver> wait = new FluentWait<WebDriver>(driver) 
					.withTimeout( 30, TimeUnit.SECONDS ) 
					     .pollingEvery( 5, TimeUnit.SECONDS ) 
					     .ignoring( NoSuchElementException.class, StaleElementReferenceException.class ); */
			wait30 =  new WebDriverWait(driver, 30);

			WebElement ele = driver.findElement(By.xpath(locatebyxpath));
			wait30.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatebyxpath)));
			ele.click();
			waitUntilPageLoad();
		}
		catch (NoSuchElementException e) 
		{
			ReportGenerate.writeResult("ClickByXpath1.0 ", "ClickByXpath", "ClickByXpath", " Validate click by xpath",
					" Validate click by xpath", "Unable to click by xpath: "+e, "Fail", " ");	

		}
		catch (StaleElementReferenceException e)
		{
			ReportGenerate.writeResult("ClickByXpath1.0 ", "ClickByXpath", "ClickByXpath", " Validate click by xpath",
					" Validate click by xpath", "Unable to click by xpath: "+e, "Fail", " ");
		} 
	}


	public WebDriver invokeApplication(String browser, String url) throws InterruptedException, IOException {

		try {
			if (browser.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver", ".\\driver\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				String details = "Browser Started";
				Reports.Pass(details, " ");
			} else if (browser.toLowerCase().equals("chrome")) {
				System.out.println("Chrome Launched");
				System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");
				driver = new ChromeDriver();
				String details = "Browser Started";
				Reports.Pass(details, " ");
			} else if (browser.toLowerCase().equals("firefox")) {
				System.out.println("firefox Launched");
				System.setProperty("webdriver.gecko.driver", ".\\driver\\geckodriver.exe");
				driver = new FirefoxDriver();
				String details = "Browser Started";
				Reports.Pass(details, " ");
			}

			driver.get(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			String details = e.getMessage();
			Reports.Warning(details, "");
		}
		return driver;
	}

	public void browser(String browserType) {
		try {

			if (browserType.equalsIgnoreCase("chrome")) {
				String details = "Browser Started";
				System.setProperty("webdriver.chrome.driver", ".//chromedriver.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				ArrayList<String> switches = new ArrayList<String>();
				capabilities.setCapability("chrome.switches", switches);
				driver = new ChromeDriver(capabilities);
				Reports.Info(details, " ");

			} else if (browserType.equalsIgnoreCase("firefox")) {

				FirefoxProfile fp = new FirefoxProfile();
				fp.setPreference("browser.startup.homepage", "about:blank");
				fp.setPreference("startup.homepage_welcome_url", "about:blank");
				fp.setPreference("startup.homepage_welcome_url.additional", "about:blank");
				//driver = new FirefoxDriver(fp);
				String details = "Browser Started";
				Reports.Info(details, " ");

			}
		} catch (Exception e) {
			String details = e.getMessage();
			Reports.Warning(details, "");
		}

	}

	public static void urlLaunch(String url) {
		try {
			String details = "URL launched Successfully";
			driver.get(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Reports.Info(details, " ");
		} catch (Exception e) {
			String details = e.getMessage();
			Reports.Warning(details, "");
		}
	}

	public void enterValuebyID(String id, String value) {
		try {
			wait10 = new WebDriverWait(driver, 10);
			WebElement element = wait10.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			String details = e.getMessage();
			Reports.Warning(details, "");
		}
	}


	public String getBrowserTitle() {

		try {
			browserTitle = driver.getTitle();

		} catch (NoSuchElementException e) {
			String details = e.getMessage();
			Reports.Warning(details, "");
		}

		return browserTitle;
	}

	public void ClickByID(String id) {
		try {
			driver.findElement(By.id(id)).click();

		} catch (NoSuchElementException e) {
			String details = e.getMessage();
			Reports.Warning(details, "");
		}
	}

	public void ClickByClassName(String classname) {
		try {
			driver.findElement(By.name(classname)).click();

		} catch (NoSuchElementException e) {
			String details = e.getMessage();
			Reports.Warning(details, "");
		}
	}

	public static void clickByXpath(String xpath) throws InterruptedException {
		try {
			WebElement element1 = driver.findElement(By.xpath(xpath));
			element1.click();
			Actions actions = new Actions(driver);

			actions.moveToElement(element1);

		} catch (NoSuchElementException e) {
			String details = e.getMessage();
			Reports.Warning(details, "");
		}
	}

	public static void enterValuebyXpath(String xpath, String value) {
		try {
			WebElement textValue = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			textValue.clear();
			textValue.sendKeys(value);
			textValue.sendKeys(Keys.ENTER);
			//textValue.enterValuebyXpath;
		} catch (Exception e) {
			String details = e.getMessage();
			Reports.Warning(details, "");
		}
	}

	public static String getTextbyxpath(String xpath) {
		try {
			wait30 = new WebDriverWait(driver, 30);
			ele_xpath = wait30.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(xpath))));
			textbyxpath = ele_xpath.getText();
			return textbyxpath;
		} catch (Exception e) {

		}

		return textbyxpath;
	}

	public static void clickElement(String xpath) {
		try {
			WebElement clickElement = (new WebDriverWait(driver, 30))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			clickElement.click();
		} catch (Exception e) {
			String details = e.getMessage();
			Reports.Warning(details, "");
		}
	}

	public static List<WebElement> elementsbyxpath(String xpathlocator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			itemsbyxpath = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpathlocator)));

		} catch (TimeoutException e) {

			/*
			 * genericMethods.getTextbyxpath(".//hmh-card/div/div/span");
			 * System.out.println("No Result found"); ReportGenerate.writeResult("",
			 * Constants.program_title, HMHOneMDS.guid, "","No Result Found", "","",
			 * "fail"); Reports.Fail("No result", genericMethods.textbyxpath);
			 */
		} catch (WebDriverException e) {
			String details = e.getMessage();
			Reports.Warning(details, "");
		}
		return itemsbyxpath;
	}

	public static WebElement elementbyxpath(String xpathlocator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathlocator)));
		} 
		catch (WebDriverException e) {
			String details = e.getMessage();
			Reports.Warning(details, "");
		}
		return element;
	}

	public boolean alertHandle() {
		try {
			alert = driver.switchTo().alert();
			alertText = alert.getText();
			return true;
		} catch (NoAlertPresentException e) {
			return false;

		} catch (WebDriverException e) {
			ReportGenerate.writeResult("", "", "", "","Driver Not Found", " ", "","Fail");
		}
		return false;
	}

	public static WebElement waitForElement(String xpath) {

		try {
			element = (new WebDriverWait(driver, 40))
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

		} catch (Exception e) {

		}

		return element;
	}

	public String getAttrbyXpath(String xpath) throws IOException {
		try {
			attribute = driver.findElement(By.xpath(xpath)).getAttribute("class");
		} catch (WebDriverException e) {

			String details = e.getMessage();
			Reports.Warning(details, "");
		}
		return attribute;
	}

	public static String getCurrentURL() throws IOException {
		try {
			currURL = driver.getCurrentUrl();
		} catch (WebDriverException e) {
			ReportGenerate.writeResult("", "", "", "","Driver Not Found", "", "","Fail");
		}
		return currURL;
	}

	public static int uricheck(String url) throws IOException {
		try {
			URL url1 = new URL(url);
			HttpURLConnection http = (HttpURLConnection) url1.openConnection();
			http.connect();
			statusCode = http.getResponseCode();
			resmess = http.getResponseMessage();
			//System.out.println(statusCode);
			System.out.println(resmess);

		} catch (SSLHandshakeException e) {

			ReportGenerate.writeResult("", "", "", "","ValidatorException", "", "","Fail");

		}
		return statusCode;
	}

	public static String switchWindowClose() {
		try {
			String parentWindow1 = driver.getWindowHandle();
			Set<String> handle = driver.getWindowHandles();

			for (String WindowHandles1 : handle) {
				driver.switchTo().window(WindowHandles1);
				System.out.println(WindowHandles1);
			}
			uri = driver.getCurrentUrl();
			driver.close();
			driver.switchTo().window(parentWindow1);
		} catch (WebDriverException e) {
			ReportGenerate.writeResult("", "", "", "","Driver Not Found24", "", "","Fail");
		}
		return uri;
	}

	public static void switchWindow() {
		try {
			parentWindow = driver.getWindowHandle();
			Set<String> handles = driver.getWindowHandles();

			for (String WindowHandles : handles) {
				driver.switchTo().window(WindowHandles);
			}

		} catch (WebDriverException e) {

		}

	}

	public static Robot selectText(WebElement element) throws AWTException, InterruptedException {
		Robot s = null;
		try {
			Point pt = element.getLocation();
			int x = pt.getX();
			int y = pt.getY();
			System.out.println(x + "<----------->" + y);
			s = new Robot();
			s.keyPress(KeyEvent.VK_F11);
			s.mouseMove(x, y);
			Thread.sleep(5000);
			s.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(5000);
			s.mouseMove(x + 392, y);
			s.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		} catch (Exception e) {
			String details = e.getMessage();
			Reports.Warning(details, " ");
		}

		return s;
	}

	public static List<WebElement> getElements(By xpath) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 40);
			elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath));

		} catch (Exception e) {
			String details = e.getMessage();
			Reports.Warning(details, " ");
		}
		return elements;
	}

	public static boolean ifelementPresent(String xpath) {
		try {

			driver.findElement(By.xpath(xpath)).isDisplayed();
			returnval = true;

		} catch (NoSuchElementException e) {
			returnval = false;
		}
		return returnval;
	}

	static String simpleDate() throws ParseException {
		try {

			LocalDate myObj = LocalDate.now(); // Create a date object
			date = myObj.toString();


		} catch (Exception exe) {
			ReportGenerate.writeResult("", "", "", "","Date not created", "", "","Fail");
		}
		return date;
	}
	/*
	 * public static void screenshot(String description) throws IOException { String
	 * dateAndTime; try{ File sourceFile =
	 * ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); LocalDateTime
	 * time=LocalDateTime.now(); String timeToString=time.toString(); dateAndTime =
	 * timeToString.replaceAll("[-+.^:,]",""); FileUtils.copyFile(sourceFile, new
	 * File(scrnShotPath+"bg"+dateAndTime+".png"));
	 * Reports.screenshot(description,dateAndTime); } catch(SessionNotFoundException
	 * e) { File scrFile =
	 * ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); LocalDateTime
	 * time=LocalDateTime.now(); String timeToString=time.toString(); dateAndTime =
	 * timeToString.replaceAll("[-+.^:,]",""); FileUtils.copyFile(scrFile, new
	 * File(scrnShotPath+"bg"+dateAndTime+".png"));
	 * Reports.screenshot(description,dateAndTime); }
	 * 
	 * 
	 * }
	 */
	/*
	 * public static void highlightElement(WebElement element) throws
	 * InterruptedException { for (int i = 0; i <3; i++) { JavascriptExecutor js =
	 * (JavascriptExecutor) driver;
	 * js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
	 * element, "color: red; border: 3px solid red;"); Thread.sleep(2000);
	 * js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
	 * element, ""); } }
	 */

	public static WebElement expandRootElement(WebElement element) {
		WebElement ele = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot",
				element);
		return ele;
	}

	public static void ScrollDown() {
		try {
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("window.scrollBy(0,400)");
			
			Thread.sleep(2000);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}


	public static void ScrollUp() {
		try {
			executor.executeScript("window.scrollBy(0,-400)");
			Thread.sleep(2000);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}





}
