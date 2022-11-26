package essentialaddons.commands;

import com.mojang.brigadier.CommandDispatcher;
import essentialaddons.EssentialSettings;
import essentialaddons.EssentialUtils;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

//#if MC >= 11900
import static carpet.utils.CommandHelper.canUseCommand;
//#else
// import static carpet.settings.SettingsManager.canUseCommand;
//#endif
import static net.minecraft.server.command.CommandManager.literal;

public class CommandNightVision {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("nightvision").requires((player) -> canUseCommand(player, EssentialSettings.commandNightVision))
            .executes(context -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayerOrThrow();
                if (!playerEntity.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                    playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 999999, 1, true, false));
                    EssentialUtils.sendToActionBar(playerEntity, "§6Night Vision has been §aenabled");
                }
                else {
                    playerEntity.removeStatusEffect(StatusEffects.NIGHT_VISION);
                    EssentialUtils.sendToActionBar(playerEntity, "§6Night Vision has been §cdisabled");
                }
                return 0;
            })
        );
    }
}
