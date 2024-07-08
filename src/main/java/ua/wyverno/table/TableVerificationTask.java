package ua.wyverno.table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TableVerificationTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(TableVerificationTask.class);

    private final ScheduledExecutorService executorService;

    private final SystemTray tray;
    private final TrayIcon trayIcon;
    public TableVerificationTask(ScheduledExecutorService executorService) throws AWTException {
        this.executorService = executorService;
        this.tray = SystemTray.getSystemTray();

        Image icon = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/java-icon.png"));
        this.trayIcon = new TrayIcon(icon, "Tray demo");
        this.trayIcon.setImageAutoSize(true);
        this.trayIcon.setToolTip("Table-Verification");

        this.tray.add(trayIcon);
    }
    public void run() {
        try {
            logger.info("Starting to check correct table");
            TableVerification tableVerification = new TableVerification();

            if (tableVerification.hasDuplicate()) {
                logger.error("TABLE IS NOT CORRECT!");
                List<KeyLang> duplicateList = tableVerification.getDuplicateKeys();

                Map<KeyLang, List<KeyLang>> keyDuplicateMap = duplicateList.stream()
                        .collect(Collectors.groupingBy(pair -> pair));

                for (Map.Entry<KeyLang, List<KeyLang>> entry : keyDuplicateMap.entrySet()) {
                    KeyLang keyLang = entry.getKey();
                    List<KeyLang> duplicatePairList = entry.getValue();
                    logger.error("Duplicate Key - ID: \"{}\"", keyLang.id());

                    for (KeyLang p : duplicatePairList) {
                        logger.warn("Sheet: \"{}\" | ID: \"{}\" | Address: \"{}\"",
                                p.sheetName(),p.id(), p.a1Note());
                    }
                }


                this.showWarningNotification();
            } else {
                logger.info("Table is correct!");
            }
        } catch (Throwable e) {
            logger.error("", e);
            this.shutdownExecutor();
        }
    }

    public void shutdownExecutor() {
        logger.info("Shutdown program!");
        this.executorService.shutdown();
        tray.remove(trayIcon);
        try {
            if (!this.executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                this.executorService.shutdownNow();
            }
            logger.info("Shutdown complete!");
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            logger.info("Shutdown complete!");
        }
    }

    private void showWarningNotification() {
        this.trayIcon.displayMessage("TABLE HAS DUPLICATE!", "Checking you're console for details!", TrayIcon.MessageType.WARNING);
    }
}
