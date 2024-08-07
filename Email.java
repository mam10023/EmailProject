public class Email {
    private String subject;
    private String recipient;
    private String emailContent;

    public Email(String subject, String recipient, String emailContent) {
        this.subject = subject;
        this.recipient = recipient;
        this.emailContent = emailContent;
    }

    public String getSubject() {
        return subject;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getEmailContent() {
        return emailContent;
    }

}