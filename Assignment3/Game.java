package assignment3;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private Actor PC;
    private Actor NPC;
    private Attack attack;
    private Guard guard;
    private SpecialAttack spAttack;
    private int enemiesDefeated = 0;   // tracks how far the player gets
    private Random rand = new Random();

    public Game() {
        PC = new Actor("Hero", 100, 5, 50, 10);
        attack = new Attack();
        guard = new Guard();
        spAttack = new SpecialAttack();
    }

    // Return an Action based on menu choice (matches the UML: Action selectAction(int index))
    private Action selectAction(int index) {
        Action result = null;

        switch (index) {
            case 1:
                result = attack;
                break;
            case 2:
                result = guard;
                break;
            case 3:
                result = spAttack;
                break;
            default:
                break;
        }

        return result;
    }

    // Give an item every time an enemy is defeated
    private void giveReward() {
        int roll = rand.nextInt(3); // 0, 1, or 2

        switch (roll) {
            case 0:
                System.out.println("You found a Healing Potion!");
                PC.addItem(new Potion());
                break;
            case 1:
                System.out.println("You found a Bomb!");
                PC.addItem(new Bomb());
                break;
            case 2:
                System.out.println("You found a Rock!");
                PC.addItem(new Rock());
                break;
            default:
                break;
        }
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Battle Arena!");

        // Outer loop: keep spawning enemies until the hero dies
        while (PC.isAlive()) {
            // Make a new enemy, slightly stronger each time
            NPC = new Actor(
                    "Goblin " + (enemiesDefeated + 1),
                    60 + enemiesDefeated * 10, // more HP each round
                    3 + enemiesDefeated,       // more defense each round
                    30,
                    0
            );

            System.out.println("\nA wild " + NPC.getName() + " appears!");

            // Inner loop: fight this one enemy
            while (PC.isAlive() && NPC.isAlive()) {
                System.out.println("\nChoose action |"+PC.getHealth() +"HP|");
                System.out.println("1. Attack");
                System.out.println("2. Guard");
                System.out.println("3. Special Attack");
                System.out.println("4. Use Item");
                System.out.println("5. View Stats");

                int choice = sc.nextInt();

                // ---- OPTION 4: USE ITEM ----
                if (choice == 4) {
                    // If inventory is empty, don't waste the turn
                    if (PC.isEmpty()) {
                        System.out.println("You don't have any items to use!");
                        continue; // back to action menu, no enemy turn
                    }

                    // Show inventory before choosing
                    PC.printInventory();

                    System.out.println("\nChoose an item slot (1–5), or 0 to cancel:");
                    int slotNumber = sc.nextInt();

                    // allow cancel
                    if (slotNumber == 0) {
                        continue; // back to action menu, no enemy turn
                    }

                    int slotIndex = slotNumber - 1;   // 1–5 -> 0–4

                    Action itemAction = PC.useItem(slotIndex);
                    if (itemAction == null) {
                        // invalid or empty slot; don't let enemy hit for free
                        System.out.println("That slot is empty or invalid. Try again.");
                        continue; // back to action menu, no enemy turn
                    }

                    // If it's a Consumable (like Potion), target yourself; otherwise target the enemy
                    if (itemAction instanceof Consume) {
                        PC.selectTarget(PC);
                    } else {
                        PC.selectTarget(NPC);
                    }
                    PC.performAction(itemAction);

                    // Enemy turn after successfully using an item
                    if (NPC.isAlive()) {
                        NPC.selectTarget(PC);
                        NPC.performAction(attack);
                    }
                    continue; // jump to next round of the loop
                }

                // ---- OPTION 5: VIEW STATS ----
                if (choice == 5) {
                    PC.printStats();
                    continue; // just show stats, no enemy turn
                }

                // ---- OPTIONS 1–3 (normal actions) ----
                Action chosen = selectAction(choice);

                if (chosen != null) {
                    PC.selectTarget(NPC);
                    PC.performAction(chosen);
                } else {
                    System.out.println("Invalid choice!");
                }

                // Enemy turn if still alive
                if (NPC.isAlive()) {
                    NPC.selectTarget(PC);
                    NPC.performAction(attack);
                }
            }

            // If the hero died in this fight, stop the game
            if (!PC.isAlive()) {
                break;
            }

            // Otherwise, enemy is dead → increase counter, give reward
            enemiesDefeated++;
            System.out.println("You defeated " + NPC.getName() + "!");
            giveReward();
        }

        System.out.println("\nGame over! You defeated " + enemiesDefeated + " enemies.");
        sc.close();
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.start();
    }
}
