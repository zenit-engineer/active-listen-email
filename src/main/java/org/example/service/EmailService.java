package org.example.service;

import org.example.processor.EmailProcessor;
import org.example.config.EmailConfig;

import javax.mail.*;
import javax.mail.search.*;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

public class EmailService {
    private final EmailConfig config;
    private final EmailProcessor processor;

    public EmailService(EmailConfig config, EmailProcessor processor) {
        this.config = config;
        this.processor = processor;
    }

    public void checkInbox() throws Exception {
        Properties properties = new Properties();
        properties.put("mail.imaps.host", config.getHost());
        properties.put("mail.imaps.port", System.getenv("IMAPS_PORT"));
        properties.put("mail.imaps.starttls.enable", "true");
        properties.put("mail.imaps.auth", "true");

        Session session = Session.getInstance(properties);
        Store store = session.getStore("imaps");
        store.connect(config.getHost(), config.getUsername(), config.getPassword());

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        SearchTerm searchTerm = new AndTerm(
                new FromTerm(new InternetAddress(config.getSenderEmail())),
                new FlagTerm(new Flags(Flags.Flag.SEEN), false)
        );

        Message[] messages = inbox.search(searchTerm);
        for (Message message : messages) {
            if (!message.isSet(Flags.Flag.SEEN)) {
                processor.processMessage(message, config.getDownloadPath());
            }
        }

        inbox.close(false);
        store.close();
    }
}
