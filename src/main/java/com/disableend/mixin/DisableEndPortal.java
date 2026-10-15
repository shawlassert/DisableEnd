package com.disableend.mixin;

import com.disableend.DisableEnd;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EndPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndPortalBlock.class)
public class DisableEndPortal {
    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    private void noEnd(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise, CallbackInfo ci) {
        if (disableend$isDisabled(level)) {
            ci.cancel();
        }
    }

    @Unique
    private static boolean disableend$isDisabled(Level level) {
        return level instanceof ServerLevel serverLevel
                && serverLevel.getGameRules().get(DisableEnd.DISABLE_END);
    }
}