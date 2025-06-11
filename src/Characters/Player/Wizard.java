package Characters.Player;

import Characters.Character;
import Misc.AttackManager;

public class Wizard extends Character {

    public Wizard() {
        super("Wizard", 150);
        new AttackManager("Base_Atack", 50, 0, 80, 0);
        new AttackManager("Ability", 70, 0, 40, 3);
        new AttackManager("Ultimate", 100, 0, 75, 5);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
