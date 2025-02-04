package com.uno.bank.sflead.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelReportGenerator {
    public static void generateReport(List<TestResult> results, String filePath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Test Results");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Test Class");
            headerRow.createCell(1).setCellValue("Test Method");
            headerRow.createCell(2).setCellValue("Status");
            headerRow.createCell(3).setCellValue("Error Message");
            headerRow.createCell(4).setCellValue("Duration (ms)");

            int rowNum = 1;
            for (TestResult result : results) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(result.getTestClass());
                row.createCell(1).setCellValue(result.getTestMethod());
                row.createCell(2).setCellValue(result.getStatus());
                row.createCell(3).setCellValue(result.getErrorMessage());
                row.createCell(4).setCellValue(result.getDuration());
            }

            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        }
    }
}
