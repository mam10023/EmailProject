public class Email {
    private String sender;
    private String subject;
    private String recipient;
    private String emailContent;

    public Email(String sender, String recipient, String subject, String emailContent) {
        this.sender = sender;
        this.subject = subject;
        this.recipient = recipient;
        this.emailContent = emailContent;
    }

    public String getSender() {
        return sender;
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