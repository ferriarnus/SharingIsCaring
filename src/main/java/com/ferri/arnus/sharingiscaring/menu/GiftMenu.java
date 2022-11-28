package com.ferri.arnus.sharingiscaring.menu;

import com.ferri.arnus.sharingiscaring.blockentity.GiftBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class GiftMenu extends AbstractContainerMenu {
    private final int size = 5;
    GiftBlockEntity gift;

    public GiftMenu(final int pContainerId, Inventory playerInventory, final FriendlyByteBuf data) {
        this(pContainerId, playerInventory.player.level, data.readBlockPos() ,playerInventory);
    }

    public GiftMenu(final int pContainerId, Level world, BlockPos pos, Inventory playerInventory) {
        super(MenuRegistry.GIFT.get(), pContainerId);
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof GiftBlockEntity gift) {
            this.gift = gift;
        }
        addSlot(new SlotItemHandler(gift.getHandler(), 0, 44, 20));
        addSlot(new SlotItemHandler(gift.getHandler(), 1, 62, 20));
        addSlot(new SlotItemHandler(gift.getHandler(), 2, 80, 20));
        addSlot(new SlotItemHandler(gift.getHandler(), 3, 98, 20));
        addSlot(new SlotItemHandler(gift.getHandler(), 4, 116, 20));

        this.bindPlayerInventory(new InvWrapper(playerInventory));
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < size) {
                if (!this.moveItemStackTo(itemstack1, size, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, size, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    private void bindPlayerInventory(IItemHandler inventory) {
        for(int l = 0; l < 3; ++l) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new SlotItemHandler(inventory, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new SlotItemHandler(inventory, i1, 8 + i1 * 18, 109));
        }
    }
}
