package com.oz.onestong.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
@SuppressWarnings("all")
public class ExcelUtil {
	 public ArrayList<ArrayList<String>> readExcel(String fileName,String path) {   
		 File  _f = new File("d:\\excel_root\\test2.xls");
		 if(!_f.renameTo(_f)){
			 return null
			 ;
		 }
		 
		 
		 ArrayList<ArrayList<String>> Row =new ArrayList<ArrayList<String>>();   
	           
	        try {   
	            Workbook workBook = null;   
	            try {   
	            workBook = new XSSFWorkbook(path+"\\"+fileName);   
	            } catch (Exception ex) {   
	            workBook = new HSSFWorkbook(new FileInputStream(path+"\\"+fileName));   
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
	                    ArrayList<String> arrCell =new ArrayList<String>();   
	                    for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {   
	                        Cell cell = row.getCell(cellNum);   
	                        if (cell == null) {   
	                            continue;   
	                        }   
	                        arrCell.add(getValue(cell));   
	                    }   
	                    Row.add(arrCell);   
	                }   
	            }   
	        } catch (IOException e) {   
	            System.out.println("e:"+e);   
	        }   
	       
	        return Row;   
	    }   
	  
	    private String getValue(Cell cell) {   
	        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {   
	            return String.valueOf(cell.getBooleanCellValue());   
	        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {   
	            return String.valueOf(cell.getNumericCellValue());   
	        } else if(cell.getCellType()==cell.CELL_TYPE_ERROR){   
	            return String.valueOf(cell.getErrorCellValue());   
	        } else if(cell.getCellType()==cell.CELL_TYPE_FORMULA){
	        	return String.valueOf(cell.getCellFormula());
	        }else {
	        	return String.valueOf(cell.getStringCellValue());
	        }
	    }   
	  
	    public static void main(String[] args) throws UnsupportedEncodingException {   
	        ExcelUtil s= new ExcelUtil();   
	        //ArrayList<ArrayList<String>> row=s.readExcel("TEST.xlsx","D:\\Program Files\\Java");   
	        ArrayList<ArrayList<String>> row=s.readExcel("test2.xls","D:\\excel_root");   
	       // System.out.println("size:"+row.size());   
	      if(null!=row){
	    	  for (ArrayList<String> cell : row) {   
		            for (String str : cell) {   
		                if(null!=str&&!str.equals("")){
		                	System.out.println(new String(str.trim().replaceAll("\\s+","")));
		                }
		            	
		            	//System.out.println(str);   
		                }   
		            }   
	      }else{
	    	  System.out.println("log :文件正在被修改");
	      }
	    }   

}
