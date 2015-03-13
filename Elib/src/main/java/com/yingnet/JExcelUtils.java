package com.yingnet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class JExcelUtils {

	public static List<String[]> readLines(File f) {

		List<String[]> resultList = new ArrayList<String[]>();

		try {
			Workbook workbook = Workbook.getWorkbook(f);
			Sheet[] sheet = workbook.getSheets();
			// 取第一个sheet页
			if (sheet.length < 1) {
				return null;
			}

			for (int i = 1; i < sheet[0].getRows(); i++) {
				String name = sheet[0].getCell(0, i).getContents().trim();
				String tel = sheet[0].getCell(1, i).getContents().trim();

				if (tel.contains(" ") || tel.contains("\t")
						|| tel.contains("/")) {
					tel = "";
				}

				resultList.add(new String[] { name, tel });
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultList;
	}
}
