package Characters.Player;

import Misc.AttackManager;
import Characters.Character;

public class Tank extends Character {
    int Shield;
    private boolean resistanceOn = false;
    private int resistanceDuration = 0;

    public Tank() {
        super("Tank", 400);
        new AttackManager("Base_Attack", 20, 0, 15);
        new AttackManager("Heavy_Attack", 50, 0, 35);
        new AttackManager("Ult_Resistance", 0,20,0);
    }

    public void increaseResistance(){
        this.resistanceOn = true;
        this.resistanceDuration = 3;
    }

}




