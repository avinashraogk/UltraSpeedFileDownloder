package ultra.speed.downloader.main;

import static ultra.speed.downloader.util.ApplicationProperties.NO_OF_THREADS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main Class of the Ultra Speed File Downloader Application.
 * takes main arguments.
 * Command : 
 * $> java -jar MainApplication URL1 URL2 URL3
 * 
 * @author avinash.rao
 *
 */
public class MainApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
        List<String> urls = Arrays.asList(args);
        LOGGER.info("URLS recieved :" + urls);

        processAsynchronously(urls);
    }

    public static void processAsynchronously(List<String> urls) {

        List<Runnable> workerThreads = new ArrayList<>();
        urls.forEach(url -> workerThreads.add(new FileDownlodingThread(url)));

        ExecutorService executorService = Executors.newFixedThreadPool(NO_OF_THREADS);
        workerThreads.forEach(task -> executorService.execute(task));

        LOGGER.info("-------- Threads are at work !! Have patience !! -----------");

        executorService.shutdown();
    }
}
