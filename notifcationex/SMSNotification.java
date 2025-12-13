package notifcationex;

public class SMSNotification extends Notification implements Sendable {
    private String phone;

    public SMSNotification(String message, String phone) {
        super(message);

        if (phone == null) {
            this.phone = "";
        } else {
            this.phone = phone;
        }
    }

    @Override
    public String send() {
        return "SMS to " + phone + ": " + this.message;
    }
}
