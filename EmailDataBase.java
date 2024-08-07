import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmailDataBase {

    private List<User> users;
    private List<Email> emails;
    private FileHandler fileHandler;
    private User loggedInUser;

    public EmailDataBase() {
        fileHandler = new FileHandler();
        try {
            users = fileHandler.readUsers();
        } catch (IOException e) {
            users = new ArrayList<>();
            e.printStackTrace(); // **
        }
        emails = new ArrayList<>();
        loggedInUser = null;
    }

    public boolean login(String email, String password) {
        User user = getUser(email);
        if (user != null && user.getPassword().equals(password)) {
            loggedInUser = user;
            loadUserEmails(email);
            return true;
        }
        return false;
    }

    public User getUser(String email) {
        for (User username : users) {
            if (username.getEmail().equals(email)) {
                return username;
            }
        }
        return null;
    }

    public void logout() {
        loggedInUser = null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void loadUserEmails(String userEmail) {
        try {
            emails = fileHandler.readEmail(userEmail);
        } catch (IOException e) {
            emails = new ArrayList<>();
            e.printStackTrace();
        }
    }

    public void saveUserEmails(String userEmail) {
        try {
            fileHandler.saveEmail(userEmail, emails);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(Email email) {
        emails.add(email);
        saveUserEmails(email.getRecipient());
    }

    public List<Email> getInbox(String userEmail) {
        List<Email> inbox = new ArrayList<>();
        for (Email e : emails) {
            if (e.getRecipient().equals(userEmail)) {
                inbox.add(e);
            }
        }
        return inbox;
    }

}
