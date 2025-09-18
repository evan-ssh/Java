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
        double lastResult = 0;
        Boolean running = true;
        
        do{
            String optionMenu = "Pick an option\nAdd\nSubtract\nMultiply\nDivide\nExponent\n";
            System.out.println(optionMenu);
            String option = sc.next();
            
            double a = 0;
            while(true){
                System.out.println("Enter first number (or 'ans'):");
                String inputA = sc.next();
                if(inputA.equals("ans")){
                    a = lastResult;
                    break;
                }
                try{
                    a = Double.parseDouble(inputA);
                    break;
                }catch(NumberFormatException e){
                    System.out.println("Invalid input. Please enter a number or 'ans' ");

                } 
            }
            double b = 0;
            while(true){
                System.out.println("Enter second number (or 'ans'):");
                String inputB = sc.next();
                if(inputB.equals("ans")){
                    b = lastResult;
                    break;
                }
                try{
                    b = Double.parseDouble(inputB);
                    break;
                }catch(NumberFormatException e){
                    System.out.println("Invalid input. Please enter a number or 'ans' ");
                }
            }

            double result = 0;
            boolean valid = true;

            switch (option) {
                case "add":
                    result = Calculator.add(a,b);
                    
                    break;
                case "subtract":
                    result = Calculator.sub(a,b);
                    break;
                case "multiply":
                    result = Calculator.mult(a, b);
                    break;
                case "divide":
                    result = Calculator.div(a,b);
                    break;
                case "exponent":
                    result = Calculator.exp(a,b);
                    break;
                default:
                    System.out.println("Invalid Command");
                    valid = false;

            }
            if(valid){
                System.out.println("Result = " + result);
                lastResult = result;
            }
        }while(running);
        sc.close();
        }
    }