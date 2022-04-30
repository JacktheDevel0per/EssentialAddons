package essentialaddons.mixins.essentialCarefulDrop;

import essentialaddons.EssentialSettings;
import essentialaddons.EssentialUtils;
import essentialaddons.utils.Subscription;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FurnaceMinecartEntity.class)
public abstract class FurnaceMinecartEntityMixin extends Entity {
	public FurnaceMinecartEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "dropItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/FurnaceMinecartEntity;dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;", shift = At.Shift.BEFORE), cancellable = true)
	private void onDropStack(DamageSource damageSource, CallbackInfo ci) {
		ci.cancel();
		if (EssentialSettings.essentialCarefulDrop && damageSource.getSource() instanceof ServerPlayerEntity player && (player.isInSneakingPose() || Subscription.ALWAYS_CAREFUL.hasPlayer(player))) {
			if (Subscription.ESSENTIAL_CAREFUL_DROP.hasPlayer(player) && EssentialUtils.placeItemInInventory(player, Items.FURNACE.getDefaultStack())) {
				return;
			}
		}
		this.dropItem(Items.FURNACE);
	}
}
