package Characters.Player;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Dante extends BaseCharacter {

    public Dante() {
        super("Dante", 300);
        new AttackManager("Base_Atack", 50, 10, 15, 0);
        new AttackManager("Ability", 30, 20,20, 1);
        new AttackManager("Ultimate", 60, 15, 50, 2);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
