package com.ferri.arnus.sharingiscaring.block;

import com.ferri.arnus.sharingiscaring.SharingIsCaring;
import com.ferri.arnus.sharingiscaring.item.GiftItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SharingIsCaring.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SharingIsCaring.MODID);

    public static void register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static RegistryObject<Gift> GIFT = BLOCKS.register("gift", () -> new Gift(BlockBehaviour.Properties.of(Material.WOOD)));
    public static RegistryObject<BlockItem> GIFT_ITEM = ITEMS.register("gift", () -> new GiftItem(GIFT.get(), new Item.Properties()));
}
