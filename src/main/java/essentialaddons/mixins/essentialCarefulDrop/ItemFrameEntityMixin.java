package essentialaddons.mixins.essentialCarefulDrop;

import essentialaddons.EssentialUtils;
import essentialaddons.utils.Subscription;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemFrameEntity.class)
public abstract class ItemFrameEntityMixin extends AbstractDecorationEntity {
	protected ItemFrameEntityMixin(EntityType<? extends AbstractDecorationEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	protected abstract ItemStack getAsItemStack();

	@Redirect(method = "dropHeldStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/ItemFrameEntity;dropStack(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/ItemEntity;"))
	private ItemEntity onDropStacks(ItemFrameEntity instance, ItemStack itemStack) {
		return null;
	}

	@Inject(method = "dropHeldStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/ItemFrameEntity;dropStack(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/ItemEntity;", ordinal = 0))
	private void onDropStack0(Entity entity, boolean alwaysDrop, CallbackInfo ci) {
		ItemStack itemStack = this.getAsItemStack();
		if (!EssentialUtils.tryCareful(entity, Subscription.ESSENTIAL_CAREFUL_DROP, itemStack)) {
			this.dropStack(itemStack);
		}
	}

	@Inject(method = "dropHeldStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/ItemFrameEntity;dropStack(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/ItemEntity;", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
	private void onDropStack1(Entity entity, boolean alwaysDrop, CallbackInfo ci, ItemStack itemStack) {
		if (!EssentialUtils.tryCareful(entity, Subscription.ESSENTIAL_CAREFUL_DROP, itemStack)) {
			this.dropStack(itemStack);
		}
	}
}
