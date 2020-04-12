package com.example.tensquare_user;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.annotation.processing.FilerException;
import java.io.*;

public class PoiRead {
	public static void main(String[] args) {
		//需要解析的Excel文件路径
		String Readpath="C:\\Users\\Administrator\\Desktop\\temp2\\yunfu.xlsx";
		PoiRead poi=new PoiRead();
		//poi.readExcel(Readpath);

		String writePath="C:\\Users\\Administrator\\Desktop\\temp2";
		poi.writeExcel(writePath);
	}
	public void readExcel(String filePath){
		File file=new  File(filePath);
		try {
			//获取工作簿
			FileInputStream fs= new FileInputStream(file);
			XSSFWorkbook workbook=new XSSFWorkbook(fs);
			//获取第一个工作表
			XSSFSheet hs=workbook.getSheetAt(0);
			//获取Sheet的第一个行号和最后一个行号
			int last=hs.getLastRowNum();
			int first=hs.getFirstRowNum();
			System.out.println(first+"----"+last);
			//遍历获取单元格里的信息
			for (int i = first; i <last; i++) {

				XSSFRow row=hs.getRow(i);
				int firstCellNum=row.getFirstCellNum();//获取所在行的第一个行号
				int lastCellNum=row.getLastCellNum();//获取所在行的最后一个行号
				//System.out.println(firstCellNum+"+++++++++++++"+lastCellNum);
				for (int j = firstCellNum; j <lastCellNum; j++) {
					XSSFCell cell=row.getCell(j);
					cell.setCellType(CellType.STRING);
					String value=cell.getStringCellValue();
					System.out.print(value+" ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void writeExcel(String path){
		XSSFWorkbook workbook=new XSSFWorkbook();
		XSSFSheet sheet=workbook.createSheet("测试文档");
		XSSFRow firstRow=sheet.createRow(0);
		XSSFCell one=firstRow.createCell(0);
		one.setCellValue("姓名");
		XSSFCell two=firstRow.createCell(1);
		two.setCellValue("班级");
		for (int i=1;i<10;i++){
			XSSFRow row=sheet.createRow(i);
			for (int j=0;j<10;j++){
				XSSFCell cell=row.createCell(j);
				cell.setCellValue("第"+i+"行"+j+"列");
			}
		}
		try {
			//File file=new File(path+"\\test.xlsx");
			workbook.write(new FileOutputStream(path+"\\test2.xlsx"));
		}
		catch (IOException e){
			e.printStackTrace();
		}

	}
}
