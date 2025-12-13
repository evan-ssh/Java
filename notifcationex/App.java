package notifcationex;

public class App {
    public static void main(String[] args) {
        Notification[] notes = {
            new EmailNotification("hi", "a@b.com"),
            new SMSNotification("yo", "555")
        };

        for (Notification n : notes) {
            System.out.println(n.preview());
            System.out.println(n.send());
        }

        Sendable[] sendables = {
            new EmailNotification("hi", "a@b.com"),
            new SMSNotification("yo", "555")
        };

        for (Sendable s : sendables) {
            System.out.println(s.send());
        }
    }
}
