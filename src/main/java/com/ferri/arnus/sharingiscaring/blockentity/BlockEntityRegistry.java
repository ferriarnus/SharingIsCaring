package com.ferri.arnus.sharingiscaring.blockentity;

import com.ferri.arnus.sharingiscaring.SharingIsCaring;
import com.ferri.arnus.sharingiscaring.block.BlockRegistry;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SharingIsCaring.MODID);

    public static void register() {
        BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static RegistryObject<BlockEntityType<GiftBlockEntity>> GIFT = BLOCK_ENTITIES.register("gift", () -> BlockEntityType.Builder.of(GiftBlockEntity::new, BlockRegistry.GIFT.get()).build(null));
}
