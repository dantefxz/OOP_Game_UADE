package Characters.Player;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Warrior extends BaseCharacter {
    
    public Warrior() {
        super("Warrior", 250, "Player",0);
        this.createAttack("Attack", 25, 0, 50, 0);
        this.createAttack("SpecialAttack", 60, 0, 50, 3);

    }

    @Override
    public void checkAbility(String ability) {

    }
    
}
