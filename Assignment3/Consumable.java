package Assignment3;

public abstract class Consumable extends Item {
    int uses;

    public Consumable(String name, int uses) {
        super(name);
        this.uses = uses;
    }
}