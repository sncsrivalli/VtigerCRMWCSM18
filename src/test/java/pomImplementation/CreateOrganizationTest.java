package pomImplementation;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import genericLibraries.ExcelUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.TabNames;
import genericLibraries.WebDriverUtility;
import pomPages.CreateNewOrganizationPage;
import pomPages.HomePage;
import pomPages.LoginPage;
import pomPages.NewOrganizationInfoPage;
import pomPages.OrganizationsPage;

public class CreateOrganizationTest {

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
		OrganizationsPage organizationPage = new OrganizationsPage(driver);
		CreateNewOrganizationPage createOrganization = new CreateNewOrganizationPage(driver);
		NewOrganizationInfoPage organizationInfo = new NewOrganizationInfoPage(driver);
		
		if (login.getLogo().isDisplayed())
			System.out.println("Pass: Vtiger login page is diplayed");
		else
			System.out.println("Fail: Vtiger login page is not displayed");

		login.loginToApplication(username, password);
		if (driver.getTitle().contains("Administrator"))
			System.out.println("Pass : Login successful");
		else
			System.out.println("Fail : Login not successful");

		home.clickRequiredTab(webdriver, TabNames.ORGANIZATIONS);

		if (driver.getTitle().contains("Organizations"))
			System.out.println("Pass : Organizations page displayed");
		else
			System.out.println("Fail : Organizations page not displayed");

		organizationPage.clickPlusButton();
		
		if (createOrganization.getPageHeader().contains("Creating New Organization"))
			System.out.println("Pass : Creating new organization page is displayed");
		else
			System.out.println("Fail : Creating new organization page is not displayed");
		
		String newOrganizationName = createOrganization.createOrganization(webdriver, excel, javaUtility);
		
		if (organizationInfo.getPageHeader().contains(newOrganizationName))
			System.out.println("Pass : New organization created successfully");
		else
			System.out.println("Fail : Organization is not created");

		organizationInfo.clickOrganization();
		
		if (organizationPage.getPageHeader().contains("Organizations"))
			System.out.println("Pass : Organizations page displayed");
		else
			System.out.println("Fail : Organizations page is not displayed");

				
		if (organizationPage.getNewOrganization().equalsIgnoreCase(newOrganizationName)) {
			System.out.println("Test Case Passed");
			excel.writeDataIntoExcel("TestData", "Pass", IConstantPath.EXCEL_FILE_PATH, "Create Organization");
		}
			
		else {
			System.out.println("Test Case Failed");
			excel.writeDataIntoExcel("TestData", "Fail", IConstantPath.EXCEL_FILE_PATH, "Create Organization");
		}
			
		
		home.mouseHoverToAdministratorImage(webdriver);
		home.clickRequiredTab(webdriver, TabNames.SIGNOUT);
				
		webdriver.closeBrowser();
		excel.closeExcel();
	}

}
