package Characters.Player;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Wizard extends BaseCharacter {

    public Wizard() {
        super("Wizard", 150);
        this.createAttack("Attack", 50, 0, 80, 0);
        this.createAttack("SpecialAttack", 100, 0, 75, 5);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
