package ua.wyverno;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.wyverno.table.TableVerificationTask;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main( String[] args ) throws AWTException, IOException {
        logger.info("Starting checking table program...");
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        TableVerificationTask task = new TableVerificationTask(executorService);
        executorService.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equals("/stop")) {
                logger.info("The program stops.");
                task.shutdownExecutor();
                break;
            } else {
                logger.info("Print \"/stop\" - to stop program!");
            }
        }
    }
}
