package  Interfaces;

import Characters.BaseCharacter;

public interface IBaseCharactrs {
    String getName();
    double getHealth();
    void setHealth(double newHealth);
    void takeDamage(double damage);
    void createAttack(String name, double damage, double healing, double criticRate, int turns);
    void checkAbility(String ability);
    void addItem(String name, int healing, int damage);
    void useItem(String name, BaseCharacter objective);
    Object getItems();
}