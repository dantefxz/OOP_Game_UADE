package Characters.Bosses;
import Characters.BaseCharacter;
import Misc.AttackManager;

public class Golem extends BaseCharacter {

    public Golem() {
        super("Malphite, the King of the Mountains", 800, "Boss",2);
        this.createAttack("Attack", 20, 0, 10, 0);
        this.createAttack("SpecialAttack", 30, 0,5, 4);
    }

    @Override
    public void checkAbility(String ability) {

    }
}
