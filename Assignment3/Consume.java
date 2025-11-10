package Assignment3;

public class Consume implements Action {
    Consumable item;

    public Consume(Consumable item) {
        this.item = item;
    }

    @Override
    public void perform(Actor src, Actor trg) {
        if (item.uses > 0) {
            src.health += 20;
            item.uses--;
            System.out.println(src.name + " uses " + item.name + " and restores 20 HP!");
        } else {
            System.out.println(item.name + " is empty!");
        }
    }
}