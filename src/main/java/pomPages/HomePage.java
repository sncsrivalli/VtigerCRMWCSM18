package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.WebDriverUtility;

/**
 * This class consists of all the web elements and business libraries of Home page in vtiger application
 * @author sncsr
 *
 */

public class HomePage {
	
	//Declaration
	//private String dynamicPath = "//a[.='%s']";
	
	@FindBy(xpath="//img[@src='themes/softed/images/user.PNG']")
	private WebElement administratorImage;
	
	//Initialization
	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	//Business Libraries
	
	/**
	 * This method is used to click on the required tab in home page of vtiger application
	 * @param webdriver
	 * @param tabName
	 */
//	public void clickRequiredTab(WebDriverUtility webdriver, TabNames tabName) {
//		webdriver.convertStringToDynamicXpath(dynamicPath, tabName.getTabName()).click();
//	}
	
	
	public void mouseHoverToAdministratorImage(WebDriverUtility webdriver) {
		webdriver.mouseHoverToElement(administratorImage);
	}
}
