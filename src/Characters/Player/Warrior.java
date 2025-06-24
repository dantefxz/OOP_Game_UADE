package Characters.Player;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Warrior extends BaseCharacter {
    
    public Warrior() {
        super("Warrior", 250);
        this.createAttack("Attack", 25, 0, 50, 0);
        this.createAttack("SpecialAttack", 40, 0, 20, 3);
        this.createAttack("Ultimate", 50, 0, 50, 5);
    }

    @Override
    public void checkAbility(String ability) {

    }
    
}
