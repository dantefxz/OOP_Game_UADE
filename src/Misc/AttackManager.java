package Misc;

import Interfaces.IAttackManager;
import Characters.BaseCharacter;

/*
 Clase que gestiona ataques o habilidades de personajes.
 Permite tanto ataques ofensivos como curaciones, incluyendo probabilidad de crítico
 y lógica de cooldown entre usos.
 */

public class AttackManager implements IAttackManager {
    private final String name;          // Nombre del ataque/habilidad
    private final double damage;        // Cantidad base de daño
    private final double healing;       // Cantidad base de curación
    private final double criticRate;    // Probabilidad de crítico en porcentaje (0-100)
    private int turns;                  // Turnos de espera (cooldown) antes de poder usarlo nuevamente

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
        // Si es curación
        if (this.healing > 0){
            finalDamage = this.healing * -1; // Se usa negativo para que "takeDamage" cure
        } else {
            finalDamage = critic(this.damage, this.criticRate); // Aplica crítico si es ofensiva
        }
        // Permite a la clase del personaje ejecutar lógica adicional relacionada con la habilidad
        source.checkAbility(name);
        // Aplica el daño o curación al objetivo
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

    //Esta un turno de cooldown al contador. Nunca baja de 0.
    public void subtractTurn(){
        this.turns -= 1;
        if (this.turns < 0){
            this.turns = 0;
        }
    }
}
