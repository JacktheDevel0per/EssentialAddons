package essentialaddons.mixins.lithium;

import essentialaddons.EssentialSettings;
import essentialaddons.EssentialUtils;
import me.jellysquid.mods.lithium.common.hopper.HopperHelper;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperHelper.class)
public class HopperHelperMixin {
    @Inject(method = "tryMoveSingleItem(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/inventory/SidedInventory;Lnet/minecraft/item/ItemStack;ILnet/minecraft/util/math/Direction;)Z", at = @At("HEAD"), cancellable = true)
    private static void onTryMoveItem(Inventory to, SidedInventory toSided, ItemStack transferStack, int targetSlot, Direction fromDirection, CallbackInfoReturnable<Boolean> cir) {
        if (EssentialSettings.stackableShulkersInPlayerInventories && EssentialUtils.isItemShulkerBox(to.getStack(targetSlot).getItem())) {
            cir.setReturnValue(false);
        }
    }
}
