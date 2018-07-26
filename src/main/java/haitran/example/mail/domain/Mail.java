package haitran.example.mail.domain;

public final class Mail {
    private String recipient;
    private String message;

    public Mail(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
    }

    public Mail(){
        this.recipient = "abc@123";
        this.message = "null";
    }

    public String getRecipient() {

        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}