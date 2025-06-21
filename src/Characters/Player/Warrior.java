package Characters.Player;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Warrior extends BaseCharacter {
    
    public Warrior() {
        super("Warrior", 250);
        new AttackManager("Base_Atack", 25, 0, 50, 0);
        new AttackManager("Ability", 40, 0, 20, 3);
        new AttackManager("Ultimate", 50, 0, 50, 5);
    }
    @Override
    public void checkAbility(String ability) {

    }
    
}
