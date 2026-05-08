package savage.biggerenderchest.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestMenu.class)
public class ChestMenuMixin {

    /**
     * Forcefully redirects any Ender Chest menu creation to a 6-row GENERIC_9x6 menu.
     * This ensures the client and server always agree on the slot mapping.
     */
    @Inject(
        method = "threeRows(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;)Lnet/minecraft/world/inventory/ChestMenu;",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void bigger_ender_chest$forceSixRows(int i, Inventory inventory, Container container, CallbackInfoReturnable<ChestMenu> cir) {
        if (container instanceof PlayerEnderChestContainer) {
            // By calling the constructor directly with GENERIC_9x6, we guarantee the MenuType sent to the client is correct.
            cir.setReturnValue(new ChestMenu(MenuType.GENERIC_9x6, i, inventory, container, 6));
        }
    }
}
