package usernameex;

public class Normalizeuser {
    static String normalizeUsername(String userName){
        return userName.trim().toLowerCase().replace(" ", "");
    }
}
