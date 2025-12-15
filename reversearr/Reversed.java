package reversearr;

public class Reversed {
    static int[] reversed(int [] a){
        int [] out = new int[a.length];

        for(int i = a.length - 1; i >= 0; i --){
            out[a.length - 1 - i] = a[i];
        }
        return out;
    }
}
