package ua.wyverno.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;

public class TableUtil {

    public static boolean isEmptyRow(Row row) {
        Iterator<Cell> cellIterator = row.cellIterator();
        boolean isEmpty = true;

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String value = cell.toString();
            if (!value.isEmpty()) {
                isEmpty = false;
                break;
            }
        }

        return isEmpty;
    }
}