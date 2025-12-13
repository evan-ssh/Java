package notifcationex;

public abstract class Notification {
    protected String message;
    public Notification(String message){
        if(message == null){
            this.message = "";
        }else{
            this.message = message;
        }
    }
    

    public String preview(){
        return "Preview: " + this.message;
    }


    public abstract String send();

    }

