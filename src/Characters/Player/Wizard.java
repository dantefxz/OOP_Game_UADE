package Characters.Player;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Wizard extends BaseCharacter {

    public Wizard() {
        super("Wizard", 200, "Player",0);
        this.createAttack("Attack", 60, 0, 80, 0);
        this.createAttack("SpecialAttack", 100, 0, 85, 5);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
