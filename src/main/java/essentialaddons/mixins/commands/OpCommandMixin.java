package essentialaddons.mixins.commands;

import com.mojang.brigadier.CommandDispatcher;
import essentialaddons.EssentialSettings;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.command.OpCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OpCommand.class)
public class OpCommandMixin {
    @Inject(method = "register", at = @At("HEAD"))
    private static void register(CommandDispatcher<ServerCommandSource> dispatcher, CallbackInfo info) {
        dispatcher.register(CommandManager.literal("op").requires((serverCommandSource) -> EssentialSettings.commandPublicOp || serverCommandSource.hasPermissionLevel(4)));
    }
}