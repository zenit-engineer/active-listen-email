package org.example;

import java.util.Properties;
import java.util.concurrent.Callable;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.*;
import javax.mail.internet.InternetAddress;

public class EmailListener implements Callable<Void> {
    private final String host;
    private final String user;
    private final String password;
    private final String senderEmail;

    public EmailListener(String host, String user, String password, String senderEmail) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.senderEmail = senderEmail;
    }

    @Override
    public Void call() throws Exception {
        Properties properties = new Properties();
        properties.put("mail.imaps.host", host);
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.starttls.enable", "true");
        properties.put("mail.imaps.auth", "true"); // Enable IMAP authentication

        Session emailSession = Session.getInstance(properties);

        Store store = emailSession.getStore("imaps");
        store.connect(host, user, password);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        // Filter email / Search for unread messages from the specific sender
        SearchTerm searchTerm = new AndTerm(
                new FromTerm(new InternetAddress(senderEmail)),
                new FlagTerm(new Flags(Flags.Flag.SEEN), false)
        );
        Message[] messages = inbox.search(searchTerm);

        for (Message message : messages) {
            //if specific message(email) is unread will proceed
            if (!message.isSet(Flags.Flag.SEEN)) {
                System.out.println("---------------------------------");
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                //if specific message(email) has attachment
                if (message.isMimeType("multipart/*")) {
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            // Handle the attachment
                            String fileName = part.getFileName();
                            String filePath = "C:\\Users\\neziri.zenit\\Desktop\\SaveFileHere\\" + fileName;
                            part.saveFile(filePath);
                            System.out.println("Attachment saved: " + filePath);
                            System.out.println("___________________________________________________________________________");
                        } else if (part.isMimeType("text/plain")) {
                            // Handle the text content
                            String textContent = part.getContent().toString();
//                            if (textContent.equals("Cryptshare")){
//                                System.out.println();
//                            }
                            System.out.println("Text: " + textContent);
                        }
                    }
                }
                // Make that message as read
                message.setFlag(Flags.Flag.SEEN, true);
            }
        }

        inbox.close(false);
        store.close();
        return null;
    }

    public static void main(String[] args) {

        String host = "outlook.office365.com";// change accordingly
        String username = "zenitneziri@hotmail.com";// change accordingly
        String password = "3I7HDy%n16f2WNAiiZkE";// change accordingly
        String senderEmail = "neziri.zenit@gmail.com";

        while (true) {
            try {
                EmailListener emailListener = new EmailListener(host, username, password, senderEmail);
                emailListener.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Wait for a specific interval before checking again
            try {
                Thread.sleep(5000); // 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
