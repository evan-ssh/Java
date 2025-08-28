import java.util.Scanner;

public class GradeInput {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your grade");
        int grade = scanner.nextInt();
        if (grade >= 85 && grade <= 100){
            System.out.println("You received an 'A'");
        }else if(grade > 70 && grade < 85){
            System.out.println("You received a 'B'");
        }else if(grade >= 60 && grade <= 70){
            System.out.println("You received a 'C'");
        }else if(grade >= 50 && grade < 60){
            System.out.println("You received a 'D'");
        }else{
            System.out.println("Sorry, you failed");
        }
        scanner.close();
    }
}
    
