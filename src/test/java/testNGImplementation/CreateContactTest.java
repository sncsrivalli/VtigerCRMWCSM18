package testNGImplementation;

import org.testng.annotations.Test;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;
import genericLibraries.TabNames;

public class CreateContactTest extends BaseClass {
	@Test
	public void createContactTest() {
		
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
	}

}
