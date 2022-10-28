package testNGImplementation;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;
import genericLibraries.TabNames;

public class CreateContactTest extends BaseClass {
	@Test
	public void createContactTest() {
		SoftAssert s = new SoftAssert();
		home.clickRequiredTab(webdriver, TabNames.CONTACTS);
		s.assertTrue(driver.getTitle().contains("Contacts"),"Fail : Contacts page not displayed");
			
		contactsPage.clickPlusButton();

		s.assertTrue(createContact.getPageHeader().contains("Creating New Contact"),"Fail : Creating new Contact page is not displayed");
			
		String contactName = createContact.createContactWithExistingOrganization(driver, webdriver, javaUtility, excel);
		
		s.assertTrue(newContactInfo.getPageHeader().contains(contactName),"Fail : Contact is not created");
			
		newContactInfo.clickContactsLink();
		
		s.assertTrue(contactsPage.getPageHeader().contains("Contacts"),"Fail : Contacts page is not displayed");
			
		if (contactsPage.getLastContactName().equalsIgnoreCase(contactName)) 
			excel.writeDataIntoExcel("TestData", "Pass", IConstantPath.EXCEL_FILE_PATH, "Create Contact");
		else 
			excel.writeDataIntoExcel("TestData", "Fail", IConstantPath.EXCEL_FILE_PATH, "Create Contact");
		s.assertTrue(contactsPage.getLastContactName().equalsIgnoreCase(contactName),"Fail : Test Case Failed");
		s.assertAll();
	}

}
