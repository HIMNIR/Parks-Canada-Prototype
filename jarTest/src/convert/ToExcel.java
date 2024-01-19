package convert;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ToExcel {
    public static void MRG(File f) {
        try {
            ArrayList<String> data = PdfToText.convertMRG();
            FileInputStream myxlsx = new FileInputStream(f);
            Workbook studentsWorkbook = new XSSFWorkbook(myxlsx);
            Sheet worksheet = studentsWorkbook.getSheetAt(0);
            int lastRow = worksheet.getLastRowNum();

            // Create a new row
            Row row = worksheet.createRow(++lastRow);
            
            // Write data from ArrayList to cells in the row
            int cellNum = 0;
            for (String item : data) {
            	System.out.println(item);
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue(item);
            }

            myxlsx.close();
            FileOutputStream output_file = new FileOutputStream(f);
            // Write changes
            studentsWorkbook.write(output_file);
           
            output_file.close();
            System.out.println("Excel file is successfully written");
            studentsWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public static void Banff(File f) {
    try {
        ArrayList<String> data = PdfToText.convertBanff();
        FileInputStream myxlsx = new FileInputStream(f);
        Workbook studentsWorkbook = new XSSFWorkbook(myxlsx);
        Sheet worksheet = studentsWorkbook.getSheetAt(0);
        int lastRow = worksheet.getLastRowNum();

        // Create a new row
        Row row = worksheet.createRow(++lastRow);
        
        // Write data from ArrayList to cells in the row
        int cellNum = 0;
        for (String item : data) {
        	System.out.println(item);
            Cell cell = row.createCell(cellNum++);
            cell.setCellValue(item);
        }

        myxlsx.close();
        FileOutputStream output_file = new FileOutputStream(f);
        // Write changes
        studentsWorkbook.write(output_file);
        output_file.close();
        studentsWorkbook.close();
        System.out.println("Excel file is successfully written");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public static void Jasper(File f) {
    try {
        ArrayList<String> data = PdfToText.convertJasper();
        FileInputStream myxlsx = new FileInputStream(f);
        Workbook studentsWorkbook = new XSSFWorkbook(myxlsx);
        Sheet worksheet = studentsWorkbook.getSheetAt(0);
        int lastRow = worksheet.getLastRowNum();

        // Create a new row
        Row row = worksheet.createRow(++lastRow);
        
        // Write data from ArrayList to cells in the row
        int cellNum = 0;
        for (String item : data) {
        	System.out.println(item);
            Cell cell = row.createCell(cellNum++);
            cell.setCellValue(item);
        }

        myxlsx.close();
        FileOutputStream output_file = new FileOutputStream(f);
        // Write changes
        studentsWorkbook.write(output_file);
        output_file.close();
        System.out.println("Excel file is successfully written");
        studentsWorkbook.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
//C:\Users\Utsav Gautam\Downloads\test.xlsx
//G:\FINANCE\Administrative Services\Realty\Business Licences\2023-24 Business Licence\Licences 2023-24\MRG Business Licence #24-BL-0206 - Wyssen Canada Inc FUSAPPROVED.pdf