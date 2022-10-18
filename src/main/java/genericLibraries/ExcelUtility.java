package genericLibraries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * This class contains the methods to perform actions on excel workbook
 * @author sncsr
 *
 */

public class ExcelUtility {
	private Workbook workbook;
	/**
	 * This method initializes the excel workbook
	 * @throws IOException 
	 * @throws EncryptedDocumentException 
	 */
	
	public void excelFileInitialization(String excelPath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(excelPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			workbook = WorkbookFactory.create(fis);
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to fetch single data from excel
	 */
	public String fetchSingleDataFromExcel(String sheetName, int rowNumber, int cellNumber) {
		Sheet sheet = workbook.getSheet(sheetName);
		DataFormatter df = new DataFormatter();
		return df.formatCellValue(sheet.getRow(rowNumber).getCell(cellNumber));
	}
	
	/**
	 * This method is used to fetch multiple data from excel
	 * @param sheetName 
	 * @return 
	 */
	public List<String> fetchMultipleDataFromExcel(String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);
		DataFormatter df = new DataFormatter();
		List<String> data = new ArrayList<>();
		for(int i=0;i< sheet.getLastRowNum();i++) {
			for(int j=0;j<sheet.getRow(i).getLastCellNum();j++) {
				data.add(df.formatCellValue(sheet.getRow(i).getCell(j)));
			}
		}
		return data;
	}
	
	/**
	 * This method is used to write data into excel
	 * @param sheetName 
	 * @param excelPath 
	 */
	
	public void writeDataIntoExcel(String sheetName, int rowNum,int cellNum, String data, String excelPath) {
		Sheet sheet = workbook.getSheet(sheetName);
		Cell cell = sheet.getRow(rowNum).createCell(cellNum);
		cell.setCellValue(data);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(excelPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			workbook.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to close excel workbook
	 */
	public void closeExcel() {
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
