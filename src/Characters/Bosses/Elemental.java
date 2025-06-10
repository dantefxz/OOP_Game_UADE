package Characters.Bosses;

import Characters.Character;
import Misc.AttackManager;

public class Elemental extends Character {
    public Elemental() {
        super("Elementor", 600);
        new AttackManager("Base_Atack", 50, 0, 0);
        new AttackManager("Ability", 0, 10,0);
        new AttackManager("Ultimate", 60, 0, 0);
    }
}
