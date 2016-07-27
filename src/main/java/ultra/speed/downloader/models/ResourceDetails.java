package ultra.speed.downloader.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Model class to hold the URL related fields.
 * 
 * @author avinash.rao
 *
 */
public class ResourceDetails {

    private String remoteFileName;
    private String username;
    private String password;
    private String server;
    private int port;
    private boolean isPublicDomain;

    /**
     * @return the remoteFileName
     */
    public String getRemoteFileName() {
        return remoteFileName;
    }

    /**
     * @param remoteFileName the remoteFileName to set
     */
    public void setRemoteFileName(String remoteFileName) {
        this.remoteFileName = remoteFileName;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the isPublicDomain
     */
    public boolean isPublicDomain() {
        return isPublicDomain;
    }

    /**
     * @param isPublicDomain the isPublicDomain to set
     */
    public void setPublicDomain(boolean isPublicDomain) {
        this.isPublicDomain = isPublicDomain;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
