package com.ferri.arnus.sharingiscaring;

import com.ferri.arnus.sharingiscaring.blockentity.GiftBlockEntity;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StopBreak {

    @SubscribeEvent
    static void noBreak(BlockEvent.BreakEvent event) {
        if (event.getLevel().getBlockEntity(event.getPos()) instanceof GiftBlockEntity gift) {
            if (!(gift.getOwner() == null || gift.getTarget() == null || event.getPlayer().getUUID().equals(gift.getOwner()) || event.getPlayer().getUUID().equals(gift.getTarget()) || event.getPlayer().getAbilities().instabuild)) {
                event.setCanceled(true);
            }
        }
    }
}
