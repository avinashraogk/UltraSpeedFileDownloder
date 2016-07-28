package ultra.speed.downloader.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Test class for ApplicationProperties
 * 
 * @author avinash.rao
 */
public class ApplicationPropertiesTest {

    @Test
    public void applicationPropertiesTest() {
        assertNotNull(ApplicationProperties.DOWNLOAD_PATH);
        assertNotNull(ApplicationProperties.MAX_FLUSH_COUNT);
        assertNotNull(ApplicationProperties.NO_OF_THREADS);
    }

}
