package Characters.Bosses;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Demon extends BaseCharacter {
    public Demon() {
        super("Ashgore, The Last Ember of Wrath", 600, "Boss",4);
        this.createAttack("Attack", 50, 0, 5,0);
        this.createAttack("SpecialAttack", 60, 0, 5,6);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
