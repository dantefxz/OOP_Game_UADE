package Characters.Bosses;

import Characters.Character;
import Misc.AttackManager;

public class Knight extends Character {
    public Knight() {
        super("ゴンサイ", 350);
        new AttackManager("Base_Atack", 25, 0, 0);
        new AttackManager("Ability", 50, 0, 0);
        new AttackManager("Ultimate", 70, 0, 0);
    }
}
