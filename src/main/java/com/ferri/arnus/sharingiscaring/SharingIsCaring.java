package com.ferri.arnus.sharingiscaring;

import com.ferri.arnus.sharingiscaring.block.BlockRegistry;
import com.ferri.arnus.sharingiscaring.blockentity.BlockEntityRegistry;
import com.ferri.arnus.sharingiscaring.config.SharingConfig;
import com.ferri.arnus.sharingiscaring.menu.MenuRegistry;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(SharingIsCaring.MODID)
public class SharingIsCaring {
    public static final String MODID = "sharingiscaring";

    public SharingIsCaring() {
        BlockRegistry.register();
        BlockEntityRegistry.register();
        MenuRegistry.register();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SharingConfig.SPEC);
    }
}
