package ultra.speed.downloader.protocols;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ultra.speed.downloader.models.ResourceDetails;
import ultra.speed.downloader.util.ApplicationProperties;
import ultra.speed.downloader.util.Utils;

/**
 * Downloads the files with FTP Protocol.
 * 
 * @author avinash.rao
 *
 */
public class FtpFileDownloder implements FileDownloder {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpFileDownloder.class);

    private FTPClient ftpClient;
    private File localFile;
    private ResourceDetails resourceDetails;

    /**
     * Initializes the FTP client with FTP URL
     * @param ftpUrl
     */
    FtpFileDownloder(String ftpUrl) {
        resourceDetails = Utils.getResourceDetails(ftpUrl);
    }

    @Override
    public void downloadFile() throws IOException {
        createFtpConnection();
        setUpLocalFilePath();
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localFile))) {
            ftpClient.retrieveFile(resourceDetails.getRemoteFileName(), outputStream);
            LOGGER.info("Successfully downloaded the file to : " + localFile.getAbsolutePath());
        } finally {
            terminateFtpConnection();
        }
    }

    @Override
    public void deleteFile() {
        LOGGER.info("Deleting the file : " + localFile.getAbsolutePath());
        localFile.deleteOnExit();
    }

    /**
     * To Initialize the FTP Client.
     */
    private void createFtpConnection() {
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(resourceDetails.getServer(), resourceDetails.getPort());
            if (!resourceDetails.isPublicDomain()) {
                ftpClient.login(resourceDetails.getUsername(), resourceDetails.getPassword());
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setBufferSize(1024 * 64);
            LOGGER.info("FTP Client initialized.");
        } catch (IOException e) {
            LOGGER.error("Exception happened at initializing the FTP Client.", e);
        }
    }

    /**
     * Terminate the FTP Connection
     */
    private void terminateFtpConnection() {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            LOGGER.error("Error while closing the FTP Client", e);
        }
    }

    /**
     * Create local file to download.
     */
    private void setUpLocalFilePath() {
        String localFileName = ApplicationProperties.DOWNLOAD_PATH + resourceDetails.getServer()
                + resourceDetails.getRemoteFileName();
        localFile = Paths.get(localFileName).toFile();
    }
}
