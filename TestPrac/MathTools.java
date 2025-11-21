package TestPrac;

public class MathTools {

    public static int clamp(int value, int min, int max){
        if (value < min){
            return min;
        } else if (value > max){
            return max;
        } else {
            return value;
        }
    }

    public static int sumToN(int n){
        if(n <= 0){
            return 0;
        }

        int sum = 0;
        for(int i = 1; i <= n; i++){
            sum += i;
        }

        return sum;
    }
}
