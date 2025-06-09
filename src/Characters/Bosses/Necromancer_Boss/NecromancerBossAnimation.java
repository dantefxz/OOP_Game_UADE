package Characters.Bosses.Necromancer_Boss;
import Characters.*;
import Utils.SpriteSheetLoader;

public class NecromancerBossAnimation extends CharacterAnimation {

    public NecromancerBossAnimation() {
        super(
                SpriteSheetLoader.loadSpriteSheet("/Assets/Images/Sprites/Bosses/Necromancer_Boss/Necromancer_Boss.png"),
                50,
                100
        );
    }

    // Si querés agregar métodos específicos para el necromancer, podés hacerlo acá
    public void playAttack() {
        playAnimation(1, false); // Por ejemplo: ataque en fila 1
    }

    public void playDamage() {
        playAnimation(2, false); // daño en fila 2, por ejemplo
    }

    public void playDeath() {
        playAnimation(3, false); // muerte en fila 3
    }
}
