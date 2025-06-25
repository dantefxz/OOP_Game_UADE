package Characters.Player;

import Misc.AttackManager;
import Characters.BaseCharacter;
import java.util.Map;
import java.util.HashMap;

public class Tank extends BaseCharacter {
    private boolean resistance = false;
    private int resistanceTurns = 0;
    Map<String, Object> customAbility = new HashMap<>();

    public Tank() {
        super("Tank", 400);
        this.createAttack("Attack", 20, 0, 15, 0);
        this.createAttack("SpecialAttack", 20, 0, 0, 5);
    }
    @Override
    public void checkAbility(String ability) {
        if (ability.equals("SpecialAttack")) {
            System.out.println("Escudo activado");
            activateResistance();
        }
    }

    private void activateResistance() {
        this.resistance = true;
        this.resistanceTurns = 3; // Dura 3 turnos
    }

    public boolean hasResistance() {
        return resistanceTurns > 0;
    }


    public void reduceResistanceTurns() {
        if (resistanceTurns > 0) {
            resistanceTurns--;
            if (resistanceTurns == 0) {
                resistance = false;
            }
        }
    }

    public boolean isResistant() {
        return resistance;
    }

}




