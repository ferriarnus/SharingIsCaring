package com.ferri.arnus.sharingiscaring.block;

import com.ferri.arnus.sharingiscaring.blockentity.BlockEntityRegistry;
import com.ferri.arnus.sharingiscaring.blockentity.GiftBlockEntity;
import com.ferri.arnus.sharingiscaring.menu.GiftMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class Gift extends Block implements EntityBlock {

    private final VoxelShape SHAPE = Shapes.join(Block.box(1,0,1,15,10,15), Block.box(0,10,0,16,13,16), BooleanOp.OR);

    public Gift(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return BlockEntityRegistry.GIFT.get().create(pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof GiftBlockEntity gift) {
                if ((!pPlayer.getUUID().equals(gift.getOwner()) && gift.getOwner() != null) || (!pPlayer.getUUID().equals(gift.getTarget()) && gift.getTarget() != null)) {
                    MutableComponent message = Component.translatable("sharingiscaring.message", gift.getOwner() == null? "" : pLevel.getPlayerByUUID(gift.getOwner()), gift.getTarget() == null? "" : pLevel.getPlayerByUUID(gift.getTarget()));
                    pPlayer.sendSystemMessage(message);
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

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (level.getBlockEntity(pos) instanceof GiftBlockEntity gift) {
            if (gift.getOwner() == null || gift.getTarget() == null || player.getUUID().equals(gift.getOwner()) || player.getUUID().equals(gift.getTarget()) || player.getAbilities().instabuild) {
                return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
            }
        }
        return false;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.BLOCK;
    }
}
