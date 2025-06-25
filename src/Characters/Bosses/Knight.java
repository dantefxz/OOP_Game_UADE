package Characters.Bosses;

import Characters.BaseCharacter;

public class Knight extends BaseCharacter {
    public Knight() {
        super("Sir Malrik, Champion of the Light", 350, "Boss",1);
        this.createAttack("Attack", 25, 0, 0, 0);
        this.createAttack("SpecialAttack", 50, 0, 0, 4);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
