package com.disableend;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.gamerules.GameRule;

public class DisableEnd implements ModInitializer {
    public static final String MOD_ID = "disable_end";


    public static GameRule<Boolean> DISABLE_END;

    @Override
    public void onInitialize() {
        DISABLE_END = GameRuleBuilder.forBoolean(false)
                .buildAndRegister(Identifier.withDefaultNamespace("disable_end"));

        
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (level.getBlockState(hitResult.getBlockPos()).getBlock() instanceof EndPortalFrameBlock
                    && level instanceof ServerLevel serverLevel
                    && serverLevel.getGameRules().get(DisableEnd.DISABLE_END)
                    && player.getItemInHand(hand).is(Items.ENDER_EYE)) {
                return InteractionResult.FAIL;
            }
        
            return InteractionResult.PASS;
        });

    }

    
}