package Characters.Bosses;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Corrupted extends BaseCharacter {
    public Corrupted() {
        super("Sir Malrik, the Corrupted", 800, "Boss",5);
        this.createAttack("Attack", 30, 0, 25,0);
        this.createAttack("SpecialAttack", 60, 0, 25,5);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
