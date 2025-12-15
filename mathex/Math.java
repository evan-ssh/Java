package mathex;

public class Math {
    static void printMathSummary(int a, int b){
        System.out.println("Addition: " + (a + b));
        System.out.println("Difference: " + (a-b));
        System.out.println("Product: " + (a*b));
        System.out.println("Quotient: " + (a/b));
        System.out.println("Remainder: " + (a%b));
        double c = (double)a /b;
        System.out.println("Double exact: " + c);
    }
}
