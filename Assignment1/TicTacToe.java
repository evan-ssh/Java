package Assignment1;

import java.util.Scanner;

public class TicTacToe {
    private static String swapPlayer(String currentPlayer) {
        // TODO: 
        //      make this method return "O" if currentPlayer is "X"
        //      and return "X" if currentPlayer is "O"
        if(currentPlayer.equals("X")){
            currentPlayer = "O";
        }else{
            currentPlayer = "X";
        }
        return currentPlayer;
    }

    private static boolean canPlayInCell(String cell) {
        // TODO: 
        //      make this method return "true" if cell is " "

        if(cell.equals(" ")){
            return true;
        }
        return false;
        
        
    }

    private static boolean isALine(String a, String b, String c) {
        // TODO: 
        //      make this method return "true" if: 
        //          a and b are the same, and;
        //          a and c are the same, and;
        //          a is not " "

        if(a.equals(b) && a.equals(c) && !(a.equals(" "))){
            return true;
        }else{
            return false;
        }


        
    }
    public static void main(String[] args) throws Exception {
        String topLeft = " ";
        String topMiddle = " ";
        String topRight = " ";
        String middleLeft = " ";
        String center = " ";
        String middleRight = " ";
        String bottomLeft = " ";
        String bottomMiddle = " ";
        String bottomRight = " ";
        Scanner sc = new Scanner(System.in);
        String player = "X";
        while (true) {
            System.out.println("Cell names are: tl, tm, tr, ml, c, mr, bl, bm, and br.");
            System.out.println(topLeft + "|" + topMiddle + "|" + topRight);
            System.out.println("-+-+-");
            System.out.println(middleLeft + "|" + center + "|" + middleRight);
            System.out.println("-+-+-");
            System.out.println(bottomLeft + "|" + bottomMiddle + "|" + bottomRight);
            System.out.print("Player " + player + " please enter the cell you'd like to play in or 'exit' to forfeit: ");
            String input = sc.next();
            if (input.toLowerCase().equals("exit")) {
                System.out.println("Player " + player + " has forfeit the game.");
                break;
            }
            /* TODO:    
                    Each case in the switch block below should use 
                    the "canPlayInCell(String cell)" method to check
                    if the cell is valid. 
                    
                    If not, it should print a message to
                    the player that tells them the error they made
                    and then use the "continue" keyword to allow
                    them to try again.
            
                    If it is valid, then it should assign the cell
                    to that players symbol (which is stored in the 
                    "player" variable).
             */
            
            switch (input) {
                case "tl":
                    
                    if(canPlayInCell(topLeft)){
                        System.out.println("Valid Move: True");
                        topLeft = player;
                        break;
                    }else{
                        System.out.println("Valid Move: False");
                        System.out.println("Invalid move cell taken or invalid input");
                        continue;
                    }
                   
                case "tm":
                     if(canPlayInCell(topMiddle)){
                        System.out.println("Valid Move: True");
                        topMiddle = player;
                        break;
                    }else{
                        System.out.println("Valid Move: False");
                        System.out.println("Invalid move cell taken or invalid input");
                        continue;
                    }
                case "tr":
                    
                     if(canPlayInCell(topRight)){
                        System.out.println("Valid Move: True");
                        topRight = player;
                        break;
                    }else{
                        System.out.println("Valid Move: False");
                        System.out.println("Invalid move cell taken or invalid input");
                        continue;
                    }
                case "ml":
                    
                     if(canPlayInCell(middleLeft)){
                        System.out.println("Valid Move: True");
                        middleLeft = player;
                        break;
                    }else{
                        System.out.println("Valid Move: False");
                        System.out.println("Invalid move cell taken or invalid input");
                        continue;
                    }
                case "c":
                    
                     if(canPlayInCell(center)){
                        System.out.println("Valid Move: True");
                        center = player;
                        break;
                    }else{
                        System.out.println("Valid Move: False");
                        System.out.println("Invalid move cell taken or invalid input");
                        continue;
                    }
                case "mr":
                     if(canPlayInCell(middleRight)){
                        System.out.println("Valid Move: True");
                        middleRight = player;
                        break;
                    }else{
                        System.out.println("Valid Move: False");
                        System.out.println("Invalid move cell taken or invalid input");
                        continue;
                    }
                    
                case "bl":
                     if(canPlayInCell(bottomLeft)){
                        System.out.println("Valid Move: True");
                        bottomLeft = player;
                        break;
                    }else{
                        System.out.println("Valid Move: False");
                        System.out.println("Invalid move cell taken or invalid input");
                        continue;
                    }
                case "bm":  
                     if(canPlayInCell(bottomMiddle)){
                        System.out.println("Valid Move: True");
                        bottomMiddle = player;
                        break;
                    }else{
                        System.out.println("Valid Move: False");
                        System.out.println("Invalid move cell taken or invalid input");
                        continue;
                    }
                case "br":
                     if(canPlayInCell(bottomRight)){
                        System.out.println("Valid Move: True");
                        bottomRight = player;
                        break;
                    }else{
                        System.out.println("Valid Move: False");
                        System.out.println("Invalid move cell taken or invalid input");
                        continue;
                    }
                default:
                    System.out.println("Invalid cell ID (" + input + ") please try again.");
                    continue;
}                   // TODO:    
                    //      Use a single expression to calculate whether their is a winner or not.
                    //      Replace the "false" with this expression. Use the method 
                    //      "isALine(String a, String b, String c)"" and the "||" operator
            boolean winner = isALine(topLeft, topMiddle, topRight ) ||
                             isALine(middleLeft, center, middleRight) ||
                             isALine(bottomLeft, bottomMiddle, bottomRight) || //Row wincases 
                             isALine(topLeft, middleLeft, bottomLeft) ||
                             isALine(topMiddle, center, bottomMiddle) ||
                             isALine(topRight, middleRight, bottomRight) || //Col wincases
                             isALine(topLeft, center, bottomRight) ||
                             isALine(topRight, center, bottomLeft);//Diag wincases
            if (winner) {
                System.out.println("Player " + player + " wins!");
                System.out.println(topLeft + "|" + topMiddle + "|" + topRight);
                System.out.println("-+-+-");
                System.out.println(middleLeft + "|" + center + "|" + middleRight);
                System.out.println("-+-+-");
                System.out.println(bottomLeft + "|" + bottomMiddle + "|" + bottomRight);
                break;
            }
            player = swapPlayer(player);
        }
        sc.close();
    }
}
