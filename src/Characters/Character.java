package Characters;

import Misc.AttackManager;
import java.util.HashMap;
import java.util.Map;

public abstract class Character {

    String name;
    double maxHealth;
    double health;
    Map<String, AttackManager> attacksList = new HashMap<>();

    public Character(String name, double maxHealth){
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = this.maxHealth;
    }

    public String getName(){
        return this.name;
    }

    public double getHealth() {
        return this.health;
    }

    public void setHealth(double health){
        this.health = Math.min(health, this.maxHealth);
    }

    public void takeDamage(double damage){
        this.setHealth(this.health - damage);
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

    public void createAttack(String name, double damage, double healing, double criticRate, int turns){
        AttackManager NewAttack = new AttackManager(name, damage, healing, criticRate, turns);
        attacksList.put(name, NewAttack);
    }

    public abstract void checkAbility(String ability);
}
