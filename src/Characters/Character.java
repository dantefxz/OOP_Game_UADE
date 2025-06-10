package Characters;

import Misc.AttackManager;

import java.util.HashMap;
import java.util.Map;

public class Character {

    String name;
    double maxHealth;
    double health;
    double damage;
    double critic;
    boolean resistanceOn = false;
    Map<String, Object> attacksList = new HashMap<>();

    public Character(String name, double maxHealth, double damage, double critic){
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = this.maxHealth;
        this.damage = damage;
        this.critic = critic;
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
        if (resistanceOn) {
            double finalDamage = damage / 1.5;
            this.setHealth(this.health - finalDamage);
            //Hacer un atributo resistanceTurns que se reste cada que se ejecute este codigo.
        }
        else{
            this.setHealth(this.health - damage);
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

    public void createAttack(String name, double damage, double healing, double criticRate){
        AttackManager NewAttack = new AttackManager(name, damage, healing, criticRate);
        attacksList.put(name, NewAttack);
    }
}
