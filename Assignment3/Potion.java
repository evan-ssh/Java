package assignment3;

public class Potion extends Consumable {
    public Potion(String name, int uses) {
        super(name, uses);
    }

    @Override
    public Action getAction() {
        return new Consume(this);
    }
}