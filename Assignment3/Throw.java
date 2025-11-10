package Assignment3;

public class Throw implements Action {
    Throwable item;

    public Throw(Throwable item) {
        this.item = item;
    }

    @Override
    public void perform(Actor src, Actor trg) {
        int damage = 5;
        trg.health -= damage;
        System.out.println(src.name + " throws " + item.name + " at " + trg.name + " for " + damage + " damage!");
    }
}
