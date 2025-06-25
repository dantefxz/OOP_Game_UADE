package Characters.Player;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Cthulhu extends BaseCharacter {

    public Cthulhu() {
        super("Cthulhu", 300, "Player",0);
        this.createAttack("Attack", 50, 10, 15, 0);
        this.createAttack("SpecialAttack", 60, 15, 50, 2);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
