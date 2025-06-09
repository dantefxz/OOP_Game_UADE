package Characters;

public class Character {

    String name;
    int health;
    int damage;
    int critic;
    boolean resistanceOn = false;


    public Character(String name, int health, int damage, int critic){
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.critic = critic;
    }

    public String getName(){
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int takeDamage(int damage, boolean resistanceOn){
        if (resistanceOn) {
            double damageReduce = damage / 1.5;
            this.health = health-damage;
            //Hacer un atributo resistanceTurns que se reste cada que se ejecute este codigo.
        }
        else{
            this.health = health-damage;
        }

        return health;
    }

    public int critic(int damage){
        if (Math.random() * 100 < critic) {
            damage *= 1.5;
            System.out.println("Critical hit!");
        }
        return damage;
    }

    public int makeDamage(Character character, int damage, boolean resistanceOn){
        damage = critic(damage);
        character.takeDamage(damage, resistanceOn); // Set its health to its health - the damage it will take

        if (character.getHealth() < 0){ // If the damage taken makes its health less than 0
            character.setHealth(0);
            System.out.println(character.getName()+ " is dead");
        } else { //If the damage doesn't kill the character
            System.out.println(character.getName()+ "'s health is " + character.getHealth());
        }
        return character.getHealth();
    }
}
