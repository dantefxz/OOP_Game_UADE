package Misc;

import Interfaces.IAttackManager;
import Characters.Character;

// agregar interfaz
public class AttackManager implements IAttackManager {
    private String name;
    private double damage;
    private double healing;
    private double criticRate;

    public AttackManager(String name, double damage, double healing, double criticRate) {
        this.name = name;
        this.damage = damage;
        this.healing = healing;
        this.criticRate = criticRate;
    }

    @Override
    public double critic(double damage, double criticRate){
        if (Math.random() * 100 < criticRate) {
            damage *= 1.5;
            System.out.println("Critical hit!");
        }
        return damage;
    }

    @Override
    public void execute(Character Enemy) {
        double finalDamage = damage;
        if (healing > 0){
            finalDamage = healing * -1;
        } else {
            finalDamage = critic(this.damage, this.criticRate); // If attacking then critic
        }
        Enemy.takeDamage(finalDamage);
    }
}
