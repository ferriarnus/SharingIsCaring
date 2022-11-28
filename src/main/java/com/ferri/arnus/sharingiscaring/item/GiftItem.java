package com.ferri.arnus.sharingiscaring.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class GiftItem extends BlockItem {

    public GiftItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        CompoundTag tag = new CompoundTag();
        tag.putUUID("owner", pPlayer.getUUID());
        CompoundTag itemTag = stack.getOrCreateTag();
        if (itemTag.contains("BlockEntityTag")) {
            itemTag.getCompound("BlockEntityTag").putUUID("owner", pPlayer.getUUID());
        } else {
            itemTag.put("BlockEntityTag", tag);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if (pStack.hasTag() && pStack.getOrCreateTag().contains("BlockEntityTag") && pLevel != null) {
            if (pStack.getOrCreateTag().getCompound("BlockEntityTag").contains("target")) {
                UUID owner = pStack.getOrCreateTag().getCompound("BlockEntityTag").getUUID("target");
                Player targetPlayer = pLevel.getPlayerByUUID(owner);
                if (targetPlayer != null) {
                    pTooltip.add(1,Component.translatable("sharingiscaring.target", targetPlayer.getDisplayName().getString()).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
                }
            }
            if (pStack.getOrCreateTag().getCompound("BlockEntityTag").contains("owner")) {
                UUID owner = pStack.getOrCreateTag().getCompound("BlockEntityTag").getUUID("owner");
                Player ownerPlayer = pLevel.getPlayerByUUID(owner);
                if (ownerPlayer != null) {
                    pTooltip.add(1,Component.translatable("sharingiscaring.owner", ownerPlayer.getDisplayName().getString()).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
                }
            }

        }
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
