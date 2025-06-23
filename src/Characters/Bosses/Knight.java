package Characters.Bosses;

import Characters.BaseCharacter;

public class Knight extends BaseCharacter {
    public Knight() {
        super("Sir Malrik, Champion of the Light", 350);
        this.createAttack("BaseAttack", 25, 0, 0, 0);
        this.createAttack("Ability", 50, 0, 0, 4);
        this.createAttack("Ultimate", 70, 0, 0, 6);
    }
    @Override
    public void checkAbility(String ability) {

    }
}
