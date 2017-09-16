/**
 * 
 */
package com.dell.coe.svntogit.utilities;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * @author Prashant_Gupta2
 *
 */
public interface IExcelUtil {

	public abstract int writeProjectDetails(XSSFSheet spreadsheet,
			int rowCount, Cell cell, XSSFRow row);

	public abstract int writeBranches(XSSFSheet spreadsheet, int rowCount,
			Cell cell, XSSFRow row);

	public abstract boolean writeToExcel() throws IOException;

	/**
	 * @param spreadsheet
	 * @param rowCount
	 * @param cell
	 * @param row
	 * @return
	 */
	public abstract int writeTags(XSSFSheet spreadsheet, int rowCount,
			Cell cell, XSSFRow row);

}