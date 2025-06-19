package Characters.Bosses;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Knight extends BaseCharacter {
    public Knight() {
        super("Sir Malrik, Champion of the Light", 350);
        new AttackManager("Base_Atack", 25, 0, 0, 0);
        new AttackManager("Ability", 50, 0, 0, 4);
        new AttackManager("Ultimate", 70, 0, 0, 6);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
