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
        if (event.getLeft().is(BlockRegistry.GIFT_ITEM.get()) && event.getName() != null && !event.getName().isEmpty()) {
            List<? extends Player> players = event.getPlayer().level.players();
            for (Player player: players) {
                if (player.getDisplayName().getString().equals(event.getName())) {
                    ItemStack gift = event.getLeft().copy();
                    CompoundTag tag = new CompoundTag();
                    tag.putUUID("target", player.getUUID());
                    gift.getOrCreateTag().put("BlockEntityTag", tag);
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
