package ultra.speed.downloader.main;

import static org.junit.Assert.assertNotNull;
import static ultra.speed.downloader.util.Utils.getLocalFileFromUrl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import ultra.speed.downloader.util.ApplicationProperties;

/**
 * Test class for FileDownloadingThread
 * 
 * @author avinash.rao
 *
 */
public class FileDownloadingThreadTest {

    private String fileToDownload = "<Add your URL to Download here>";

    @Before
    public void setUp() {
        try {
            Properties properties = new Properties();
            properties.load(ApplicationProperties.class.getResourceAsStream("/applicationTest.properties"));
            fileToDownload = properties.getProperty("fileToDownload");
        } catch (IOException e) {
            throw new RuntimeException("IO Exception for Test application property file!");
        }
    }

    @Test
    public void fileDownloadingTest() throws IOException {
        File file = getLocalFileFromUrl(new URL(fileToDownload));
        Thread thread = new Thread(new FileDownlodingThread(fileToDownload));
        thread.setDaemon(true);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(file);
    }
}
