package Characters.Bosses;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Necromancer extends BaseCharacter {
    public Necromancer() {
        super("Mortuus, Master of the Undead" , 400);
        this.createAttack("Attack", 40, 0, 0, 0);
        this.createAttack("SpecialAttack", 80, 0, 50, 6);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
