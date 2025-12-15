package rangeex;

public class RangeCheck {
    static int[] range(int n){
        if(n<0){
            throw new IllegalArgumentException("n < 0");
            
        }
        int[] out = new int[n];
        for(int i = 0; i < n; i++){
            out[i] = i;
        }
        return out;
    }
}
