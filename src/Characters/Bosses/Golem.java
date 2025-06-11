package Characters.Bosses;
import Characters.Character;
import Misc.AttackManager;

public class Golem extends Character {

    public Golem() {
        super("Malphite, the King of the Mountains", 800);
        new AttackManager("Base_Atack", 20, 0, 0, 0);
        new AttackManager("Ability", 0, 10,0, 4);
        new AttackManager("Ultimate", 30, 0, 0, 6);
    }

    @Override
    public void checkAbility(String ability) {

    }
}
