import java.util.Scanner;

public class PracticeArrays {
    public static int[] addWithResizing(int[] values, int value, int index) {
        /* TODO: This method should: 
                     check if index is larger than values length, and if so:
                         create a new array twice as large as values
                         copy all the values to the new array
                         set values to the new array
                     set the element of values at index to value
                     return values
        */

        int valuesLength = values.length;
        if(index >= valuesLength){
            int[] newArr = new int[valuesLength * 2];
            for(int i = 0; i < valuesLength; i++){
                newArr[i] = values[i];
            }
            values = newArr;
            
        }
        values[index] = value;
        return values;
    }
    public static int[] removeWithResizing(int[] values, int index) {
        /* TODO: This method should: 
                     check if index is less than half values length, and if so:
                         create a new array half as large as values
                         copy the first half of values to the new array
                         set values to the new array
                     set the element of values at index to 0
                     return values
        */
        int valuesLength = values.length;
        if(index < valuesLength/2){
            int[] halfArr = new int[valuesLength/2];
            for(int i = 0; i <= valuesLength/2; i++){
                halfArr[i] = values[i];
            }
            values = halfArr;
           
        }
        if (index < valuesLength){
            values[index] = 0;
        }

        return values;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] values = new int[16];
        int index = 0;
        loop: do {
            System.out.println("Enter 'add' to add a number to the array, 'remove' to remove a number, and anything else to quit.");
            System.out.print("Operation: ");
            String input = sc.next();
            switch (input) {
                case "add":
                    System.out.print("What in do you want to add?: ");
                    if (!sc.hasNextInt()) {
                        System.out.println(sc.next() + " was not an integer");
                        continue;
                    }
                    addWithResizing(values, sc.nextInt(), ++index);
                    break;
                case "remove":
                    if (index < 0) {
                        System.out.println("Array is already empty");
                        continue;
                    }
                    removeWithResizing(values, index--);
                    break;
                default:
                    break loop;
            }
        } while (true);
        sc.close();
    }
}
