package ua.wyverno;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.wyverno.exceptions.FileNotFoundExceptionRuntime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private static final Path path = Paths.get("config.properties");

    private static Config instance;
    private final Properties properties = new Properties();
    private Config() throws NoSuchFileException {
        try {
            logger.trace("Start read Config properties");
            this.properties.load(Files.newInputStream(path));
        } catch (NoSuchFileException e) {
            logger.warn("File config not exists!");
            logger.warn("File config be created!");

            this.properties.put("table-url", "");

            try {
                this.properties.store(Files.newBufferedWriter(path), "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw e;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Config getInstance() {
        if (instance == null) {
            try {
                instance = new Config();
            } catch (NoSuchFileException e) {
                throw new FileNotFoundExceptionRuntime(e);
            }
        }

        return instance;
    }

    public String getTableUrl() {
        return this.properties.getProperty("table-url");
    }
}
