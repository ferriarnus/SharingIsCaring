package com.ferri.arnus.sharingiscaring;

import com.ferri.arnus.sharingiscaring.block.BlockRegistry;
import com.ferri.arnus.sharingiscaring.blockentity.BlockEntityRegistry;
import com.ferri.arnus.sharingiscaring.menu.MenuRegistry;
import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SharingIsCaring.MODID)
public class SharingIsCaring {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "sharingiscaring";

    public SharingIsCaring() {
        BlockRegistry.register();
        BlockEntityRegistry.register();
        MenuRegistry.register();
    }
}
