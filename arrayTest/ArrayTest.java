package arrayTest;
import java.util.Arrays;

public class ArrayTest {
    
    public static void main(String[] args) {
        String [] fruits = {"apple","peach","banana","orange"};

        int amountFruits = fruits.length;
        System.out.println("There are " + amountFruits + " fruits");
       /*  for (int i = 0; i < fruits.length; i++) {
            System.out.println(fruits[i]);
        }*/

        //Arrays.sort(fruits);

        //Fills array with given value
        Arrays.fill(fruits, "pineapple");



        //Foreach loop
        for(String fruit: fruits){
            System.out.println(fruit);
        }

    }
}
   