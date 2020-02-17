package com.filta.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelData
{
	private Sheet sheet1;
	private Workbook wb;
	private File fileObj;
	private FileInputStream input;
	private int totlaRows;
	private int totalCol;

	public ReadExcelData(String path, String sheetName)
	{
		fileObj = new File(path);
		try
		{
			input = new FileInputStream(fileObj);
			wb = new XSSFWorkbook(input);
			sheet1 = wb.getSheet(sheetName);
			wb.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public ReadExcelData(String path, int index)
	{
		fileObj = new File(path);
		try
		{
			input = new FileInputStream(fileObj);
			wb = new XSSFWorkbook(input);
			sheet1 = wb.getSheetAt(index);
			wb.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public int getTotalRows()
	{
		// For Only One Row Use: int totlaRows = sheet1.getLastRowNum() + 1;
		totlaRows = sheet1.getLastRowNum();
		return totlaRows;
	}


	public int getTotalColumns()
	{
		totalCol = sheet1.getRow(0).getLastCellNum();
		return totalCol;
	}


	public String getCellData(int rowNum, int colNum)
	{
		return sheet1.getRow(rowNum).getCell(colNum).getStringCellValue();
	}


	public double getCellDataInt(int rowNum, int colNum)
	{
		return sheet1.getRow(rowNum).getCell(colNum).getNumericCellValue();
	}
}
