package com.ferri.arnus.sharingiscaring.block;

import com.ferri.arnus.sharingiscaring.blockentity.BlockEntityRegistry;
import com.ferri.arnus.sharingiscaring.blockentity.GiftBlockEntity;
import com.ferri.arnus.sharingiscaring.menu.GiftMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class Gift extends Block implements EntityBlock {

    public Gift(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return BlockEntityRegistry.GIFT.get().create(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof GiftBlockEntity gift) {
                if (!pPlayer.getUUID().equals(gift.getOwner()) || !pPlayer.getUUID().equals(gift.getTarget())) {
                    return InteractionResult.FAIL;
                }
                MenuProvider p = new MenuProvider() {
                    @Override
                    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
                        return new GiftMenu(pContainerId, pLevel, pPos, pInventory);
                    }

                    @Override
                    public Component getDisplayName() {
                        return gift.getTitle();
                    }
                };
                NetworkHooks.openScreen((ServerPlayer) pPlayer, p, blockentity.getBlockPos());
            }
            return InteractionResult.CONSUME;
        }
    }
}
