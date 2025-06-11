package Characters.Bosses;

import Characters.Character;
import Misc.AttackManager;

public class Necromancer extends Character {
    public Necromancer() {
        super("Mortuus, Master of the Undead" , 400);
        new AttackManager("Base_Atack", 40, 0, 0);
        new AttackManager("Ability", 50, 0,0);
        new AttackManager("Ultimate", 80, 0, 0);
    }
    
}
