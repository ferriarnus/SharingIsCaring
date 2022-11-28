package com.ferri.arnus.sharingiscaring.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class GiftBlockEntity extends BlockEntity {
    private final ItemStackHandler handler = new ItemStackHandler(5);
    private UUID owner;
    private UUID target;

    public GiftBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.GIFT.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("items", handler.serializeNBT());
        if (owner != null) {
            pTag.putUUID("owner", this.owner);
        }
        if (target != null) {
            pTag.putUUID("target", this.target);
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.handler.deserializeNBT(pTag.getCompound("items"));
        if (pTag.contains("owner")) {
            this.owner = pTag.getUUID("owner");
        }
        if (pTag.contains("target")) {
            this.target = pTag.getUUID("target");
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public IItemHandler getHandler() {
        return handler;
    }

    public Component getTitle() {
        return Component.translatable("sharingiscaring.gift.menu", this.owner == null? "?" : this.level.getPlayerByUUID(owner), this.target == null? "?" : this.level.getPlayerByUUID(this.target));
    }

    public UUID getOwner() {
        return this.owner;
    }

    public UUID getTarget() {
        return target;
    }
}
