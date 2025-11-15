
package assignment3;

public class Attack implements Action {
    @Override
    public void perform(Actor src, Actor trg) {
        int damage = Math.max(1, 15 - trg.getDefense());
        trg.takeDamage(damage);
        System.out.println(src.getName() + " attacks " + trg.getName() + " for " + damage + " damage!");
    
    }
}