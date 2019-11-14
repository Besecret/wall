/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wanding.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author SONY
 */
public class ImportExcel {

	private static Workbook wb = null;

	private static Sheet sheet = null;

	private static Cell cell = null;

	private static Row row = null;

	public static String[][] readExcel(InputStream stream, int version) throws IOException {
		wb = (Workbook) new HSSFWorkbook(stream);
		sheet = wb.getSheetAt(0);

		// 行数(从0开始,相当于最后一行的索引),列数
		int count_row = sheet.getLastRowNum(), count_cell = sheet.getRow(0).getPhysicalNumberOfCells();

		String[][] str = new String[count_row][count_cell];

		for (int i = 0; i < count_row; i++) {

			for (int j = 0; j < count_cell; j++) {

				row = sheet.getRow(i + 1);

				cell = row.getCell(j);
				if (cell != null) {
					int type = cell.getCellType(); // 得到单元格数据类型
					String k = "";
					switch (type) { // 判断数据类型
					case Cell.CELL_TYPE_BLANK:
						k = "";
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						k = cell.getBooleanCellValue() + "";
						break;
					case Cell.CELL_TYPE_ERROR:
						k = cell.getErrorCellValue() + "";
						break;
					case Cell.CELL_TYPE_FORMULA:
						k = cell.getCellFormula();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							k = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-mm-dd");// 格式化日期
						} else {
							if (j <= 0 || j >= 8) {
								cell.setCellType(Cell.CELL_TYPE_NUMERIC);
								DecimalFormat df = new DecimalFormat("0");
								k = df.format(cell.getNumericCellValue());
							} else
								k = String.valueOf(cell.getNumericCellValue());
						}
						break;
					case Cell.CELL_TYPE_STRING:
						k = cell.getStringCellValue();
						break;
					default:
						break;
					}
					str[i][j] = k;
				}

			}

		}

		cell = null;

		row = null;

		sheet = null;

		wb = null;

		return str;

	}

	public static HashMap<String, Object> readExcel2(InputStream stream, int version) throws IOException {
		wb = (Workbook) new XSSFWorkbook(stream);
		sheet = wb.getSheetAt(0);

		// 行数(从0开始,相当于最后一行的索引),列数
		int count_row = sheet.getLastRowNum(), count_cell = sheet.getRow(0).getPhysicalNumberOfCells();

		String[][] str = new String[count_row][count_cell];

		for (int i = 0; i < count_row; i++) {

			for (int j = 0; j < count_cell; j++) {

				row = sheet.getRow(i + 1);

				cell = row.getCell(j);
				if (cell != null) {
					int type = cell.getCellType(); // 得到单元格数据类型
					String k = "";
					switch (type) { // 判断数据类型
					case Cell.CELL_TYPE_BLANK:
						k = "";
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						k = cell.getBooleanCellValue() + "";
						break;
					case Cell.CELL_TYPE_ERROR:
						k = cell.getErrorCellValue() + "";
						break;
					case Cell.CELL_TYPE_FORMULA:
						k = cell.getCellFormula();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							k = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-mm-dd");// 格式化日期
						} else {
							k = String.valueOf((long) cell.getNumericCellValue());
						}
						break;
					case Cell.CELL_TYPE_STRING:
						k = cell.getStringCellValue();
						break;
					default:
						break;
					}
					str[i][j] = k;
				}

			}

		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("strArrData", str);
		map.put("wb", wb);
		return map;
	}

	public static HashMap<String, Object> readExcelToList(InputStream stream, int version) throws IOException {

		wb = (Workbook) new HSSFWorkbook(stream);
		sheet = wb.getSheetAt(0);

		// 行数(从0开始,相当于最后一行的索引),列数
		int count_row = sheet.getLastRowNum(), count_cell = sheet.getRow(0).getPhysicalNumberOfCells();
		List<Map<Integer, String>> datalist = new ArrayList<Map<Integer, String>>();
		for (int i = 0; i < count_row; i++) {
			Map<Integer, String> data = new HashMap<Integer, String>();
			for (int j = 0; j < count_cell; j++) {
				row = sheet.getRow(i + 1);
				cell = row.getCell(j);
				if (cell != null) {
					int type = cell.getCellType(); // 得到单元格数据类型
					String k = "";
					switch (type) { // 判断数据类型
					case Cell.CELL_TYPE_BLANK:
						k = "";
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						k = cell.getBooleanCellValue() + "";
						break;
					case Cell.CELL_TYPE_ERROR:
						k = cell.getErrorCellValue() + "";
						break;
					case Cell.CELL_TYPE_FORMULA:
						k = cell.getCellFormula();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							k = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-mm-dd");// 格式化日期
						}
						break;
					case Cell.CELL_TYPE_STRING:
						k = cell.getStringCellValue();
						break;
					default:
						break;
					}
					data.put(j, k);
				}

			}
			datalist.add(data);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("datalist", datalist);
		map.put("wb", wb);
		return map;
	}

}
