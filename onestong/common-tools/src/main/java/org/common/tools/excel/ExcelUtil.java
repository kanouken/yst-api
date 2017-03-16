package org.common.tools.excel;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.common.tools.string.StringUtil;

@SuppressWarnings("all")
public class ExcelUtil<T> {
	public ArrayList<ArrayList<String>> readExcel(String fileName, String path) {
		ArrayList<ArrayList<String>> Row = new ArrayList<ArrayList<String>>();

		try {
			Workbook workBook = null;
			try {
				workBook = new XSSFWorkbook(path + File.separator + fileName);
			} catch (Exception ex) {
				workBook = new HSSFWorkbook(new FileInputStream(path + File.separator + fileName));
			}


			for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
				Sheet sheet = workBook.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					Row row = sheet.getRow(rowNum);
					if (row == null) {
						continue;
					}

					// 循环列Cell
					ArrayList<String> arrCell = new ArrayList<String>();
					for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
						Cell cell = row.getCell(cellNum);
						if (cell == null) {
							arrCell.add(null);
							continue;
						}
						arrCell.add(getValue(cell));
					}
					Row.add(arrCell);
				}
			}
		} catch (IOException e) {
			System.out.println("e:" + e);
		}

		return Row;
	}


	public ArrayList<ArrayList<String>> readExcel(InputStream fis) {
		ArrayList<ArrayList<String>> Row = new ArrayList<ArrayList<String>>();

		try {
			Workbook workBook = null;
			try {
				workBook = new XSSFWorkbook(fis);
			} catch (Exception ex) {
				workBook = new HSSFWorkbook(fis);
			}


			for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
				Sheet sheet = workBook.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					Row row = sheet.getRow(rowNum);
					if (row == null) {
						continue;
					}

					// 循环列Cell
					ArrayList<String> arrCell = new ArrayList<String>();
					for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
						Cell cell = row.getCell(cellNum);
						if (cell == null) {
							arrCell.add(null);
							continue;
						}
						arrCell.add(getValue(cell));
					}
					Row.add(arrCell);
				}
			}
		} catch (IOException e) {
			System.out.println("e:" + e);
		}

		return Row;
	}


	public static String getValue(Cell cell) {

		DecimalFormat df = new DecimalFormat("0");
		if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {


			return df.format(cell.getNumericCellValue());
		} else if (cell.getCellType() == cell.CELL_TYPE_ERROR) {
			return String.valueOf(cell.getErrorCellValue());
		} else if (cell.getCellType() == cell.CELL_TYPE_FORMULA) {
			return String.valueOf(cell.getCellFormula());
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}

	/**
	 * 导出集合数据到Excel，单Sheet
	 * */
	public ByteArrayOutputStream exportToExcel(String sheetName, List<String[]> head, List<T> data)
			throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet st = wb.createSheet(sheetName);
		if (data.size() <= 0) {
			wb.write(output);
			return output;
		}

		// 标题
		XSSFRow header = st.createRow(0);
		for(int j=0;j<head.size();j++)
		{
			String name = head.get(j)[0];
			header.createCell(j).setCellValue(name);
		}

		// 填充数据
		int rowIndex = 1;
		for (T d : data) {
			XSSFRow row = st.createRow(rowIndex);
			for(int j=0;j<head.size();j++)
			{
				String key = head.get(j)[1];
				Object value = null;
				String className = d.getClass().getName();

				if ("java.util.HashMap".equals(className)){
					value = ((Map<Object, Object>)d).get(key);
				}

				if (!"java.util.HashMap".equals(className)){
					Method m = d.getClass().getMethod("get" + key);
					value = m.invoke(d);
				}

				row.createCell(j).setCellValue(StringUtil.isNullOrEmpty(value) ? "" : value.toString());
			}
			rowIndex++;
		}

		wb.write(output);
		return output;
	}
}
