package Characters;

import Misc.AttackManager;
import java.util.HashMap;
import java.util.Map;
import Items.Items;

public class Character {

    String name;
    double maxHealth;
    double health;
    boolean resistanceOn = false;
    Map<String, AttackManager> attacksList = new HashMap<>();
    Map<String, Items> Inventory = new HashMap<>();



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
        if (health < 0){
            this.health = 0;
        } else {
            this.health = Math.min(health, this.maxHealth);
        }

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

    public void createAttack(String name, double damage, double healing, double criticRate){
        AttackManager NewAttack = new AttackManager(name, damage, healing, criticRate);
        attacksList.put(name, NewAttack);
    }

    public void addItem(String name, int healing, int damage){
        Items newItem = new Items(name, healing, damage);
        Inventory.put(name, newItem);
    }

    public void useItem(String name, Character objective){ //faltaria hacer que el item al ser usado se borre
        for (String i : Inventory.keySet()){
            Items item = Inventory.get(i);
            if (i.equals(name)){
                if (item.getHealing() > 0){
                    double heal = item.getHealing() + objective.getHealth();
                    objective.setHealth(heal);
                }

                if (item.getDamage() > 0){
                    double newHealth = objective.getHealth() - item.getDamage();
                    objective.setHealth(newHealth);
                }
            }
        }
    }

}
