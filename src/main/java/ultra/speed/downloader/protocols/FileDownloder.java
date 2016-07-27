package ultra.speed.downloader.protocols;

import java.io.IOException;

import javax.naming.OperationNotSupportedException;

/**
 * Interface to download the file.
 * 
 * @author avinash.rao
 *
 */
public interface FileDownloder {

    /**
     * Get the FileDownloder based on protocol.
     * @param url
     * @return FileDownloder
     * @throws OperationNotSupportedException
     */
    static FileDownloder getInstance(String url) throws OperationNotSupportedException {
        switch (url.split("://")[0]) {
        case "http":
        case "https":
            return new HttpFileDownloader(url);
        case "ftp":
            return new FtpFileDownloder(url);
        case "sftp":
            return new SftpFileDownloder(url);
        default:
            throw new OperationNotSupportedException("Protocol Not supported !");
        }
    }

    /**
     * Downloads the file and deletes the file in case of exception.
     */
    default void proceed() {
        try {
            downloadFile();
        } catch (Exception e) {
            deleteFile();
        }
    }

    /**
     * To download File from URL
     * @throws IOException
     */
    void downloadFile() throws IOException;

    /**
     * To delete the file in case of exception.
     */
    void deleteFile();
}
