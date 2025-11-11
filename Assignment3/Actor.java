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
    public int getSkillPoints() { return skillPoints; }
    public boolean isAlive() { return health > 0; }

    // NEW: guarding getter/setter
    public boolean isGuarding() {
        return guarding;
    }
    
    public void printStats() {
        System.out.println("\n=== " + name + " ===");
        System.out.println("Defense: " + defense);
        System.out.println("Stamina: " + stamina);
        System.out.println("Skill Points: " + skillPoints);
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
        if (index < 0 || index >= inventory.length || inventory[index] == null) {
            System.out.println("Invalid item selection.");
            return null;
        }
        return inventory[index].getAction();
    }
    
    public void printInventory() {
        System.out.println("\nInventory:");
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
                System.out.println(name + " obtained " + item.getName());
                return;
            }
        }
        System.out.println("Inventory full!");

    }

    public boolean isEmpty() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                return false; 
            }
        }
        return true; 
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
        System.out.println(name + " now has " + health + " HP.");
    }

    public void restoreHealth(int amount) {
        health += amount;
        System.out.println(name + " heals for " + amount + ", total HP: " + health);
    }

    public void boostDefense(int amount) { defense += amount; } 

    public void useSkillPoints(int amount) {
        skillPoints -= amount;
        if (skillPoints < 0) skillPoints = 0;
    }
}
