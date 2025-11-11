package assignment3;

public abstract class Consumable extends Item {
    protected int uses;

    public Consumable(String name, int uses) {
        super(name);
        this.uses = uses;
    }

    public int getUses() {
        return uses;
    }

    @Override
    public abstract Action getAction();
}
