package ultra.speed.downloader.protocols;

import static ultra.speed.downloader.util.ApplicationProperties.DOWNLOAD_PATH;
import static ultra.speed.downloader.util.Utils.getResourceDetails;

import java.io.File;
import java.io.IOException;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ultra.speed.downloader.models.ResourceDetails;

/**
 * Downloads the file with SFTP protocol.
 * 
 * @author avinash.rao
 *
 */
public class SftpFileDownloder implements FileDownloder {

    private static final Logger LOGGER = LoggerFactory.getLogger(SftpFileDownloder.class);

    private String urlToDownload;
    private String localFileName;

    SftpFileDownloder(String url) {
        urlToDownload = url;
        ResourceDetails resource = getResourceDetails(urlToDownload);
        localFileName = DOWNLOAD_PATH + resource.getServer() + resource.getRemoteFileName();
    }

    @Override
    public void downloadFile() throws IOException {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        try {
            manager.init();
            new File(localFileName).createNewFile();
            FileObject localFile = manager.resolveFile(localFileName);
            FileObject remoteFile = manager.resolveFile(urlToDownload, getFileSystemOptions());
            localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);
            LOGGER.info("Successfully downloaded the file to : " + localFileName);
        } finally {
            manager.close();
        }
    }

    @Override
    public void deleteFile() {
        LOGGER.info("Deleting the file : " + localFileName);
        new File(localFileName).deleteOnExit();
    }

    /**
     * Set up the FileSystemOptions
     * 
     * @return FileSystemOptions
     * @throws FileSystemException 
     */
    private FileSystemOptions getFileSystemOptions() throws FileSystemException {
        FileSystemOptions fileSystemOptions = new FileSystemOptions();
        SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fileSystemOptions, "no");
        SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(fileSystemOptions, true);
        SftpFileSystemConfigBuilder.getInstance().setTimeout(fileSystemOptions, 10000);
        return fileSystemOptions;
    }
}
