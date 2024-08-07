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
        }
    }

}
