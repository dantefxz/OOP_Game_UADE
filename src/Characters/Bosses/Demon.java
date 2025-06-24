package Characters.Bosses;

import Characters.BaseCharacter;
import Misc.AttackManager;

public class Demon extends BaseCharacter {
    public Demon() {
        super("Ashgore, The Last Ember of Wrath", 600);
        this.createAttack("Attack", 50, 0, 0,0);
        this.createAttack("Ability", 0, 10,0,4);
        this.createAttack("Ultimate", 60, 0, 0,6);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
