package Misc;

public class Items {
    String name;
    int healing;
    int damage;

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
