package asciiex;

public class Ascii {
    static boolean isAsciiLetter(char c){
        return ((c >= 'A' && c <= 'Z') || (c>= 'a' && c <= 'z'));
    }
}
