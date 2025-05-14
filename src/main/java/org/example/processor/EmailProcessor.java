package org.example.processor;

import org.example.util.EmailUtils;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;

public class EmailProcessor {

    public void processMessage(Message message, String downloadPath) throws MessagingException, IOException {
        System.out.println("---------------------------------");
        System.out.println("Subject: " + message.getSubject());
        System.out.println("From: " + message.getFrom()[0]);

        if (message.isMimeType("multipart/*")) {
            Multipart multiPart = (Multipart) message.getContent();
            for (int i = 0; i < multiPart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);

                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    // Instead of saving the attachment here, we call EmailUtils
                    EmailUtils.saveAttachment(part, downloadPath); // Call to saveAttachment
                } else if (part.isMimeType("text/plain")) {
                    System.out.println("Text: " + part.getContent().toString());
                }
            }
        }
        message.setFlag(Flags.Flag.SEEN, true); // Mark as read
    }
}
