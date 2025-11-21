package assignment3;

public class Actor {
    protected  String name;
    protected  int health;
    protected  int defense;
    protected  int stamina;
    protected  int experience;
    private Actor target;
    private Item[] inventory;
    private boolean guarding;

    public Actor(String name, int health, int defense, int stamina, int skillPoints) {
        this.name = name;
        this.health = health;
        this.defense = defense;
        this.stamina = stamina;
        this.experience = experience;
        this.inventory = new Item[5];
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getDefense() { return defense; }
    public int getStamina() { return stamina; }
    public int getExperience() { return experience; }
    public boolean isAlive() { return health > 0; }
    public boolean isGuarding() {
        return guarding;
    }
    
    public void printStats() {
        System.out.println("\n<<====== Stats =========>>");
        System.out.println("Defense: " + defense);
        System.out.println("Stamina: " + stamina);
        System.out.println("Experience: " + experience);
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

    
    //Inventory
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


    //Health
    public void takeDamage(int amount) {
        if (guarding) {
            int originalAmount = amount;
            amount = amount / 3; // Reduces damage  1/3
            guarding = false; 
            System.out.println(name + " blocks some damage! (" + originalAmount + " reduced to " + amount + ")");
        }
        
        health -= amount;
        if (health < 0){
            health = 0;
        }
    }

    public void restoreHealth(int amount) {
        health += amount;
        System.out.println(name + " heals for " + amount + ", total HP: " + health);
    }
    //

    //Defense 
    public void boostDefense(int amount) { defense += amount; } 


    //Stamina
    public void useStamina(int amount) {
        stamina -= amount;
    }

    public void regenerateStamina(int amount) {
        stamina += amount;
        System.out.println("You recover " + amount + " stamina. Total: " + stamina);
    }


    //Experience
    public void gainExperience(int exp) {
        experience += exp;
        System.out.println("You gained experience! Total: " + experience);
    
    }
}
