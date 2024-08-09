import java.io.*;
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
        try (PrintWriter out = new PrintWriter(new FileWriter(userEmail + EMAIL_FILE, true))) {
            for (Email e : emails) {
                out.println(
                        e.getSender() + ", " + e.getRecipient() + ", " + e.getSubject() + ", " + e.getEmailContent());
            }
        }
    }

    public List<Email> readEmail(String userEmail) throws IOException {
        List<Email> emails = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(userEmail + EMAIL_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
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

    public void removeEmail(String userEmail, Email email) throws IOException {
        List<Email> emails = readEmail(userEmail);
        emails.removeIf(e -> e.getSender().equals(email.getSender()) && e.getRecipient().equals(email.getRecipient())
                && e.getSubject().equals(email.getSubject()) && e.getEmailContent().equals(email.getEmailContent()));
        try (PrintWriter out = new PrintWriter(new FileWriter(userEmail + EMAIL_FILE))) {
            for (Email e : emails) {
                out.println(
                        e.getSender() + ", " + e.getRecipient() + ", " + e.getSubject() + ", " + e.getEmailContent());
            }
        }
    }

    // not sure if we need this; may use for login
    public void saveUser(User user) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(USERS_FILE, true))) {
            out.println(user.getEmail() + ", " + user.getPassword());
        }
    }

    public List<User> readUsers() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String email = parts[0];
                    String pass = parts[1];
                    users.add(new User(email, pass));
                } else {
                    System.err.println("error");
                }
            }
        }
        return users;
    }
}
