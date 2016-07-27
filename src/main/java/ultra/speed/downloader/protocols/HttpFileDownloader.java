package ultra.speed.downloader.protocols;

import static org.apache.commons.io.FileUtils.copyURLToFile;
import static ultra.speed.downloader.util.ApplicationProperties.MAX_FLUSH_COUNT;
import static ultra.speed.downloader.util.Utils.copyLargeFile;
import static ultra.speed.downloader.util.Utils.getLocalFileFromUrl;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Downloads the file with HTTP protocol
 * 
 * @author avinash.rao
 *
 */
public class HttpFileDownloader implements FileDownloder {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpFileDownloader.class);

    private String urlToDownload;
    private File localFile;

    HttpFileDownloader(String url) {
        urlToDownload = url;
    }

    @Override
    public void downloadFile() throws IOException {
        URL url = new URL(urlToDownload);
        localFile = getLocalFileFromUrl(url);

        if (url.openConnection().getContentLength() > MAX_FLUSH_COUNT) {
            copyLargeFile(url, localFile);
        } else {
            copyURLToFile(url, localFile);
        }

        LOGGER.info("Successfully downloaded the file to : " + localFile.getAbsolutePath());
    }

    @Override
    public void deleteFile() {
        LOGGER.info("Deleting the file : " + localFile.getAbsolutePath());
        localFile.deleteOnExit();
    }

}
