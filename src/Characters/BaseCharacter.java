package Characters;

import Interfaz.AnimationPanel;
import Misc.AttackManager;
import java.util.HashMap;
import java.util.Map;
import Misc.Items;

import javax.swing.*;

public abstract class BaseCharacter{

    String name;
    double maxHealth;
    double health;
    boolean canAttack;
    Map<String, AttackManager> attacksList = new HashMap<>();
    Map<String, Items> inventory = new HashMap<>();
    private AnimationPanel sprite;

    public BaseCharacter(String name, double maxHealth){
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

    public void setHealth(double newHealth){
        this.health = Math.min(Math.max(newHealth, 0), this.maxHealth);
    }

    public void takeDamage(double damage){
        this.setHealth(this.health - damage);
        if (this.getHealth() < 0) {
            this.setHealth(0);
            System.out.println(this.getName() + " is dead");
        } else {
            System.out.println(this.getName() + "'s health is " + this.getHealth());
        }
        System.out.println(this.getName() + this.getHealth());
        if (damage < 0) { // Healing
            double finalHealing = damage * -1;
            double nuevaVida = this.getHealth() + (finalHealing);
            this.setHealth(nuevaVida);
            System.out.println(this.getName() + " se curó " + finalHealing + " de vida. Nueva vida: " + nuevaVida);
        }
    }

    public Map<String, AttackManager> getAttacksList(){
        return attacksList;
    }

    public void createAttack(String name, double damage, double healing, double criticRate, int turns){
        AttackManager NewAttack = new AttackManager(name, damage, healing, criticRate, turns);
        attacksList.put(name, NewAttack);
    }

    public abstract void checkAbility(String ability);

    public void addItem(String name, int healing, int damage){
        Items newItem = new Items(name, healing, damage);
        inventory.put(name, newItem);
    }

    public void useItem(String name, BaseCharacter objective){
        Items item = inventory.get(name);
        if (item.getHealing() > 0){
            double heal = item.getHealing() + objective.getHealth();
            objective.setHealth(heal);
        }

        if (item.getDamage() > 0){
            double newHealth = objective.getHealth() - item.getDamage();
            objective.setHealth(newHealth);
        }
        inventory.remove(name);
    }

    public Object getItems(){ // Items ordenados por ej: pocion: 3
        Map<String, Integer> orderedItems = new HashMap<>();
        
        for (String i : inventory.keySet()){
            if (orderedItems.get(i) != null){
                Integer amount = orderedItems.get(i);
                orderedItems.replace(i, amount + 1);
            }
        }
        return orderedItems;
    }

    public void setSprite(AnimationPanel characterSprite){
        this.sprite = characterSprite;
    }

    public AnimationPanel getSprite(){
        return this.sprite;
    }

    public void setCanAttack(boolean value){
        this.canAttack = value;
    }

    public boolean getCanAttack(){
        return this.canAttack;
    }
}
