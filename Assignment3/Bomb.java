package assignment3;

public class Bomb extends ThrowableItem {

    public Bomb() {
        super("Bomb");
    }

    @Override
    public Action getAction() {
        return new Throw(this);
    }
}
