package ultra.speed.downloader.main;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import ultra.speed.downloader.util.ApplicationProperties;
import ultra.speed.downloader.util.Utils;

/**
 * Test class for MainApplication
 * 
 * @author avinash.rao
 */
public class MainApplicationTest {

    private List<String> urls;

    @Before
    public void setUp() {
        try {
            urls = new ArrayList<>();
            Properties properties = new Properties();
            properties.load(ApplicationProperties.class.getResourceAsStream("/applicationTest.properties"));
            urls.addAll(Arrays.asList(properties.getProperty("mainArgs").split(" ")));
        } catch (IOException e) {
            throw new RuntimeException("IO Exception for Test application property file!");
        }
    }

    @Test
    public void mainApplicationTest() throws IOException {
        MainApplication.processAsynchronously(urls);
        File file = Utils.getLocalFileFromUrl(new URL(urls.get(0)));
        assertNotNull(file);
    }
}
