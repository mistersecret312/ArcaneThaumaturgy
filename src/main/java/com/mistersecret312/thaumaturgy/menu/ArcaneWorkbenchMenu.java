package com.mistersecret312.thaumaturgy.menu;

import com.mistersecret312.thaumaturgy.block_entities.ArcaneCraftingTableBlockEntity;
import com.mistersecret312.thaumaturgy.containers.ArcaneWorkbenchCraftingContainer;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.init.MenuInit;
import com.mistersecret312.thaumaturgy.menu.slots.WandSlotItemHandler;
import com.mistersecret312.thaumaturgy.recipes.ArcaneCraftingShapedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArcaneWorkbenchMenu extends AbstractContainerMenu
{
    public ArcaneCraftingTableBlockEntity blockEntity;
    public Level level;

    public ArcaneWorkbenchMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer)
    {
        this(containerId, inventory, inventory.player.level().getBlockEntity(buffer.readBlockPos()));
    }

    public ArcaneWorkbenchMenu(int pContainerId, Inventory inventory, BlockEntity blockEntity)
    {
        super(MenuInit.ARCANE_WORKBENCH.get(), pContainerId);
        this.blockEntity = ((ArcaneCraftingTableBlockEntity) blockEntity);
        this.level = inventory.player.level();

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.addSlot(new SlotItemHandler(this.blockEntity.getInput(), 0, 66, 37));
        this.addSlot(new SlotItemHandler(this.blockEntity.getInput(), 1, 89, 37));
        this.addSlot(new SlotItemHandler(this.blockEntity.getInput(), 2, 112, 37));

        this.addSlot(new SlotItemHandler(this.blockEntity.getInput(), 3, 66, 60));
        this.addSlot(new SlotItemHandler(this.blockEntity.getInput(), 4, 89, 60));
        this.addSlot(new SlotItemHandler(this.blockEntity.getInput(), 5, 112, 60));

        this.addSlot(new SlotItemHandler(this.blockEntity.getInput(), 6, 66, 83));
        this.addSlot(new SlotItemHandler(this.blockEntity.getInput(), 7, 89, 83));
        this.addSlot(new SlotItemHandler(this.blockEntity.getInput(), 8, 112, 83));

        this.addSlot(new WandSlotItemHandler(this.blockEntity.getWandHandler(), 0, 184, 16));

        this.addSlot(new SlotItemHandler(this.blockEntity.getOutputHandler(), 0, 184, 60)
        {
            private int removeCount;

            @Override
            public @NotNull ItemStack remove(int amount)
            {
                if (this.hasItem()) {
                    this.removeCount += Math.min(amount, this.getItem().getCount());
                }

                return super.remove(amount);
            }

            @Override
            public void onTake(Player pPlayer, ItemStack pStack)
            {
                super.onTake(pPlayer, pStack);
                this.checkTakeAchievements(pStack);
                if(!pPlayer.isLocalPlayer())
                {
                    Optional<CraftingRecipe> craftingRecipe = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, new ArcaneWorkbenchCraftingContainer(((ArcaneCraftingTableBlockEntity) blockEntity).getInput(),((ArcaneCraftingTableBlockEntity) blockEntity).getWandHandler()), level);
                    Optional<ArcaneCraftingShapedRecipe> arcaneShaped = level.getRecipeManager().getRecipeFor(ArcaneCraftingShapedRecipe.Type.INSTANCE, new ArcaneWorkbenchCraftingContainer(((ArcaneCraftingTableBlockEntity) blockEntity).getInput(), ((ArcaneCraftingTableBlockEntity) blockEntity).getWandHandler()), level);
                    if(craftingRecipe.isPresent())
                        ((ArcaneCraftingTableBlockEntity) blockEntity).doRecipeAfterstuff(pPlayer);
                    if(arcaneShaped.isPresent())
                        ((ArcaneCraftingTableBlockEntity) blockEntity).doMagicRecipeStuff(pPlayer, arcaneShaped.get());

                }
            }

            @Override
            protected void onQuickCraft(ItemStack pStack, int pAmount)
            {
                removeCount++;
                this.checkTakeAchievements(pStack);
            }

            @Override
            protected void onSwapCraft(int pNumItemsCrafted)
            {
                removeCount++;
            }

            @Override
            protected void checkTakeAchievements(ItemStack pStack)
            {
                if (this.removeCount > 0) {
                    pStack.onCraftedBy(inventory.player.level(), inventory.player, this.removeCount);
                    net.minecraftforge.event.ForgeEventFactory.firePlayerCraftingEvent(inventory.player, pStack, new ArcaneWorkbenchCraftingContainer(((ArcaneCraftingTableBlockEntity) blockEntity).getInput(), ((ArcaneCraftingTableBlockEntity) blockEntity).getWandHandler()));
                }

                Container container = this.container;
                if (container instanceof RecipeHolder recipeholder)
                {
                    List<ItemStack> stacks = new ArrayList<>();
                    for (int i = 0; i < ((ArcaneCraftingTableBlockEntity) blockEntity).getInput().getSlots(); i++)
                    {
                        stacks.add(((ArcaneCraftingTableBlockEntity) blockEntity).getInput().getStackInSlot(i).copy());
                    }
                    recipeholder.awardUsedRecipes(inventory.player, stacks);
                }

                this.removeCount = 0;
            }

            @Override
            public boolean mayPlace(@NotNull ItemStack stack)
            {
                return false;
            }
        });

        this.slotsChanged(inventory);
    }

    @Override
    public void slotsChanged(Container pContainer)
    {
        super.slotsChanged(pContainer);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, BlockInit.ARCANE_WORKBENCH.get());
    }

    private void addPlayerInventory(Inventory playerInventory)
    {
        for (int i = 0; i < 3; ++i)
        {
            for (int l = 0; l < 9; ++l)
            {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 17 + l * 18, 148 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory)
    {
        for (int i = 0; i < 9; ++i)
        {
            this.addSlot(new Slot(playerInventory, i, 17 + i * 18, 206));
        }
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 11;  // must match TileEntityInventoryBasic.NUMBER_OF_SLOTS

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index)
    {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT)
        {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false))
            {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        }
        else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT)
        {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false))
            {
                return ItemStack.EMPTY;
            }
        } else
        {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0)
        {
            sourceSlot.set(ItemStack.EMPTY);
        } else
        {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }
}
