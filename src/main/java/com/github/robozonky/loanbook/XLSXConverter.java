package com.github.robozonky.loanbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

final class XLSXConverter implements Function<InputStream, String[][]> {

    private static final Logger LOGGER = LogManager.getLogger(XLSXConverter.class);
    private final DataFormatter objDefaultFormat = new DataFormatter();

    @Override
    public String[][] apply(final InputStream inputStream) {
        try (final XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            var objFormulaEvaluator = new XSSFFormulaEvaluator(workbook);
            var mySheet = workbook.getSheet("data");
            var numRows = mySheet.getPhysicalNumberOfRows();
            var result = new String[numRows][];
            for (int rowId = 0; rowId < numRows; rowId++) {
                final Row row = mySheet.getRow(rowId);
                final List<String> rowCells = new ArrayList<>();
                for (int colId = 0; colId <= row.getLastCellNum(); colId++) {
                    var cell = row.getCell(colId);
                    objFormulaEvaluator.evaluate(cell);
                    var cellValue = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
                    rowCells.add(cellValue);
                }
                result[rowId] = rowCells.toArray(String[]::new);
                LOGGER.trace("Parsed {}.", Arrays.toString(result[rowId]));
            }
            return result;
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
