package Array2d;

public class Matrix {

    public static void main(String[] args) {
        /*String[] fruits = {"apple","banana","orange"};
        String[] vegetables = {"potato","onion","carrot"};
        String[] meats = {"chicken","pork","beef","fish"};

        String[][] groceries = {fruits,vegetables,meats};

        for(String[] foods : groceries){
            for(String food : foods){
                System.out.println(food + " ");
        }
        }*/

        char[][] telephone = {
            {'1','2','3'},
            {'4','5','6'},
            {'7','8','9'},
            {'*','0','#'}};
        for(char[] row : telephone){
            for(char number : row){
                System.out.print(number + " ");
            }
            System.out.println();
        }
    }
}
