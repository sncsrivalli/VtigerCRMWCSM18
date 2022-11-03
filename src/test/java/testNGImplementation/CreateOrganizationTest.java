package testNGImplementation;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;
import genericLibraries.TabNames;


public class CreateOrganizationTest extends BaseClass{

	@Test
	public void createOrganizationTest() {
		SoftAssert s = new SoftAssert();
		
		home.clickRequiredTab(webdriver, TabNames.ORGANIZATIONS);
		s.assertTrue(driver.getTitle().contains("Organizations"), "Fail : Organizations page not displayed");

		organizationPage.clickPlusButton();
		s.assertTrue(createOrganization.getPageHeader().contains("Creating New Organization"), "Fail : Creating new organization page is not displayed");

		String newOrganizationName = createOrganization.createOrganization(webdriver, excel, javaUtility);
		s.assertTrue(organizationInfo.getPageHeader().contains(newOrganizationName), "Fail : Organization is not created");

		organizationInfo.clickOrganization();
		s.assertTrue(organizationPage.getPageHeader().contains("Organizations"), "Fail : Organizations page is not displayed");

		if (organizationPage.getNewOrganization().equalsIgnoreCase(newOrganizationName)) 
			excel.writeDataIntoExcel("TestData", "Pass", IConstantPath.EXCEL_FILE_PATH, "Create Organization");
		else 			
			excel.writeDataIntoExcel("TestData", "Fail", IConstantPath.EXCEL_FILE_PATH, "Create Organization");
		
		s.assertTrue(organizationPage.getNewOrganization().equalsIgnoreCase(newOrganizationName), "Test Case Failed");
		s.assertFalse(true);
		s.assertAll();	
	}

}
