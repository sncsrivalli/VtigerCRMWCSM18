package pomImplementation;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import genericLibraries.ExcelUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.TabNames;
import genericLibraries.WebDriverUtility;
import pomPages.CreateNewLeadPage;
import pomPages.DuplicatingLeadPage;
import pomPages.HomePage;
import pomPages.LeadsPage;
import pomPages.LoginPage;
import pomPages.NewLeadInfoPage;

public class CreateAndDuplicateLeadTest {

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
		LeadsPage leadPage = new LeadsPage(driver);
		CreateNewLeadPage createLead = new CreateNewLeadPage(driver);
		NewLeadInfoPage newLead = new NewLeadInfoPage(driver);
		DuplicatingLeadPage duplicateLead = new DuplicatingLeadPage(driver);
		
		if (login.getLogo().isDisplayed())
			System.out.println("Pass: Vtiger login page is diplayed");
		else
			System.out.println("Fail: Vtiger login page is not displayed");

		login.loginToApplication(username, password);
		if (driver.getTitle().contains("Administrator"))
			System.out.println("Pass : Login successful");
		else
			System.out.println("Fail : Login not successful");
		home.clickRequiredTab(webdriver, TabNames.LEADS);
		leadPage.clickPlusButton();
		
		if (createLead.getPageHeader().contains("Creating New Lead"))
			System.out.println("Pass : Creating new lead page is displayed");
		else
			System.out.println("Fail : Creating new lead page is not displayed");
		
		String leadName = createLead.createLead(webdriver, excel, javaUtility);
		
		if (newLead.getPageHeader().contains(leadName))
			System.out.println("Pass : New lead created successfully");
		else
			System.out.println("Fail : Lead is not created");
		
		newLead.clickDuplicateButton();
		
		if(duplicateLead.getPageHeader().contains(leadName)) 
			System.out.println("Pass : Duplicating page displayed");
		else
			System.out.println("Fail : Duplicating page is not displayed");
		
		String newLastName = duplicateLead.duplicatingLead(excel, javaUtility);
		
		if (newLead.getPageHeader().contains(newLastName))
			System.out.println("Pass : New lead created successfully");
		else
			System.out.println("Fail : Lead is not created");
		
		newLead.clickLeads();
		
		if(leadPage.getLastLeadName().equals(newLastName)) {
			System.out.println("Test Case passed");
			excel.writeDataIntoExcel("TestData", "Pass", IConstantPath.EXCEL_FILE_PATH, "Create Lead");
		}
			
		else {
			System.out.println("Test Case Failed");
			excel.writeDataIntoExcel("TestData", "Fail", IConstantPath.EXCEL_FILE_PATH, "Create Lead");
		}
			
		home.mouseHoverToAdministratorImage(webdriver);
		home.clickRequiredTab(webdriver, TabNames.SIGNOUT);
		excel.closeExcel();
		webdriver.closeBrowser();
	}

}
