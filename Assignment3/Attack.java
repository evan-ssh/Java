package assignment3;

public class Attack implements Action {
    @Override
    public void perform(Actor src, Actor trg) {
        int baseDamage = 10;
        int damage = baseDamage - trg.getDefense();

        if (damage < 1) {
            damage = 1; // always at least 1 damage
        }

        // If the target is guarding, reduce THIS hit only
        if (trg.isGuarding()) {
            damage = damage / 2;      // halve the damage
            if (damage < 1) {
                damage = 1;
            }
            trg.setGuarding(false);   // guard is consumed after this hit
            System.out.println(trg.getName() + " guards and reduces the damage!");
        }

        trg.takeDamage(damage);
        System.out.println(src.getName() + " attacks " + trg.getName() + " for " + damage + " damage!");
    }
}
