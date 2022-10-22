package pomPages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.ExcelUtility;
import genericLibraries.JavaUtility;
import genericLibraries.WebDriverUtility;

/**
 * This class contains the web elements and business libraries of Create New Organization Page
 * @author sncsr
 *
 */
public class CreateNewOrganizationPage {
	// Declaration
	
	@FindBy(xpath="//span[@class='lvtHeaderText']")
	private WebElement pageHeader;
	

	@FindBy(name = "accountname")
	private WebElement organizationNameTextField;
	@FindBy(name = "industry")
	private WebElement industryDropdown;
	@FindBy(xpath = "//input[@value='T']")
	private WebElement groupRadioButton;
	@FindBy(name = "assigned_group_id")
	private WebElement assignedToGroupDropdown;
	@FindBy(xpath = "//input[contains(@value,'Save')]")
	private WebElement saveButton;

	// Initialization
	public CreateNewOrganizationPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	// Business Libraries
	public String createOrganization(WebDriverUtility webdriver,ExcelUtility excel, JavaUtility javaUtility) {
		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData", "Create Organization");

		String newOrganizationName = map.get("Organization Name") + javaUtility.generateRandomNumber(100);
		organizationNameTextField.sendKeys(newOrganizationName);
		webdriver.dropDown(industryDropdown, map.get("Industry"));
		groupRadioButton.click();
		webdriver.dropDown(assignedToGroupDropdown, map.get("Group"));
		saveButton.click();
		return newOrganizationName;
		
	}
	
	public String getPageHeader() {
		return pageHeader.getText();
	}
}
