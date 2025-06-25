package Characters.Player;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Cthulhu extends BaseCharacter {

    public Cthulhu() {
        super("Cthulhu", 500, "Player",0);
        this.createAttack("Attack", 50, 0, 30, 0);
        this.createAttack("SpecialAttack", 60, 0, 50, 2);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
