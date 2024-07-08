package ua.wyverno.table;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.wyverno.Config;
import ua.wyverno.util.TableUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TableVerification {
    private static final Logger logger = LoggerFactory.getLogger(TableVerification.class);
    private final Workbook workbook;
    private List<KeyLang> duplicateKeys;
    public TableVerification() {
        try {
            logger.info("Downloading table...");
            URL url = URI.create(Config.getInstance().getTableUrl()).toURL();

            this.workbook = new XSSFWorkbook(url.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasDuplicate() {
        DataFormatter formatter = new DataFormatter();

        List<KeyLang> listIDs = new ArrayList<>(); // Список для зберігання айді ключів
        Iterator<Sheet> sheetIterator = this.workbook.sheetIterator(); // Воркбук таблиці


        while (sheetIterator.hasNext()) { // Проходимся по всіх таблицям
            Sheet sheet = sheetIterator.next();
            String sheetName = sheet.getSheetName();
            logger.debug("Parsing table: {}", sheetName);

            TableHeader header = new TableHeader(sheet.getRow(0));

            Iterator<Row> rowIterator = sheet.rowIterator();
            rowIterator.next(); // skip first row

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (!TableUtil.isEmptyRow(row)) {
                    String id = formatter.formatCellValue(row.getCell(header.getColumnIndexByName("ID")));
                    String a1Note = row.getCell(header.getColumnIndexByName("ID")).getAddress().formatAsString();
                    listIDs.add(new KeyLang(id, a1Note, sheetName));
                }
            }
        }

        Map<KeyLang, Long> frequancyMap = listIDs.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<KeyLang> duplicateKeys = frequancyMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();

        if (duplicateKeys.size() > 0) { // Перевіряємо чи є у списку дублікати?
            this.duplicateKeys = listIDs.stream() // Шукаємо всі пари які були дубльовані
                    .filter(duplicateKeys::contains)
                    .toList();
        } else { // Якщо немає просто створюємо порожній лист
            this.duplicateKeys = new ArrayList<>();
        }

        return this.duplicateKeys.size() > 0;// Якщо список порожній значить немає дублікатів
    }

    public List<KeyLang> getDuplicateKeys() {
        return duplicateKeys;
    }
}
