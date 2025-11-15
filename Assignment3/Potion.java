package assignment3;

public class Potion extends Consumable {
    public Potion() {
        super("Healing Potion", 1);
    }

    @Override
    public Action getAction() {
        return new Consume();
    }
}