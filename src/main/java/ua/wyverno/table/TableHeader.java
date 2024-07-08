package ua.wyverno.table;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import ua.wyverno.table.exceptions.TableNotHasColumnNameException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TableHeader {
    private final Map<String, Integer> columnIndexMap = new HashMap<>();
    private final String sheetName;

    public TableHeader(Row row) {
        this.sheetName = row.getSheet().getSheetName();
        Iterator<Cell> cellIterator = row.cellIterator();


        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String columnName = cell.getStringCellValue();
            if (!columnName.isEmpty()) {
                this.columnIndexMap.put(columnName, cell.getColumnIndex());
            }
        }
    }

    public String getSheetName() {
        return sheetName;
    }

    public int getColumnIndexByName(String name) {
        if (this.columnIndexMap.containsKey(name)) return this.columnIndexMap.get(name);
        throw new TableNotHasColumnNameException(String.format("Table - '%s' not has column - '%s'", this.sheetName, name));
    }
}
