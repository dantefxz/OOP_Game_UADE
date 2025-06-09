package Characters.Player;

import Characters.Character;

public class Tank extends Character {
    int Shield;
    private boolean resistanceOn = false;
    private int resistanceDuration = 0;

    public Tank(String name, int health, int damage, int critic) {
        super(name, health, damage, critic);

    }

    public void increaseResistance(){
        this.resistanceOn = true;
        this.resistanceDuration = 3;
    }

}




