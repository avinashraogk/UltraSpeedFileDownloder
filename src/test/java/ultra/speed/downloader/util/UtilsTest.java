package ultra.speed.downloader.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ultra.speed.downloader.util.Utils.getLocalFileFromUrl;
import static ultra.speed.downloader.util.Utils.getResourceDetails;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import ultra.speed.downloader.models.ResourceDetails;

/**
 * Test class for Utils
 * 
 * @author avinash.rao
 */
public class UtilsTest {

    private ResourceDetails resourceDetailsForPrivateFtpUrl;
    private ResourceDetails resourceDetailsForPublicFtpUrl;
    private ResourceDetails resourceDetailsForPrivateSftpUrl;

    private String httpUrl = "http://sample.server.com/file.txt";
    private String privateFtpUrl = "ftp://user001:secretpassword@private.ftp-servers.example.com/mydirectory/myfile.txt";
    private String publicFtpUrl = "ftp://public.ftp-servers.example.com/mydirectory/myfile.txt";
    private String privateSftpUrl = "sftp://sftpUser001:secretpassword@private.sftp-servers.example.com/mydirectory/myfile.txt";

    @Before
    public void setUp() {
        resourceDetailsForPrivateFtpUrl = new ResourceDetails();
        resourceDetailsForPrivateFtpUrl.setUsername("user001");
        resourceDetailsForPrivateFtpUrl.setPassword("secretpassword");
        resourceDetailsForPrivateFtpUrl.setServer("private.ftp-servers.example.com");
        resourceDetailsForPrivateFtpUrl.setRemoteFileName("myfile.txt");
        resourceDetailsForPrivateFtpUrl.setPort(21);

        resourceDetailsForPublicFtpUrl = new ResourceDetails();
        resourceDetailsForPublicFtpUrl.setServer("public.ftp-servers.example.com");
        resourceDetailsForPublicFtpUrl.setRemoteFileName("myfile.txt");
        resourceDetailsForPublicFtpUrl.setPort(21);

        resourceDetailsForPrivateSftpUrl = new ResourceDetails();
        resourceDetailsForPrivateSftpUrl.setUsername("sftpUser001");
        resourceDetailsForPrivateSftpUrl.setPassword("secretpassword");
        resourceDetailsForPrivateSftpUrl.setServer("private.sftp-servers.example.com");
        resourceDetailsForPrivateSftpUrl.setRemoteFileName("myfile.txt");
        resourceDetailsForPrivateSftpUrl.setPort(22);
    }

    @Test
    public void getResourceDetailsForPrivateFtpUrlTest() {
        ResourceDetails details = getResourceDetails(privateFtpUrl);
        assertEquals(resourceDetailsForPrivateFtpUrl.getPassword(), details.getPassword());
        assertEquals(resourceDetailsForPrivateFtpUrl.getUsername(), details.getUsername());
        assertEquals(resourceDetailsForPrivateFtpUrl.getPort(), details.getPort());
        assertEquals(resourceDetailsForPrivateFtpUrl.getServer(), details.getServer());
        assertEquals(resourceDetailsForPrivateFtpUrl.getRemoteFileName(), details.getRemoteFileName());
    }

    @Test
    public void getResourceDetailsForPublicFtpUrlTest() {
        ResourceDetails details = getResourceDetails(publicFtpUrl);
        assertEquals(resourceDetailsForPublicFtpUrl.getPort(), details.getPort());
        assertEquals(resourceDetailsForPublicFtpUrl.getServer(), details.getServer());
        assertEquals(resourceDetailsForPublicFtpUrl.getRemoteFileName(), details.getRemoteFileName());
    }

    @Test
    public void getResourceDetailsForPrivateSftpUrlTest() {
        ResourceDetails details = getResourceDetails(privateSftpUrl);
        assertEquals(resourceDetailsForPrivateSftpUrl.getPassword(), details.getPassword());
        assertEquals(resourceDetailsForPrivateSftpUrl.getUsername(), details.getUsername());
        assertEquals(resourceDetailsForPrivateSftpUrl.getPort(), details.getPort());
        assertEquals(resourceDetailsForPrivateSftpUrl.getServer(), details.getServer());
        assertEquals(resourceDetailsForPrivateSftpUrl.getRemoteFileName(), details.getRemoteFileName());
    }

    @Test
    public void getLocalFileFromUrlTest() throws Exception {
        File file = getLocalFileFromUrl(new URL(httpUrl));
        assertNotNull(file);
    }
}
