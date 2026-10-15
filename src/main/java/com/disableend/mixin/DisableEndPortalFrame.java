
package com.disableend.mixin;

import com.disableend.DisableEnd;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public abstract class DisableEndPortalFrame {
    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void disableEnd$preventEyeInsertion(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit,
            CallbackInfoReturnable<InteractionResult> cir) {

            if (state.getBlock() instanceof EndPortalFrameBlock
                    && level instanceof ServerLevel serverLevel
                    && serverLevel.getGameRules().get(DisableEnd.DISABLE_END)
                    && stack.is(Items.ENDER_EYE)) {
                cir.setReturnValue(InteractionResult.FAIL);
            }
    }
}
