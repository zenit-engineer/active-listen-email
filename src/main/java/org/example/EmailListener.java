package org.example;

import org.example.listener.Listener;
import org.example.processor.EmailProcessor;
import org.example.config.EmailConfig;
import org.example.service.EmailService;

public class EmailListener {
    public static void main(String[] args) {
        // Fetch values from environment variables
        String host = System.getenv("EMAIL_HOST");
        String username = System.getenv("EMAIL_USERNAME");
        String password = System.getenv("EMAIL_PASSWORD");
        String senderEmail = System.getenv("FROM_SENDER_EMAIL");
        String downloadPath = System.getenv("DOWNLOAD_PATH");

        // Check for nulls (optional safety)
        if (host == null || username == null || password == null || senderEmail == null || downloadPath == null) {
            System.err.println("Missing one or more required environment variables.");
            return;
        }

        EmailConfig config = new EmailConfig(host, username, password, senderEmail, downloadPath);
        EmailProcessor processor = new EmailProcessor();
        EmailService service = new EmailService(config, processor);
        Listener listener = new Listener(service);

        while (true) {
            try {
                listener.call();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(5000); // 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
