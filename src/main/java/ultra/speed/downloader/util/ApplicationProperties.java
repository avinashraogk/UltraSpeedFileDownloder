package ultra.speed.downloader.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to Read Application Properties
 * 
 * @author avinash.rao
 *
 */
public class ApplicationProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProperties.class);

    public static final Path DOWNLOAD_PATH;
    public static final int NO_OF_THREADS;
    public static final int MAX_FLUSH_COUNT;

    static {
        Properties p = new Properties();
        try {
            p.load(ApplicationProperties.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("IO Exception for Application property file!");
        }
        DOWNLOAD_PATH = Paths.get(p.getProperty("pathToDownload"));
        NO_OF_THREADS = Integer.parseInt(p.getProperty("noOfThreads").trim());
        MAX_FLUSH_COUNT = Integer.parseInt(p.getProperty("maxFlushCount").trim());

        printProperties();
    }

    /**
     * To print the properties
     */
    private static void printProperties() {
        LOGGER.info("==========================================================================");
        LOGGER.info("DOWNLOAD PATH              : " + DOWNLOAD_PATH);
        LOGGER.info("NUMBER OF THREADS          : " + NO_OF_THREADS);
        LOGGER.info("MAX FLUSH COUNT            : " + MAX_FLUSH_COUNT);
        LOGGER.info("==========================================================================");
    }

}
