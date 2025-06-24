package Misc;

import Interfaces.IAttackManager;
import Characters.BaseCharacter;

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
        }
        return damage;
    }

    @Override
    public void execute(BaseCharacter source, BaseCharacter Enemy) {
        double finalDamage = this.damage;
        if (this.healing > 0){
            finalDamage = this.healing * -1;
        } else {
            finalDamage = critic(this.damage, this.criticRate); // If attacking then critic
        }
        source.checkAbility(name);
        Enemy.takeDamage(finalDamage);
    }

    public String getName() {
        return this.name;
    }

    public double getDamage(){
        return this.damage;
    }

    public int getTurns(){
        return this.turns;
    }

    public void setTurns(int turn){
        this.turns = turn;
    }

    public int subtractTurns(){
        this.turns -= 1;
        if (this.turns < 0){
            this.turns = 0;
        }
        return this.turns;
    }
}
