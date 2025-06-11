package Characters.Bosses;

import Characters.Character;
import Misc.AttackManager;

public class Corrupted extends Character {
    public Corrupted() {
        super("Sir Malrik, the Corrupted", 800);
        new AttackManager("Base_Atack", 30, 0, 50);
        new AttackManager("Ability", 30, 5,0);
        new AttackManager("Ultimate", 60, 0, 50);
    }
}
