import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmailDataBase {

    private List<Email> emails;
    private FileHandler fileHandler;
    private String loggedInUser;

    public EmailDataBase(String sender) {
        fileHandler = new FileHandler();
        emails = new ArrayList<>();
        loggedInUser = sender;
    }

    public void loadUserEmails(String userEmail) {
        loggedInUser = userEmail;
        try {
            emails = fileHandler.readEmail(userEmail);

            System.out.println("Loaded " + emails.size() + " emails for user: " + loggedInUser);

        } catch (IOException e) {
            emails = new ArrayList<>();
            e.printStackTrace();
        }
    }

    public void saveUserEmails() {
        if (loggedInUser == null) {
            throw new IllegalStateException("No user is logged in.");
        }
        try {
            fileHandler.saveEmail(loggedInUser, emails);

            System.out.println("Saved " + emails.size() + " emails for user: " + loggedInUser);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(Email email) {
        /*
         * emails.add(email);
         * 
         * System.out.println("Sending email: " + email.getSubject() + " to " +
         * email.getRecipient());
         * 
         * saveUserEmails();
         * 
         * try {
         * fileHandler.saveEmail(email.getRecipient(), emails); // Save for recipient
         * } catch (IOException e) {
         * e.printStackTrace();
         * }
         */

        System.out.println("Sending email: " + email.getSubject() + " to " + email.getRecipient());
        EmailDataBase recipientEmailDB = new EmailDataBase(email.getRecipient());
        recipientEmailDB.loadUserEmails(email.getRecipient());
        recipientEmailDB.emails.add(email);
        System.out.println("Saving email to recipient: " + email.getRecipient());
        recipientEmailDB.saveUserEmails();
        System.out.println("Email saved successfully.");

    }

    public List<Email> getInbox(String userEmail) {
        List<Email> inbox = new ArrayList<>();
        for (Email e : emails) {
            if (e.getRecipient().equals(userEmail)) {
                inbox.add(e);
            }
        }

        System.out.println("Inbox contains " + inbox.size() + " emails for user: " + loggedInUser);

        return inbox;
    }

    public void deleteEmail(String userEmail, Email emailDel) {
        emails.remove(emailDel);

        System.out.println("Deleting email: " + emailDel.getSubject());

        saveUserEmails();
    }

}
