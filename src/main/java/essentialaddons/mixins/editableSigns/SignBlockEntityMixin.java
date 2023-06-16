package essentialaddons.mixins.editableSigns;

import essentialaddons.EssentialSettings;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlockEntity.class)
public class SignBlockEntityMixin {

    //#if MC < 12000
//$$    @Shadow
//$$    private boolean editable;
//$$
//$$    @Inject(method = "onActivate", at = @At("HEAD"))
//$$    private void onActivate(ServerPlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
//$$        if (EssentialSettings.editableSigns && player.getActiveHand() == Hand.MAIN_HAND && player.isSneaking()) {
//$$            this.editable = true;
//$$            player.openEditSignScreen((SignBlockEntity) (Object) this);
//$$        }
//$$    }

    //#endif
}
