package Misc;

import Interfaces.IAttackManager;
import Characters.BaseCharacter;

// agregar interfaz
public class AttackManager implements IAttackManager {
    private final String name;
    private final double damage;
    private final double healing;
    private final double criticRate;
    private int turns;

    public AttackManager(String name, double damage, double healing, double criticRate, int turns) {
        this.name = name;
        this.damage = damage;
        this.healing = healing;
        this.criticRate = criticRate;
        this.turns = turns;
    }

    //Función utilizada para que el daño tenga una posibilidad de ser crítico
    @Override
    public double critic(double damage, double criticRate){
        if (Math.random() * 100 < criticRate) {
            damage *= 1.5;
        }
        return damage;
    }


    //Función utilizada para indicar si se cura al jugador o se daña al enemigo.
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

    //Función utilizada
    public void subtractTurn(){
        this.turns -= 1;
        if (this.turns < 0){
            this.turns = 0;
        }
    }
}
