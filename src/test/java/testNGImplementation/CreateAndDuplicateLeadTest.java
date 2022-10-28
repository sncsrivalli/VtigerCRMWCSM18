package testNGImplementation;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;
import genericLibraries.TabNames;

public class CreateAndDuplicateLeadTest extends BaseClass {

	@Test
	public void createAndDuplicateLeadTest() {
		SoftAssert s = new SoftAssert();
		home.clickRequiredTab(webdriver, TabNames.LEADS);
		leadPage.clickPlusButton();
		s.assertTrue(createLead.getPageHeader().contains("Creating New Lead"),"Fail : Creating new lead page is not displayed");
		
		String leadName = createLead.createLead(webdriver, excel, javaUtility);
		s.assertTrue(newLead.getPageHeader().contains(leadName),"Fail : Lead is not created");
		
		newLead.clickDuplicateButton();
		s.assertTrue(duplicateLead.getPageHeader().contains(leadName),"Fail : Duplicating page is not displayed");
		
		String newLastName = duplicateLead.duplicatingLead(excel, javaUtility);
		s.assertTrue(newLead.getPageHeader().contains(newLastName),"Fail : Lead is not created");
		
		newLead.clickLeads();
		
		if(leadPage.getLastLeadName().equals(newLastName)) 			
			excel.writeDataIntoExcel("TestData", "Pass", IConstantPath.EXCEL_FILE_PATH, "Create Lead");
		else 	
			excel.writeDataIntoExcel("TestData", "Fail", IConstantPath.EXCEL_FILE_PATH, "Create Lead");
		s.assertTrue(leadPage.getLastLeadName().equals(newLastName), "Fail : Test Case failed");
		s.assertAll();
	}
}
