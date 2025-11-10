package assignment3;
import java.util.Scanner;

public class Game {
    Actor PC;
    Actor NPC;
    Attack attack = new Attack();
    Guard guard = new Guard();
    SpecialAttack spAttack = new SpecialAttack();

    public Game() {
        PC = new Actor("Hero", 100, 5, 40, 0);
        NPC = new Actor("Goblin", 80, 3, 30, 0);

        // optional: give hero some items
        PC.inventory[0] = new Potion("Health Potion", 2);
        PC.inventory[1] = new Rock("Small Rock");

        PC.selectTarget(NPC);
        NPC.selectTarget(PC);
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

        while (PC.health > 0 && NPC.health > 0) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Attack  2. Guard  3. Special Attack  4. Use Potion  5. Throw Rock");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> PC.performAction(attack);
                case 2 -> PC.performAction(guard);
                case 3 -> PC.performAction(spAttack);
                case 4 -> {
                    Action usePotion = PC.useItem(0);
                    if (usePotion != null) usePotion.perform(PC, PC);
                }
                case 5 -> {
                    Action throwRock = PC.useItem(1);
                    if (throwRock != null) throwRock.perform(PC, NPC);
                }
                default -> System.out.println("Invalid choice.");
            }

            // enemy turn
            if (NPC.health > 0) {
                NPC.performAction(attack);
            }

            System.out.println("Hero HP: " + PC.health + " | Goblin HP: " + NPC.health);
        }

        System.out.println(PC.health > 0 ? "\nYou win!" : "\nYou lose!");
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
