package Characters.Player;

import Characters.Character;
import Misc.AttackManager;

public class Guts extends Character {

    public Guts() {
        super("Guts", 300);
        new AttackManager("Base_Atack", 50, 10, 15);
        new AttackManager("Ability", 30, 20,20);
        new AttackManager("Ultimate", 60, 15, 50);
    }
}
