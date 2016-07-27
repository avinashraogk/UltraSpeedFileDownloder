package ultra.speed.downloader.main;

import javax.naming.OperationNotSupportedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ultra.speed.downloader.protocols.FileDownloder;

/**
 * Downloads the contents of the URL in a separate thread.
 *  
 * @author avinash.rao
 *
 */
public class FileDownlodingThread implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDownlodingThread.class);

    private String urlToDownload;

    FileDownlodingThread(String url) {
        urlToDownload = url;
    }

    @Override
    public void run() {
        LOGGER.info("Thread has been started to download : " + urlToDownload);
        try {
            FileDownloder.getInstance(urlToDownload).proceed();
        } catch (OperationNotSupportedException e) {
            LOGGER.error("Protocol not supported.", e);
        }
    }
}
