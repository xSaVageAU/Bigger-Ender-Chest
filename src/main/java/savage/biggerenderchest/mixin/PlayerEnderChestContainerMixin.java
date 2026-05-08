package savage.biggerenderchest.mixin;

import net.minecraft.world.inventory.PlayerEnderChestContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerEnderChestContainer.class)
public class PlayerEnderChestContainerMixin {

    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 27))
    private static int modifyEnderChestSize(int original) {
        // We will make this configurable later, but for now let's set it to 54 (6 rows)
        return 54;
    }
}
