package assignment3;

public class Actor {
    private String name;
    private int health;
    private int defense;
    private int stamina;
    private int skillPoints;
    private Actor target;
    private Item[] inventory;
    private boolean guarding;

    public Actor(String name, int health, int defense, int stamina, int skillPoints) {
        this.name = name;
        this.health = health;
        this.defense = defense;
        this.stamina = stamina;
        this.skillPoints = skillPoints;
        this.inventory = new Item[5];
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getDefense() { return defense; }
    public int getStamina() { return stamina; }
    public int getSkillPoints() { return skillPoints; }
    public boolean isAlive() { return health > 0; }
    public boolean isGuarding() {
        return guarding;
    }
    
    public void printStats() {
        System.out.println("\n<<====== Stats =========>>");
        System.out.println("Defense: " + defense);
        System.out.println("Stamina: " + stamina);
        System.out.println("Skill Points: " + skillPoints);
        System.out.println("<<======================>>");


    }

    
    public void setGuarding(boolean guarding) {
        this.guarding = guarding;
    }

    public void selectTarget(Actor target) { this.target = target; }

    public void performAction(Action action) {
        if (target == null) {
            System.out.println("No target selected.");
            return;
        }
        action.perform(this, target);
    }

    public Action useItem(int index) {
        if (index >= 0 && index < inventory.length && inventory[index] != null) {
            Action action = inventory[index].getAction();
            
            if (inventory[index] instanceof Consumable) {
                inventory[index] = null;
            }
            
            if (inventory[index] instanceof ThrowableItem) {
                inventory[index] = null; 
            }
            
            return action;
        }
        return null;
    }
    
    public void printInventory() {
        System.out.println("\nInventory (0 to cancel):");
        for (int i = 0; i < inventory.length; i++) {
            int displayIndex = i + 1; 
            if (inventory[i] != null) {
                System.out.println(displayIndex + ": " + inventory[i].getName());
            } else {
                System.out.println(displayIndex + ": [empty]");
            }
        }
    }

    public void addItem(Item item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = item;
                return;
            }
        }
        System.out.println("Inventory full!");

    }

    public boolean isEmpty() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                return false; 
            }
        }
        return true;
    }

    public void takeDamage(int amount) {
        if (guarding) {
            int originalAmount = amount;
            amount = amount / 2; 
            guarding = false; 
            System.out.println(name + " blocks some damage! (" + originalAmount + " reduced to " + amount + ")");
        }
        health -= amount;
        if (health < 0) health = 0;
    }

    public void useStamina(int amount) {
        stamina -= amount;
    }

    public void restoreHealth(int amount) {
        health += amount;
        System.out.println(name + " heals for " + amount + ", total HP: " + health);
    }

    public void boostDefense(int amount) { defense += amount; } 

    public void regenerateStamina(int amount) {
        stamina += amount;
        System.out.println("You recover " + amount + " stamina. Total: " + stamina);
    }

    public void gainSkillPoint() {
        skillPoints++;
        System.out.println("You gained a skill point! Total: " + skillPoints);
    
    }
}
