package savage.biggerenderchest;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BiggerEnderChest implements ModInitializer {
	public static final String MOD_ID = "bigger-ender-chest";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Bigger Ender Chest initialized (26.1.x)");

		// Register the admin command: /bec edit <player>
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("bec")
				.requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
				.then(Commands.literal("edit")
					.then(Commands.argument("player", EntityArgument.player())
						.executes(context -> {
							ServerPlayer sourcePlayer = context.getSource().getPlayerOrException();
							ServerPlayer targetPlayer = EntityArgument.getPlayer(context, "player");

							// Open the target player's ender chest.
							// Our ChestMenuMixin will automatically detect the PlayerEnderChestContainer 
							// and provide a 6-row menu.
							sourcePlayer.openMenu(new SimpleMenuProvider((i, inventory, playerx) -> 
								ChestMenu.threeRows(i, inventory, targetPlayer.getEnderChestInventory()),
								targetPlayer.getDisplayName()
							));

							return 1;
						})
					)
				)
			);
		});
	}
}