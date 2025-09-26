public class ArrayExamples {
    private static void printArray(int[] intArray) {
        for (int i = 0; i < intArray.length; i++) {
            System.out.println(intArray[i]);
        }
    }

    private static void changeAndReturn(int methodLocal) {
        methodLocal = 5;
        System.out.println("In changeAndReturn: " + methodLocal);
    }

    private static void changeAndReturn(int[] methodLocal) {
        methodLocal[0] = 5;
        System.out.println("In changeAndReturn: " + methodLocal[0]);
    }

    private static int[] returnArrayOfThree() {
        int[] arrayOfThree = {10, 9, 8};
        return arrayOfThree;
    }

    private static int returnArrayOfTwo() [] {
        int[] arrayOfTwo = {20, 21};
        return arrayOfTwo;
    }

    public static void main(String[] args) throws Exception {
        int[] arrayOfIntegers;
        arrayOfIntegers = new int[5];
        arrayOfIntegers[0] = 27;
        arrayOfIntegers[1] = -512;
        arrayOfIntegers[2] = 37;
        arrayOfIntegers[3] = 1009;
        arrayOfIntegers[4] = 1_000_000;
        System.out.println("[" +
                arrayOfIntegers[0] + ", " +
                arrayOfIntegers[1] + ", " +
                arrayOfIntegers[2] + ", " +
                arrayOfIntegers[3] + ", " +
                arrayOfIntegers[4] + "]");
        
        // System.out.println(arrayOfIntegers[10]);
        // System.out.println(arrayOfIntegers[-1]);

        System.out.println(arrayOfIntegers.length);
        printArray(arrayOfIntegers);

        int mainLocal = 10;
        changeAndReturn(mainLocal);
        System.out.println(mainLocal);
        
        int[] arrayOfOne = { 10 };
        changeAndReturn(arrayOfOne);
        System.out.println(arrayOfOne[0]);

        System.out.println(returnArrayOfThree()[1]);
        System.out.println(returnArrayOfTwo()[1]);
    }
}
