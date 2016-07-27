package ultra.speed.downloader.protocols;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for FileDownloder
 * 
 * @author avinash.rao
 */
public class FileDownloderTest {

    private String httpUrl;
    private String ftpUrl;
    private String sftpUrl;

    @Before
    public void setUp() {
        httpUrl = "http://server.com/file.txt";
        ftpUrl = "ftp://server.com/file.txt";
        sftpUrl = "sftp://server.com/file.txt";
    }

    @Test
    public void getInstanceHttpTest() throws Exception {
        FileDownloder fileDownloder = FileDownloder.getInstance(httpUrl);
        Assert.assertEquals(fileDownloder.getClass(), HttpFileDownloader.class);
    }

    @Test
    public void getInstanceFtpTest() throws Exception {
        FileDownloder fileDownloder = FileDownloder.getInstance(ftpUrl);
        Assert.assertEquals(fileDownloder.getClass(), FtpFileDownloder.class);
    }

    @Test
    public void getInstanceSftpTest() throws Exception {
        FileDownloder fileDownloder = FileDownloder.getInstance(sftpUrl);
        Assert.assertEquals(fileDownloder.getClass(), SftpFileDownloder.class);
    }

}
