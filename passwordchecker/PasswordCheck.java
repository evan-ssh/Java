package passwordchecker;

public class PasswordCheck {
    static boolean isValidPassword(String s){
        if(s == null || s.length() < 9){
            return false;
        }

        boolean hasUpper = false;
        boolean hasDigit = false;

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);

            if(c >= 'A' && c <= 'Z'){
                hasUpper = true;
            }

            if(c >= '0' && c <= '9'){
                hasDigit = true;
            }

            if(hasUpper && hasDigit){
                return true;
            }
        }
        return false;
    }
}
