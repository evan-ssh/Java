package assignment3;

public class SpecialAttack implements Action {
    @Override
    public void perform(Actor src, Actor trg) {
        if (src.getStamina() < 10) {
            System.out.println(src.getName() + " doesn't have enough stamina!");
            return;
        }
        src.useStamina(10);
        int damage = Math.max(1, 25 - trg.getDefense());
        trg.takeDamage(damage);
        System.out.println(src.getName() + " performs a special attack on " + trg.getName() + " for " + damage + " damage!");
    }
}