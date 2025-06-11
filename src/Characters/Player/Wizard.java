package Characters.Player;

import Characters.Character;
import Misc.AttackManager;

public class Wizard extends Character {

    public Wizard() {
        super("Wizard", 150);
        new AttackManager("Base_Atack", 50, 0, 80);
        new AttackManager("Ability", 70, 0, 40);
        new AttackManager("Ultimate", 100, 0, 75);
    }
}
