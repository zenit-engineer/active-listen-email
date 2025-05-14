package org.example.util;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.IOException;

public class EmailUtils {
    public static void saveAttachment(MimeBodyPart part, String downloadPath) throws IOException, MessagingException {
        System.out.println("Saving attachment on local machine...");
        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
            String fileName = part.getFileName();
            String filePath = downloadPath + File.separator + fileName;
            part.saveFile(filePath);
            System.out.println("Attachment saved to: " + filePath);
        }
    }
}
