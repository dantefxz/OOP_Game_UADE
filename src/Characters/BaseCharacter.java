package Characters;

import Interfaces.IBaseCharacter;
import Interfaz.AnimationPanel;
import Misc.AttackManager;
import java.util.HashMap;
import java.util.Map;
import Misc.Items;

// Clase abstracta base para todos los personajes del juego
public abstract class BaseCharacter implements IBaseCharacter {

    // Atributos básicos del personaje
    String name;
    double maxHealth;
    double health;
    boolean canAttack;
    Map<String, AttackManager> attacksList = new HashMap<>(); // Lista de ataques disponibles
    Map<String, Items> inventory = new HashMap<>(); // Inventario de objetos
    private AnimationPanel sprite; // Sprite asociado al personaje
    String characterType;
    int ID;

    // Constructor principal del personaje
    public BaseCharacter(String name, double maxHealth, String characterType, int ID){
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = this.maxHealth;
        this.characterType = characterType;
        this.ID = ID; // Solo para jefes sino se fija en 0
    }

    public String getName(){
        return this.name;
    }

    public double getHealth() {
        return this.health;
    }

    public void setHealth(double newHealth){
        // Limita la vida a un mínimo de 0 y un máximo de maxHealth
        this.health = Math.min(Math.max(newHealth, 0), this.maxHealth);
    }

    //Función utilizada al recibir daño de una fuente externa o se cura al personaje actual.
    public void takeDamage(double damage){
        this.setHealth(this.health - damage); // Aplica daño (o cura si negativo)
        if (this.getHealth() < 0) {
            this.setHealth(0);
        }
        if (damage < 0) { // Curación
            double finalHealing = damage * -1;
            double nuevaVida = this.getHealth() + (finalHealing);
            this.setHealth(nuevaVida);
        }
    }

    public Map<String, AttackManager> getAttacksList(){
        return attacksList;
    }

    //Función utilizada para crear y añadir la lista de ataques del personaje
    public void createAttack(String name, double damage, double healing, double criticRate, int turns){
        AttackManager NewAttack = new AttackManager(name, damage, healing, criticRate, turns);
        attacksList.put(name, NewAttack);
    }

    public abstract void checkAbility(String ability);

    //Función utilizada para crear y añadir items al inventario
    public void addItem(String name, int healing, int damage){
        Items newItem = new Items(name, healing, damage);
        inventory.put(name, newItem);
    }

    /*
     Utiliza un ítem en otro personaje (puede curar o dañar).
     Después de usarlo, se remueve del inventario.
     */
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

    // Asigna un sprite al personaje
    public void setSprite(AnimationPanel characterSprite){
        this.sprite = characterSprite;
    }

    // Devuelve el sprite del personaje
    public AnimationPanel getSprite(){
        return this.sprite;
    }

    // Establece si el personaje puede atacar o no
    public void setCanAttack(boolean value){
        this.canAttack = value;
    }

    // Devuelve si el personaje puede atacar en este turno
    public boolean getCanAttack(){
        return this.canAttack;
    }

    public String getType(){
        return this.characterType;
    }

    // Devuelve el ID (por ahora solo usado para jefes)
    public int getID(){
        return this.ID;
    }
}
