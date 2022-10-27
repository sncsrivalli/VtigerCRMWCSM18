package genericLibraries;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import pomPages.CreateNewOrganizationPage;
import pomPages.HomePage;
import pomPages.LoginPage;
import pomPages.NewOrganizationInfoPage;
import pomPages.OrganizationsPage;

public class BaseClass {
	
	protected WebDriverUtility webdriver;
	protected JavaUtility javaUtility;
	protected PropertyFileUtility property;
	protected ExcelUtility excel;
	protected WebDriver driver;
	protected LoginPage login ;
	protected HomePage home;
	protected OrganizationsPage organizationPage;
	protected CreateNewOrganizationPage createOrganization;
	protected NewOrganizationInfoPage organizationInfo;
	
	//@BeforeSuite
	
	@BeforeTest
	public void testSetup() {
		webdriver = new WebDriverUtility();
		javaUtility = new JavaUtility();
		property = new PropertyFileUtility();
		excel = new ExcelUtility();
	}
	
	@BeforeClass
	public void classSetup() {
		property.propertyFileInitialization(IConstantPath.PROPERTY_FILE_PATH);
		excel.excelFileInitialization(IConstantPath.EXCEL_FILE_PATH);
		String browser = property.getDataFromPropertyFile("browser");
		String url = property.getDataFromPropertyFile("url");
		long time = Long.parseLong(property.getDataFromPropertyFile("timeouts")); 
		
		driver = webdriver.openBrowserAndApplication(browser, url, time);
		
		login = new LoginPage(driver);
		if (login.getLogo().isDisplayed())
			System.out.println("Pass: Vtiger login page is diplayed");
		else
			System.out.println("Fail: Vtiger login page is not displayed");
	}
	
	@BeforeMethod
	public void methodSetup() {
		home = new HomePage(driver);
		organizationPage = new OrganizationsPage(driver);
		createOrganization = new CreateNewOrganizationPage(driver);
		organizationInfo = new NewOrganizationInfoPage(driver);

		String username = property.getDataFromPropertyFile("username");
		String password = property.getDataFromPropertyFile("password");
		login.loginToApplication(username, password);
		if (driver.getTitle().contains("Administrator"))
			System.out.println("Pass : Login successful");
		else
			System.out.println("Fail : Login not successful");
	}
	
	@AfterMethod
	public void methodTearDown() {
		home.mouseHoverToAdministratorImage(webdriver);
		home.clickRequiredTab(webdriver, TabNames.SIGNOUT);
	}
	
	@AfterClass
	public void classTearDown() {
		webdriver.closeBrowser();
	}
	
	@AfterTest
	public void testTearDown() {
		excel.closeExcel();
	}
	
	//@AfterSuite

}
