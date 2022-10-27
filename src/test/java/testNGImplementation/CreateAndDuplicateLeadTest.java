package testNGImplementation;

import org.testng.annotations.Test;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;
import genericLibraries.TabNames;

public class CreateAndDuplicateLeadTest extends BaseClass {

	@Test
	public void createAndDuplicateLeadTest() {
		
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
	}
}
