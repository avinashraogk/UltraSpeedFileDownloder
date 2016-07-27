package ultra.speed.downloader.util;

import static ultra.speed.downloader.util.ApplicationProperties.DOWNLOAD_PATH;
import static ultra.speed.downloader.util.ApplicationProperties.MAX_FLUSH_COUNT;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import ultra.speed.downloader.models.ResourceDetails;

/**
 * Utility methods.
 * 
 * @author avinash.rao
 */
public class Utils {

    /**
     * This can be used for parsing ftp and sftp protocols and get Server details.
     * @param ftpUrl
     * @return
     */
    public static ResourceDetails getResourceDetails(String ftpUrl) {
        return parseFtpOrSftpUrl(ftpUrl);
    }

    /**
     * Should Parse the FTP URLs with RegEx :  ftp://[user[:password]@]host[:port]/url-path
     * Examples : 
     *  - ftp://public.ftp-servers.example.com/mydirectory/myfile.txt
     *  - ftp://user001:secretpassword@private.ftp-servers.example.com/mydirectory/myfile.txt
     * 
     * @param url
     */
    private static ResourceDetails parseFtpOrSftpUrl(String url) {
        ResourceDetails resourceDetails;
        if (url.contains("@")) {
            resourceDetails = parsePrivateFTPUrl(url);
            resourceDetails.setPublicDomain(false);
        } else {
            resourceDetails = parsePublicFTPUrl(url);
            resourceDetails.setPublicDomain(false);
        }
        switch (url.split("://")[0]) {
        case "ftp":
            if (resourceDetails.getPort() == 0) {
                resourceDetails.setPort(21);
            }
            break;
        case "sftp":
            if (resourceDetails.getPort() == 0) {
                resourceDetails.setPort(22);
            }
            break;
        }
        return resourceDetails;
    }

    /**
     * Parses FTP URLS of type : ftp://public.ftp-servers.example.com/mydirectory/myfile.txt
     * @param url
     */
    private static ResourceDetails parsePublicFTPUrl(String url) {
        ResourceDetails resourceDetails = new ResourceDetails();
        String[] serverFileInfo = url.split("/");
        if (serverFileInfo[2].contains(":")) {
            String[] splitByColumn = serverFileInfo[2].split(":");
            resourceDetails.setServer(splitByColumn[0]);
            resourceDetails.setPort(Integer.parseInt(splitByColumn[1]));
        } else {
            resourceDetails.setServer(serverFileInfo[2]);
        }
        resourceDetails.setRemoteFileName(serverFileInfo[serverFileInfo.length - 1]);
        return resourceDetails;
    }

    /**
     * Parses URLs of Type : ftp://user001:secretpassword@private.ftp-servers.example.com/mydirectory/myfile.txt
     * @param url
     */
    private static ResourceDetails parsePrivateFTPUrl(String url) {

        ResourceDetails resourceDetails = new ResourceDetails();
        String[] splitByAt = url.split("@");
        String[] userInfo = splitByAt[0].split(":");
        resourceDetails.setUsername(userInfo[1].substring(2)); // Removing '//'
        resourceDetails.setPassword(userInfo[2]);
        String[] serverFileInfo = splitByAt[1].split("/");
        if (serverFileInfo[0].contains(":")) {
            String[] splitByColumn = serverFileInfo[0].split(":");
            resourceDetails.setServer(splitByColumn[0]);
            resourceDetails.setPort(Integer.parseInt(splitByColumn[1]));

        } else {
            resourceDetails.setServer(serverFileInfo[0]);
        }
        resourceDetails.setRemoteFileName(serverFileInfo[serverFileInfo.length - 1]);
        return resourceDetails;
    }

    /**
     * To get the local file Object from the URL.
     * 
     * @param url
     * @return file
     */
    public static File getLocalFileFromUrl(URL url) {
        String fileName = url.getFile();
        String hostName = url.getHost();
        String fileNameToDownload = DOWNLOAD_PATH + hostName + fileName;
        return Paths.get(fileNameToDownload).toFile();
    }

    /**
     * Downloads the Large files from URL with periodic flush method.
     * 
     * @param url
     * @param destination
     * @throws IOException
     */
    public static void copyLargeFile(URL url, File destination) throws IOException {
        try (InputStream input = url.openStream();
                FileOutputStream output = FileUtils.openOutputStream(destination)) {

            byte[] buffer = new byte[8 * 1024];
            int n = 0;
            int count = 0;
            while ((n = input.read(buffer)) != -1) {
                output.write(buffer, 0, n);
                count += n;
                if (count > MAX_FLUSH_COUNT) {
                    output.flush();
                    count = 0;
                }
            }
        }
    }

    /**
     * Private constructor
     */
    private Utils() {
    }
}
