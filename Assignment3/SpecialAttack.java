package assignment3;

public class SpecialAttack implements Action {
    @Override
    public void perform(Actor src, Actor trg) {
        if (src.getSkillPoints() < 5) {
            System.out.println(src.getName() + " doesn’t have enough skill points!");
            return;
        }
        src.useSkillPoints(5);
        int damage = Math.max(0, 25 - trg.getDefense());
        trg.takeDamage(damage);
        System.out.println(src.getName() + " performs a special attack on " + trg.getName() + " for " + damage + " damage!");
    }
}
