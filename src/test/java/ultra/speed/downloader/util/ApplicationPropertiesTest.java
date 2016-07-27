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
        assertNotNull(ApplicationProperties.MAX_AWAIT_TIME_IN_SECONDS);
        assertNotNull(ApplicationProperties.NO_OF_THREADS);
    }

}
