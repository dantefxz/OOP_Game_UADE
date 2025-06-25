package Misc;

import Interfaces.IItems;

/**
 * Clase Items:
 * Representa un objeto consumible o utilizable en combate, que puede curar o hacer daño.
 * Implementa la interfaz IItems.
 */
public class Items implements IItems {
    String name;    // Nombre del objeto
    int healing;    // Cantidad de curación que proporciona (puede ser 0)
    int damage;     // Cantidad de daño que causa (puede ser 0)

    //Constructor de la clase Items
    public Items(String name, int healing, int damage){
        this.name = name;
        this.healing = healing;
        this.damage = damage;
    }

    public String getName(){
        return this.name;
    }

    public int getHealing(){
        return this.healing;
    }

    public int getDamage(){
        return this.damage;
    }

}
