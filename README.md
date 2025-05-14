## 📧 Email Listener Process

These Java automation scripts monitors an email inbox for new messages from a specific sender, processes attachments, and marks emails as read.

### 🔄 Workflow Steps

1. **Read Config from batch file under .bat**  
   - Fetches:
     - `EMAIL_HOST` (e.g., `smtp.gmail.com`)
     - `EMAIL_USERNAME` (your email)
     - `EMAIL_PASSWORD` (app password)
     - `FROM_SENDER_EMAIL` (target sender)
     - `DOWNLOAD_PATH` (local folder for attachments)

2. **Connect to Email Server (IMAP over SSL)**  
   - Uses `javax.mail` to establish a secure IMAP connection.

3. **Search for Unread Emails from Specific Sender**  
   - Filters only unread emails sent by `FROM_SENDER_EMAIL`.

4. **Process Each Matching Email**  
   For every matched email:
   - 📌 **Print metadata**: Subject and sender  
   - 📎 **If attachments exist**:  
     - Saves files to `DOWNLOAD_PATH`  
   - ✉️ **If plain text content exists**:  
     - Prints the message body  
   - ✔️ **Marks email as "Seen"** to prevent reprocessing

5. **Repeat Every 5 Seconds**  
   - Infinite loop with a 5-second delay between checks.

---
