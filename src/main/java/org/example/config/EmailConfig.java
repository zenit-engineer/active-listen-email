package org.example.config;

public class EmailConfig {
    private String host;
    private String username;
    private String password;
    private String senderEmail;
    private String downloadPath;

    public EmailConfig(String host, String username, String password, String senderEmail, String downloadPath) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.senderEmail = senderEmail;
        this.downloadPath = downloadPath;
    }

    public String getHost() { return host; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getSenderEmail() { return senderEmail; }
    public String getDownloadPath() { return downloadPath; }
}
