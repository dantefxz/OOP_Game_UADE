package Interfaces;
import Characters.Character;

public interface IAttackManager {
    double critic(double damage, double ratePercentage);
    void execute(Character Enemy);
}
