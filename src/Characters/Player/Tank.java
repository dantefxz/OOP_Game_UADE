package Characters.Player;

import Characters.BaseCharacter;

public class Tank extends BaseCharacter {
    private boolean resistance = false;
    private int resistanceTurns = 0;

    public Tank() {
        super("Tank", 400, "Player",0);
        this.createAttack("Attack", 20, 0, 15, 0);
        this.createAttack("SpecialAttack", 20, 0, 0, 5);
    }

    //Función que al momento de ejecutar una animación, chequea si la habilidad es la indicada y activa la resistencia.
    @Override
    public void checkAbility(String ability) {
        if (ability.equals("SpecialAttack")) {
            System.out.println("Escudo activado");
            activateResistance();
        }
    }

    //Función utilizada para indicar que se activa
    private void activateResistance() {
        this.resistance = true;
        this.resistanceTurns = 3; // Dura 3 turnos
    }

    //Función que devuelve si aun queda resistencia
    public boolean hasResistance() {
        return resistanceTurns > 0;
    }

    //Función utilizada en cada ronda para reducir la cantidad de turnos restantes para que se acabe la resistencia.
    public void reduceResistanceTurns() {
        if (resistanceTurns > 0) {
            resistanceTurns--;
            if (resistanceTurns == 0) {
                resistance = false;
            }
        }
    }

    //Función similar a takeDamage de BaseCharacter, pero este toma en cuenta la resistencia.
    @Override
    public void takeDamage(double damage){
        if (damage > 0 && hasResistance()) {
            damage = damage / 1.5; // Reduce el daño si tiene escudo
            reduceResistanceTurns();
        }

        this.setHealth(getHealth() - damage);

        if (this.getHealth() < 0) {
            this.setHealth(0);
        }

        if (damage < 0) { // Curación
            double finalHealing = damage * -1;
            double nuevaVida = this.getHealth() + finalHealing;
            this.setHealth(nuevaVida);
        }
    }
}




