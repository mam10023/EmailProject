import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String EMAIL_DIR = "emails";

    // constructor
    public FileHandler() {
        Path path = Paths.get(EMAIL_DIR);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
                // handle error
            }
        }
    }

    public void saveEmail(String userEmail, List<Email> emails) throws IOException {
        // Save all emails for the user
        Path userDir = Paths.get(EMAIL_DIR, userEmail);
        if (!Files.exists(userDir)) {
            Files.createDirectory(userDir);
        }
        for (Email email : emails) {
            Path emailFile = userDir.resolve(email.getSubject() + ".txt"); // Using subject as filename

            System.out.println("Saving email to: " + emailFile.toString());

            try (BufferedWriter writer = Files.newBufferedWriter(emailFile)) {
                writer.write("From: " + email.getSender() + "\n");
                writer.write("To: " + email.getRecipient() + "\n");
                writer.write("Subject: " + email.getSubject() + "\n");
                writer.write(email.getEmailContent() + "\n");
            }
        }
    }

    public List<Email> readEmail(String userEmail) throws IOException {
        List<Email> emails = new ArrayList<>();
        Path userDir = Paths.get(EMAIL_DIR, userEmail);
        if (Files.exists(userDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(userDir)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {

                        System.out.println("Reading email from: " + entry.toString());

                        try (BufferedReader reader = Files.newBufferedReader(entry)) {
                            String sender = reader.readLine().split(": ")[1];
                            String recipient = reader.readLine().split(": ")[1];
                            String subject = reader.readLine().split(": ")[1];
                            StringBuilder emailContent = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                emailContent.append(line).append("\n");
                            }
                            emails.add(new Email(sender, recipient, subject, emailContent.toString()));
                        }
                    }
                }
            }
        }
        return emails;
    }

    public void removeEmail(String userEmail, Email email) throws IOException {
        List<Email> emails = readEmail(userEmail);
        Path userDir = Paths.get(EMAIL_DIR, userEmail);
        for (Email e : emails) {
            if (e.equals(email)) {
                Path emailFile = userDir.resolve(email.getSubject() + ".txt");

                System.out.println("Deleting email from: " + emailFile.toString());

                Files.delete(emailFile);
                break;
            }
        }
    }

}
