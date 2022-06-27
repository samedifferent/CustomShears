package samebutdifferent.customshears.mixin;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Block.class)
public class BlockMixin {
    @Redirect(method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/context/LootContext$Builder;parameter(Lnet/minecraft/loot/context/LootContextParameter;Ljava/lang/Object;)Lnet/minecraft/loot/context/LootContext$Builder;"))
    private static <T> LootContext.Builder onGetDrops(LootContext.Builder instance, LootContextParameter<T> key, T value) {
        if (value instanceof ItemStack stack && stack.isIn(ConventionalItemTags.SHEARS)) {
            return instance.parameter(key, (T)new ItemStack(Items.SHEARS));
        }
        return instance.parameter(key, value);
    }
}
