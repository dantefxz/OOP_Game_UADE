package Characters.Bosses;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Demon extends BaseCharacter {
    public Demon() {
        super("Elementor, the Forbidden Fire", 600);
        new AttackManager("Base_Atack", 50, 0, 0,0);
        new AttackManager("Ability", 0, 10,0,4);
        new AttackManager("Ultimate", 60, 0, 0,6);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
