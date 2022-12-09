package com.ferri.arnus.sharingiscaring;

import com.ferri.arnus.sharingiscaring.block.BlockRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenameGift {

    @SubscribeEvent
    static void rename(AnvilUpdateEvent event) {
        boolean valid = false;
        ItemStack gift = event.getLeft().copy();
        if (gift.getOrCreateTag().contains("BlockEntityTag") ) {
            if (gift.getOrCreateTag().getCompound("BlockEntityTag").contains("owner")) {
                if (!gift.getOrCreateTag().getCompound("BlockEntityTag").getUUID("owner").equals(event.getPlayer().getUUID())) {
                    event.setCanceled(true);
                    return;
                }
            }
        }
        if (event.getLeft().is(BlockRegistry.GIFT_ITEM.get()) && event.getName() != null && !event.getName().isEmpty()) {
            List<? extends Player> players = event.getPlayer().level.players();
            for (Player player: players) {
                if (player.getDisplayName().getString().equals(event.getName()) && !event.getPlayer().getUUID().equals(player.getUUID())) {
                    if (gift.getOrCreateTag().contains("BlockEntityTag")) {
                        gift.getOrCreateTag().getCompound("BlockEntityTag").putUUID("target", player.getUUID());
                    } else {
                        CompoundTag tag = new CompoundTag();
                        tag.putUUID("target", player.getUUID());
                        gift.getOrCreateTag().put("BlockEntityTag", tag);
                    }
                    event.setOutput(gift);
                    event.setCost(1);
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                event.setCanceled(true);
            }
        }
    }
}
