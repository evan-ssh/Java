package NestedFor;

public class NestedFor {
    public static void main(String[] args) {
        //Square
        int row = 3;
        int col = 3;


        for(int i = 0; i <= row; i++){
            for(int j = 0; j <= col; j++){
                System.out.println("* ");
            }
            System.out.println("* ");
        }
    }
}
