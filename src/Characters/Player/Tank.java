package Characters.Player;

import Misc.AttackManager;
import Characters.Character;
import java.util.Map;
import java.util.HashMap;

public class Tank extends Character {
    int Shield;
    private boolean resistanceOn = false;
    private int resistanceDuration = 0;
    Map<String, Object> customAbility = new HashMap<>();

    public Tank() {
        super("Tank", 400);
        new AttackManager("Base_Attack", 20, 0, 15, 0);
        new AttackManager("Heavy_Attack", 50, 0, 35, 3);
        new AttackManager("Ult_Resistance", 0, 20,0, 5);
    }

    private void increaseResistance(){
        this.resistanceOn = true;
        this.resistanceDuration = 3;
    }

    @Override
    public void takeDamage(double damage){
        if (resistanceOn) {
            double finalDamage = damage / 1.5;
            this.setHealth(this.getHealth() - finalDamage);
            resistanceDuration --;
            //Hacer un atributo resistanceTurns que se reste cada que se ejecute este codigo.
        }
        else{
            this.setHealth(this.getHealth() - damage);
        }
        if (this.getHealth() < 0) {
            this.setHealth(0);
            System.out.println(this.getName() + " is dead");
        } else {
            System.out.println(this.getName() + "'s health is " + this.getHealth());
        }

        if (damage < 0) { // Healing
            double finalHealing = damage * -1;
            double nuevaVida = this.getHealth() + (finalHealing);
            this.setHealth(nuevaVida);
            System.out.println(this.getName() + " se curó " + finalHealing + " de vida. Nueva vida: " + nuevaVida);
        }
    }

    @Override
    public void checkAbility(String ability){
        if (ability.equals("Ult_Resistance")){
            increaseResistance();
        }
    }

}




