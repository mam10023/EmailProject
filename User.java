public class User {

    private String email;
    private String password;

    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private String getEmail() {
        return email;
    }

    private String getPassword() {
        return password;
    }

}
