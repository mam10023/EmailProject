import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public void saveEmail(String userEmail, Email email) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(userEmail + "emails.txt", true))) {
            out.println("Sender: " + email.getSender() + " | Recipient: " + email.getRecipient() + " | "
                    + email.getSubject() + ": " + email.getEmailContent());
        }
    }

    public List<Email> readEmail(String userEmail) throws IOException {
        List <Email> emails = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(userEmail + "emails.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("," 4);
                if (parts.length == 4) {
                    String sender = parts[0];
                    String recipient = parts[1];
                    String subject = parts[2];
                    String emailContent = parts[3];
                    emails.add(new Email(sender, recipient, subject, emailContent));
                } else {
                    System.err.println("error");
                }
            }
        }
        return emails;
    }
}
