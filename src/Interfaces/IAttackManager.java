package Interfaces;
import Characters.BaseCharacter;

public interface IAttackManager {
    double critic(double damage, double ratePercentage);
    void execute(BaseCharacter source, BaseCharacter Enemy);
    
}
