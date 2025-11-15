package assignment3;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private Actor PC;
    private Actor NPC;
    private Attack attack;
    private Guard guard;
    private SpecialAttack spAttack;
    private int enemiesDefeated = 0;   
    private Random rand = new Random();
    private boolean firstRound = true; 

    public Game() {
        PC = new Actor("Hero", 100, 5, 50, 0);
        attack = new Attack();
        guard = new Guard();
        spAttack = new SpecialAttack();
    }

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

    private void giveReward() {
        if (PC.isEmpty()) {
            System.out.println("\nInventory is full! No item awarded.");
            return;
        }

        int roll = rand.nextInt(3); 

        switch (roll) {
            case 0:
                System.out.println("\nYou found a Healing Potion!");
                PC.addItem(new Potion());
                break;
            case 1:
                System.out.println("\nYou found a Bomb!");
                PC.addItem(new Bomb());
                break;
            case 2:
                System.out.println("\nYou found a Rock!");
                PC.addItem(new Rock());
                break;
            default:
                break;
        }
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Gulag!");

        while (PC.isAlive()) {
            NPC = new Actor(
                    "Wolf " + "LVL "+(enemiesDefeated + 1),
                    50 + enemiesDefeated * 5,
                    3 + enemiesDefeated,       
                    30,
                    0
            );

            System.out.println("\nA " + NPC.getName() + " appears!");
            firstRound = true; 

            while (PC.isAlive() && NPC.isAlive()) {
                System.out.println("\n<<====== Menu =========>>");
                    System.out.println("X Choose action |"+PC.getHealth() +"HP| X");
                System.out.println("X 1. Attack  \t\tX");
                System.out.println("X 2. Guard\t\tX");
                System.out.println("X 3. Special Attack \tX");
                System.out.println("X 4. Use Item\t\tX");
                System.out.println("X 5. View Stats \tX");
                System.out.println("<<=====================>>");

                int choice = sc.nextInt();

                if (choice == 4) {
                    if (PC.isEmpty()) {
                        System.out.println("You don't have any items to use!");
                        continue; 
                    }

                    PC.printInventory();

                    System.out.println("\nChoose an item slot (1/5)");
                    int slotNumber = sc.nextInt();

                    if (slotNumber == 0) {
                        continue;  
                    }

                    int slotIndex = slotNumber - 1; 

                    Action itemAction = PC.useItem(slotIndex);
                    if (itemAction == null) {
                        System.out.println("That slot is empty or invalid. Try again.");
                        continue; 
                    }

                    if (itemAction instanceof Consume) {
                        PC.selectTarget(PC);
                    } else {
                        PC.selectTarget(NPC);
                    }
                    PC.performAction(itemAction);

                    if (NPC.isAlive()) {
                        System.out.println();
                        NPC.selectTarget(PC);
                        NPC.performAction(attack);
                        System.out.println();
                        System.out.println(PC.getName() + " has " + PC.getHealth() + " HP.");
                    }

                    if (!firstRound) {
                        System.out.println();
                        PC.regenerateStamina(2);
                    }
                    continue; 
                }

                if (choice == 5) {
                    PC.printStats();
                    continue; 
                }

                Action chosen = selectAction(choice);

                if (chosen != null) {
                    PC.selectTarget(NPC);
                    PC.performAction(chosen);
                    System.out.println();
                    System.out.println(NPC.getName() + " has " + NPC.getHealth() + " HP.");
                } else {
                    System.out.println("Invalid choice!");
                }

                if (NPC.isAlive()) {
                    System.out.println();
                    NPC.selectTarget(PC);
                    NPC.performAction(attack);
                    System.out.println();
                    System.out.println(PC.getName() + " has " + PC.getHealth() + " HP.");
                }

                if (!firstRound) {
                    System.out.println();
                    PC.regenerateStamina(2);
                }
                firstRound = false; 
            }
    
            if (!PC.isAlive()) {
                break;
            }
            enemiesDefeated++;
            System.out.println("\nYou defeated " + NPC.getName() + "!");
            System.out.println();
            PC.gainSkillPoint();         
            System.out.println();
            PC.regenerateStamina(10);     
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