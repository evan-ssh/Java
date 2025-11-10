package assignment3;

public class Actor {
    String name;
    int health;
    int defense;
    int stamina;
    int skillPoints;
    Actor target;
    Item[] inventory;

    public Actor(String name, int health, int defense, int stamina, int skillPoints) {
        this.name = name;
        this.health = health;
        this.defense = defense;
        this.stamina = stamina;
        this.skillPoints = skillPoints;
        this.inventory = new Item[5];
    }

    public void selectTarget(Actor target) {
        this.target = target;
    }

    public void performAction(Action action) {
        if(target == null){
            System.out.println("No target selected.");
            return;
        }
        action.perform(this, target);
    }

    public Action useItem(int index) {
        if(index < 0 || index >= inventory.length || inventory[index] == null){
            System.out.println("Invalid item selection.");
            return null;
        }
        Item item = inventory[index];
        return item.getAction();
    }


}
