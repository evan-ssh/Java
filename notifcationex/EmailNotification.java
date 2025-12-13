package notifcationex;

public class EmailNotification extends Notification implements Sendable{


    private String email;
    public EmailNotification(String message, String email){
        super(message);
        if(email == null){
            this.email = "";
        }else{
            this.email = email;
        }
       
    }

    @Override
    public String send(){
        return "Email to " + email + ": " + message;
    }

}
