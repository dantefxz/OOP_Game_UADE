package Characters;

public class Character {

    String name;
    int health;
    int damage;
    int critic;

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

    public int takeDamage(int damage){
        this.health = health-damage;
        return health;
    }

    public int makeDamage(Character enemy, int damage){
        int enemyHealth = enemy.getHealth(); //Grab the enemy's health
        enemy.setHealth(enemyHealth-damage); // Set its health to its health - the damage it will take

        if (enemyHealth < 0){ // If the damage taken makes its health less than 0
            enemy.setHealth(0);
            System.out.println(enemy.getName()+ " is dead");
        } else { //If the damage doesn't kill the enemy
            System.out.println(enemy.getName()+ "'s health is " + enemyHealth);
        }
        return enemyHealth;
    }
}
