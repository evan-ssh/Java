package assignment3;

public class Consume implements Action {
    private Consumable item;

    public Consume(Consumable item) { this.item = item; }

    @Override
    public void perform(Actor src, Actor trg) {
        if (item.getUses() <= 0) {
            System.out.println(src.getName() + " tried to use " + item.getName() + ", but it’s empty!");
            return;
        }
        item.uses--;
        src.restoreHealth(10);
        System.out.println(src.getName() + " consumes " + item.getName() + " and restores 10 HP!");
    }
}
