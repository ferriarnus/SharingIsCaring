package com.ferri.arnus.sharingiscaring;

import com.ferri.arnus.sharingiscaring.menu.MenuRegistry;
import com.ferri.arnus.sharingiscaring.screen.GiftScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = SharingIsCaring.MODID)
public class ClientSetup {

    @SubscribeEvent
    static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(MenuRegistry.GIFT.get(), GiftScreen::new));
    }
}
