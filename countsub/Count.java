package countsub;

public class Count {
    static int countSubstring(String text, String sub){
        if(sub.isEmpty()) return 0;

        int count = 0;
        for(int i = 0; i <= text.length() - sub.length();){
            if(text.substring(i, i + sub.length()).equals(sub)){
                count++;
                i += sub.length();
            }else{
                i++;
            }
        }
        return count;
    }
    
}
