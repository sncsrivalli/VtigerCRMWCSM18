package pomImplementation;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import genericLibraries.ExcelUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.TabNames;
import genericLibraries.WebDriverUtility;
import pomPages.ContactsPage;
import pomPages.CreateNewContactPage;
import pomPages.HomePage;
import pomPages.LoginPage;
import pomPages.NewContactInfoPage;

public class CreateContactTest {

	public static void main(String[] args) throws IOException {
		
		WebDriverUtility webdriver = new WebDriverUtility();
		JavaUtility javaUtility = new JavaUtility();
		
		PropertyFileUtility property = new PropertyFileUtility();
		property.propertyFileInitialization(IConstantPath.PROPERTY_FILE_PATH);
		
		ExcelUtility excel = new ExcelUtility();
		excel.excelFileInitialization(IConstantPath.EXCEL_FILE_PATH);
		
		String browser = property.getDataFromPropertyFile("browser");
		String url = property.getDataFromPropertyFile("url");
		String username = property.getDataFromPropertyFile("username");
		String password = property.getDataFromPropertyFile("password");
		long time = Long.parseLong(property.getDataFromPropertyFile("timeouts")); 
		
		WebDriver driver = webdriver.openBrowserAndApplication(browser, url, time);
		
		LoginPage login = new LoginPage(driver);
		HomePage home = new HomePage(driver);
		ContactsPage contactsPage = new ContactsPage(driver);
		CreateNewContactPage createContact = new CreateNewContactPage(driver);
		NewContactInfoPage newContactInfo = new NewContactInfoPage(driver);
		
		if (login.getLogo().isDisplayed())
			System.out.println("Pass: Vtiger login page is diplayed");
		else
			System.out.println("Fail: Vtiger login page is not displayed");
		
		login.loginToApplication(username, password);

		if (driver.getTitle().contains("Administrator"))
			System.out.println("Pass : Login successful");
		else
			System.out.println("Fail : Login not successful");

		home.clickRequiredTab(webdriver, TabNames.CONTACTS);
		if (driver.getTitle().contains("Contacts"))
			System.out.println("Pass : Contacts page displayed");
		else
			System.out.println("Fail : Contacts page not displayed");

		contactsPage.clickPlusButton();

		if (createContact.getPageHeader().contains("Creating New Contact"))
			System.out.println("Pass : Creating new Contact page is displayed");
		else
			System.out.println("Fail : Creating new Contact page is not displayed");

		String contactName = createContact.createContactWithExistingOrganization(driver, webdriver, javaUtility, excel);
		
		if (newContactInfo.getPageHeader().contains(contactName))
			System.out.println("Pass : New contact created successfully");
		else
			System.out.println("Fail : Contact is not created");

		newContactInfo.clickContactsLink();
		
		if (contactsPage.getPageHeader().contains("Contacts"))
			System.out.println("Pass : Contacts page displayed");
		else
			System.out.println("Fail : Contacts page is not displayed");

		if (contactsPage.getLastContactName().equalsIgnoreCase(contactName)) {
			System.out.println("Test Case Passed");
			excel.writeDataIntoExcel("TestData", "Pass", IConstantPath.EXCEL_FILE_PATH, "Create Contact");
		}
			
		else {
			System.out.println("Test Case Failed");
			excel.writeDataIntoExcel("TestData", "Fail", IConstantPath.EXCEL_FILE_PATH, "Create Contact");
		}
			

		home.mouseHoverToAdministratorImage(webdriver);
		home.clickRequiredTab(webdriver, TabNames.SIGNOUT);
		excel.closeExcel();
		webdriver.closeBrowser();

	}

}
