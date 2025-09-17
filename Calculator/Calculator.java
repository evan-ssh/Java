package Calculator;
import java.util.Scanner;

public class Calculator {
    public static double add(double a, double b){
        return a + b;
    }
    public static double sub(double a ,double b){
        return a - b;
    }
    public static double mult(double a, double b){
        return a * b;
    }
    public static double div(double a, double b){
        return a / b;
    }
    public static double exp(double a, double b){
        return Math.pow(a,b);
    }


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        Boolean running = true;
        do{
            String optionMenu = "Pick an option\nadd\nsubtract\nmultiply\ndivide\nraise to power\n";
            System.out.println(optionMenu);
            String option = sc.next();
            System.out.println("Enter first number\s");
            double a = sc.nextInt();
            System.out.println("Enter second number\s");
            double b = sc.nextInt();
            double result = 0;
            boolean valid = true;
            switch (option) {
                case "add":
                    result = Calculator.add(a,b);
                    System.out.println("Result =" + result);
                    break;
                case "subtract":
                    result = Calculator.sub(a,b);
                    System.out.println("Result =" + result);
                    break;
                case "multiply":
                    result = Calculator.mult(a, b);
                    System.out.println("Result =" + result);
                case "divide":
                    result = Calculator.sub(a,b);
                    System.out.println("Result =" + result);
                case "exponent":
                    result = Calculator.exp(a,b);
                    System.out.println("Result =" + result);
            
                default:
                    System.out.println("Invalid Command");
                    valid = false;

            }
            if(valid){
                System.out.println("Result = " + result);
            }
        }while(running);
        sc.close();
        }
    }