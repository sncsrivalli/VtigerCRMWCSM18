package testNGImplementation;

import org.testng.annotations.Test;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;
import genericLibraries.TabNames;

public class CreateOrganizationTest extends BaseClass{

	@Test
	public void createOrganizationTest() {
		
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
			
	}

}
