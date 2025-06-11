package Misc;

import Interfaces.IAttackManager;
import Characters.Character;

// agregar interfaz
public class AttackManager implements IAttackManager {
    private String name;
    private double damage;
    private double healing;
    private double criticRate;
    private int turns;

    public AttackManager(String name, double damage, double healing, double criticRate, int turns) {
        this.name = name;
        this.damage = damage;
        this.healing = healing;
        this.criticRate = criticRate;
        this.turns = turns;
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
    public void execute(Character source, Character Enemy) {
        double finalDamage = this.damage;
        if (this.healing > 0){
            finalDamage = this.healing * -1;
        } else {
            finalDamage = critic(this.damage, this.criticRate); // If attacking then critic
        }
        source.checkAbility(name);
        Enemy.takeDamage(finalDamage);
    }
}
