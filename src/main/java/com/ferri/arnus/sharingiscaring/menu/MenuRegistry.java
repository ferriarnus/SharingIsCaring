package com.ferri.arnus.sharingiscaring.menu;

import com.ferri.arnus.sharingiscaring.SharingIsCaring;
import com.ferri.arnus.sharingiscaring.block.Gift;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegistry {

    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, SharingIsCaring.MODID);

    public static void register() {
        MENUS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<MenuType<GiftMenu>> GIFT = MENUS.register("gift", () -> IForgeMenuType.create(GiftMenu::new));
}
