package Base;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class baseClass {

	public static WebDriver driver = null;
	public String url;
	public int done = 3;
	
	
	//Function to return url
	public String CRMurl() {
		url = "https://www.freecrm.com/";
		return url;
	}

	//Function to select the browser
	public String browserIn() {
		return "CR";
		//		return "FF";
		//		return "IE";
		//		return "EE";
	}

	//Launch browser and maximize to 100%
	public WebDriver browserLaunch() {

		try {
			if(browserIn().equals("CR") || browserIn().equals("CHROME")) {
				//Launch Chrome browser
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/Drivers/chromedriver.exe");
				driver = new ChromeDriver();
			}
			else if(browserIn().equals("FF") || browserIn().equals("FIREFOX")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/src/test/resources/Drivers/geckodriver.exe");
				driver = new FirefoxDriver();			
			}
			else if(browserIn().equals("EE") || browserIn().equals("EDGE")) {
				System.setProperty("webdriver.edge.driver", System.getProperty("user.dir")+"/src/test/resources/Drivers/msedgedriver.exe");
				driver = new EdgeDriver();
			}
			else if(browserIn().equals("IE") || browserIn().equals("INTERNET EXPLORER")) {
				System.setProperty("webdriver.edge.driver", System.getProperty("user.dir")+"/src/test/resources/Drivers/msedgedriver.exe");
				driver = new EdgeDriver();
			}
			else {
				System.out.println("WE DO NOT SUPPORT THIS BROWSER: "+browserIn());
			}
		}
		catch(Exception e) {
			System.out.println("COULD NOT START WEBDRIVER: "+e);
		}
		driver.manage().window().maximize();
		return driver;

	}


	//Launch browser and enter url
	public void browserInvoke() {
		driver = browserLaunch();
		//driver.get(CRMurl());
	}
	
	public void openFreeCRMSite() {
		driver.get(CRMurl());
	}
	
	public void browserQuit() {
		driver.close();
		driver.quit();
	}
	
	public void Wait(int waitTime) throws InterruptedException {
		Thread.sleep(waitTime);
	}
	

//*********************************************************************************************
	 	 
	public final String getUrl() {
		String currentUrl = driver.getCurrentUrl();
		return currentUrl;
	}
	
	public final String getWindowTitle() {
		String currentWindowTitle = driver.getTitle();
		return currentWindowTitle;
	}
	
	 
	 public final String GetEleVal(String ele)
		{
			return ele.contains("#") ? ele.split("[#]", -1)[0].trim() : ele;
		}
		public final String GetEleLbl(String ele)
		{
			return ele.contains("#") ? ele.split("[#]", -1)[1].trim() : ele;
		}

		public final WebElement GetElement(String LocatorValue)
		{

			WebElement ele = null;
			By byLoc;
			byLoc = LocatorValue.substring(0, 2).equals("//") ? By.xpath(GetEleVal(LocatorValue)) : By.id(GetEleVal(LocatorValue));

			try
			{
				ele = driver.findElement(byLoc);
			}
			catch (RuntimeException e)
			{
				System.out.println(GetEleLbl(LocatorValue) + " not found :" + e);
				Assert.assertFalse(false);
			}
			return ele;
		}

		public final void ClickEl(WebElement ele, String lbl)
		{
			try
			{
				ele.click();
				System.out.println("Clicked element: " + lbl);
			}
			catch (RuntimeException e)
			{
				try
				{
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", ele);
					System.out.println("Could click element'" + lbl + " 'with javascript");
				}
				catch (RuntimeException exc)
				{
					System.out.println("Could not click element: " + lbl + " :: " + exc + " : " + e);
					Assert.fail();

				}
			}
		}

		//Clicking on element
		public final void ClickEle(String eleXP)
		{
			ClickEl(GetElement(eleXP), GetEleLbl(eleXP));
		}
		
//**********************************************************************************************
		
		 public final void JavaScriptClick(WebElement element)
		 {
			 JavascriptExecutor executor = (JavascriptExecutor) driver;
					try
					{
						executor.executeScript("arguments[0].click();", element);
						System.out.println("PASS>> Clicked on element: " + getAbsoluteXPath(element) + " using JS");
					}
					catch (RuntimeException e)
					{
						e.printStackTrace();;
						System.out.println("FAIL>> Couldn't click element: using JS");
					}
		 }

		
//***********************************************************************************************
	 
		private String getAbsoluteXPath(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		public final boolean IsElepresent(String LocatorValue)
		{
					boolean flag = false;
					By byLoc;
					byLoc = LocatorValue.substring(0, 2).equals("//") ? By.xpath(GetEleVal(LocatorValue)) : By.id(GetEleVal(LocatorValue));

					try
					{
						driver.findElement(byLoc);
						flag = true;
					}
					catch (RuntimeException e)
					{
						System.out.println(GetEleLbl(LocatorValue) + " not found :" + e);
					}
					return flag;
		}

//**********************************************************************************************
		
		 public final void ClearTexts(WebElement el, String lbl)
		 {
					try
					{
						el.clear();
						System.out.println("Cleared from element: " + lbl);
					}
					catch (RuntimeException e)
					{
						try
						{
							JavascriptExecutor js = (JavascriptExecutor)driver;
							js.executeScript("arguments[0].value = '';", el);
							System.out.println("Cleared from element: " + lbl + " using js:" + e);
						}
						catch (RuntimeException ex)
						{
							System.out.println("Could not clear text from element: " + lbl + " :: " + ex);
							Assert.assertFalse(false);
						}
					}
									
		 }

		 public void ClearText(String eleXP)
	        {
	            ClearTexts(GetElement(eleXP), GetEleLbl(eleXP));
	        }
	 
//**********************************************************************************************
	 
	 
		 public final void TypeText(String eleXP, String text)
		 {
		 			try
		 			{
		 				GetElement(eleXP).sendKeys(text);
		 				System.out.println("Typed '" + text + "' in element: " + GetEleLbl(eleXP));
		 			}
		 			catch (RuntimeException e)
		 			{
		 				try
		 				{
		 					String js = "arguments[0].setAttribute('value','" + text + "')";
		 					((JavascriptExecutor)driver).executeScript(js, GetElement(eleXP));
		 					System.out.println("Could type '" + text + "' in element: " + GetEleLbl(eleXP) + " :using javascript: " + e);
		 				}
		 				catch (RuntimeException ex)
		 				{
		 					System.out.println("Could not type '" + text + "' in element: " + GetEleLbl(eleXP) + " :: " + ex);
		 					Assert.assertFalse(false);
		 				}
		 			}
		 }

//*********************************************************************************************
		 
		//Getting title of the page
			public static String GetTitle()
			{
				//Driver refers to Title - Selenium Code
				String title = driver.getTitle();
				return title;
			}
	 
//**********************************************************************************************
			
			
			public final String GetTexts(WebElement el, String lbl)
			{
						String text = "";
						try
						{
							((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", el);
							text = el.getText();
							if (text.equals(""))
							{
								text = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerText;", el);
								System.out.println("Got text:<" + text + "> using JS from element: " + lbl);
							}
							else
							{
								System.out.println("Got text:<" + text + "> from element: " + lbl);
							}
						}
						catch (RuntimeException e)
						{
							System.out.println("Could not get text from element: " + lbl + " :: " + e);
							Assert.assertFalse(false);
						}
						return text;
			}
			
			  //Getting text from element
	        public String GetText(String eleXP)
	        {
	            return GetTexts(GetElement(eleXP), GetEleLbl(eleXP));
	        }
			
//**********************************************************************************************
			
	        public final void ImpWait(int sec)
	        {
	       			// driver.Manage().Timeouts().ImpWait = TimeSpan.FromSeconds(2);
	       			driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
	        }
	        
	        
	        public String waitForElementById(String item) {
	        	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
	            wait.until(ExpectedConditions.elementToBeClickable(By.id(item)));
	            return item;
	        }
	        
	        public String waitForElementByXpath(String item) {
	        	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
	            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(item)));
	            return item;	            
	        }

//********************************************************************************************

	        public final void Match(String act, String exp, String msg)
	        {
	        			//Trim extra spaces
	        			act = act.replaceAll("\\s+", " ");
	        			exp = exp.replaceAll("\\s+", " ");
	        			//act = Regex.Replace(act, "\\s+", " ");
	        			//exp = Regex.Replace(exp, "\\s+", " ");

	        			if (act.trim().equals(exp.trim()))
	        			{
	        				msg = msg.replace("<contains>", "");
	        				System.out.println("PASS >> " + msg + " >> Actual: " + act + " :: Expected: " + exp);
	        			}
	        			else if (act.toUpperCase().trim().equals(exp.toUpperCase().trim()))
	        			{
	        				msg = msg.replace("<contains>", "");
	        				System.out.println("WARNING matched ignoring  case >> " + msg + " >> Actual: " + act + " :: Expected: " + exp);
	        			}
	        			else if (msg.contains("<contains>"))
	        			{
	        				msg = msg.replace("<contains>", "");
	        				if (act.contains(exp.trim()))
	        				{
	        					System.out.println("PASS >> Matched as Actual contains Expetced >> " + msg + " >> Actual: " + act + " :: Expected: " + exp);
	        				}
	        				else if (exp.contains(act.trim()))
	        				{
	        					System.out.println("PASS >> Matched as Actual contains Expetced >> " + msg + " >> Actual: " + act + " :: Expected: " + exp);
	        				}
	        				else
	        				{
	        					System.out.println("FAIL >>" + msg + " >> Actual: " + act + " :: Expected: " + exp);
	        					//Take screen shot
	        					Assert.assertFalse(false);
	        				}
	        			}
	        			else
	        			{
	        				System.out.println("FAIL >>" + msg + " >> Actual: " + act + " :: Expected: " + exp);
	        				//Take screen shot
	        				Assert.assertFalse(false);
	        			}
	        }
	        
	        
	        
	        public void Match(Boolean act, Boolean exp, String msg)
	        {
	            if (act == exp)
	            {
	                System.out.println("PASS >> " + msg + " >> Actual: " + act + " :: Expected: " + exp);
	            }
	            else
	            {
	            	System.out.println("FAIL >>" + msg + " >> Actual: " + act + " :: Expected: " + exp);
	                //Take screen shot
	            	Assert.assertFalse(false);
	            }
	        }
	        
	        
	        
	        public void Match(int act, int exp, String msg)
	        {
	            if (act == exp)
	            {
	            	System.out.println("PASS >> " + msg + " >> Actual: " + act + " :: Expected: " + exp);
	            }
	            else
	            {
	            	System.out.println("FAIL >>" + msg + " >> Actual: " + act + " :: Expected: " + exp);
	                //Take screen shot
	            	Assert.assertFalse(false);
	            }
	        }
	        
	        
	        
	        public final String getCurrentWindow()
	        {
	        			String mainWindow = null;
	        			try
	        			{
	        				mainWindow = driver.getTitle();
	        			}
	        			catch (RuntimeException e)
	        			{
	        				System.out.println("Could not get current window handle Title:" + e);
	        			}
	        			return mainWindow;
	        }
	        
	        
	        
	        public final void switchToWindow(String titl)
	        {
	        			WebDriver popup = null;
	        			//driver.SwitchTo().DefaultContent();
	        			boolean foundPopupTitle = false;
	        			for (String handle : driver.getWindowHandles())
	        			{
	        				popup = driver.switchTo().window(handle);
	        				if (popup.getTitle().contains(titl))
	        				{
	        					foundPopupTitle = true;
	        					System.out.println("PASS>> Switched to window: " + titl);
	        					break;
	        				}
	        			}
	        			if (!foundPopupTitle)
	        			{
	        				System.out.println("FAIL>> Could not find window: " + titl);
	        				Assert.assertFalse(false);
	        			}
	        }



	        public final boolean hasWindow(String titl) throws InterruptedException
	        {
	       			WebDriver popup = null;
	       			boolean foundPopupTitle = false;
	       			for (String handle : driver.getWindowHandles())
	       			{
	       				popup = driver.switchTo().window(handle);
	       				if (popup.getTitle().contains(titl))
	       				{
	       					foundPopupTitle = true;
	       					System.out.println("Switched to window: " + titl);
	       					break;
	       				}
	       			}
	       			if (!foundPopupTitle && done != 0)
	       			{
	       				Wait(2000);
	       				hasWindow(titl);
	       				done--;
	       			}
	       			return foundPopupTitle;
	        }

			
	        
	        public void closeWindow(String titl)
	        {
	            WebDriver popup = null;
	            for (String handle : driver.getWindowHandles())
	            {
	                popup = driver.switchTo().window(handle);
	                if (popup.getTitle().contains(titl))
	                {
	                    try
	                    {
	                        popup.close();
	                        System.out.println("Closed window: " + titl);
	                    }
	                    catch (Exception e)
	                    {
	                    	System.out.println("Could not close window: " + titl + " due to : " + e);
	                    }
	                    break;
	                }
	            }
	        }
			
			
			
	        //public void lastWin() { driver.switchTo().Window(driver.getWindowHandles().Last()); System.out.println("Switched to window: " + driver.getTitle()); }
	        //public void firstWin() { driver.switchTo().Window(driver.getWindowHandles().First()); System.out.println("Switched to window: " + driver.getTitle()); }
			
	        public final void ScrollToTop()
	        {
	       			((JavascriptExecutor)driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	        }

			
	        public void ScrollToBottom() throws InterruptedException
	        {
	            long scrollHeight = 0;
	            do
	            {
	                JavascriptExecutor js = (JavascriptExecutor)driver;
	                long newScrollHeight = (long)js.executeScript("window.scrollTo(0, document.body.scrollHeight); return document.body.scrollHeight;");
	                if (newScrollHeight == scrollHeight) { break; }
	                else { scrollHeight = newScrollHeight; Thread.sleep(400); }
	            } while (true);
	        }
	        
	        
	        
	        public String getAttr(WebElement el, String lbl, String attr)
	        {
	            String attrVal = "";
	            try
	            {
	                attrVal = el.getAttribute(attr);
	                System.out.println("Got value:" + attrVal + " from element: " + lbl + " for attribute: " + attr);
	            }
	            catch(Exception e) { }
	            //if (attrVal.Equals(""))
	            //{
	            //    try
	            //    {
	            //        ((IJavaScriptExecutor)driver).ExecuteScript("return document."+el+".getAttribute('" + attr + "');").ToString();
	            //    }
	            //    catch (Exception e)
	            //    {
	            //        Console.WriteLine("Could not get attribute value for " + attr + " from element: " + lbl + " :: " + e);
	            //        Assert.Fail();
	            //    }
	            //}
	            return attrVal;
	        }
	        
	        
	        public String getAttribute(String eleXP, String attr)
	        {
	            return getAttr(GetElement(eleXP), GetEleLbl(eleXP), attr);
	        }
	        
	        
	        
	       		public final void setAttr(WebElement ele, String attrNm, String attrVal, String eleLbl)
	       		{
	       			try
	       			{
	       				((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", ele, attrNm, attrVal);
	       				System.out.println("Attribute: " + attrNm + " has been set with value: " + attrVal + " in element: " + eleLbl);
	       			}
	       			catch (RuntimeException e)
	       			{
	       				System.out.println("Coukdn't set attribute: " + attrNm + " with value: " + attrVal + " for element: " + eleLbl);
	       			}
	       		}
	       			       		
		        
		        public final void setAttribute(String eleXP, String attName, String attValue)
		        {
		       			setAttr(GetElement(eleXP), attName, attValue, GetEleLbl(eleXP));
		        }
		        
		        

		      		public final boolean isAttributePresent(WebElement element, String attribute)
		      		{
		      			boolean result = false;
		      			try
		      			{
		      				String value = element.getAttribute(attribute);
		      				if (value != null)
		      				{
		      					result = true;
		      				}
		      			}
		      			catch (RuntimeException e)
		      			{
		      			}
		      			return result;
		      		}
		      		
		      		
			        public final boolean isAttrPresent(String el, String attr)
			        {
			      			return isAttributePresent(GetElement(el), attr);
			        }
			        
			        
			        
			      //Switch to iFrame
					public final void SwitchtoiFrame(String frameName)
					{
						try
						{
							driver.switchTo().frame(frameName);
							System.out.println("switched to frame: " + frameName);
						}
						catch (RuntimeException e)
						{
							System.out.println("Cannot switch to frame: " + frameName + " : Reason:- " + e);
							Assert.assertFalse(false);
						}
					}
					
					
					
					//double click

					public final void DoubleClick(WebElement ElementName)
					{
						Actions actions = new Actions(driver);
						actions.doubleClick(ElementName).build().perform();
					}
					
					// Right Click
					public final void RightClick(WebElement ElementName)
					{
						Actions actions = new Actions(driver);
						actions.contextClick(ElementName).build().perform();
						//actions.ContextClick(ElementName).SendKeys(Keys.ArrowDown).SendKeys(Keys.ArrowDown).SendKeys(Keys.ArrowDown).SendKeys("Make Read Only").Click().Perform();
					}
					
					
					
					 // Right Click using java script
					public final void RightClickjavaScript(WebElement ElementName)
					{
						JavascriptExecutor js = (JavascriptExecutor)driver;
						String javaScript = "var evt = document.createEvent('MouseEvents');" + "var RIGHT_CLICK_BUTTON_CODE = 2;" + "evt.initMouseEvent('contextmenu', true, true, window, 1, 0, 0, 0, 0, false, false, false, false, RIGHT_CLICK_BUTTON_CODE, null);" + "arguments[0].dispatchEvent(evt)";

						js.executeScript(javaScript, ElementName);
						System.out.println("Right clicked using javascript: " + ElementName.toString());
					}
										

}
