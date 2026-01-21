package de.larsensmods.lmcc.api.wrappers.item;

import de.larsensmods.lmcc.LMCCConstants;
import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper class for Minecraft's {@link SpawnEggItem} to enable cross loader support for spawn eggs
 */
public class WrappedSpawnEggItem extends SpawnEggItem {

    public static final Set<WrappedSpawnEggItem> MODDED_EGGS = new HashSet<>();

    private final DeferredSupplier<? extends EntityType<? extends Mob>> entityTypeSupplier;

    /**
     * Creates a new WrappedSpawnEggItem instance that can then be used to be registered
     * @param entityTypeSupplier A {@link DeferredSupplier} of the {@link EntityType} this spawn egg should spawn
     * @param backgroundColor Background color of the spawn egg texture in game
     * @param highlightColor Highlight color of the spawn egg texture in game
     * @param itemProperties {@link net.minecraft.world.item.Item.Properties} of this item
     */
    public WrappedSpawnEggItem(DeferredSupplier<? extends EntityType<? extends Mob>> entityTypeSupplier, int backgroundColor, int highlightColor, Properties itemProperties) {
        super(null, backgroundColor, highlightColor, itemProperties);
        this.entityTypeSupplier = entityTypeSupplier;
        SpawnEggItem.BY_ID.remove(null);
        this.entityTypeSupplier.onRegistration(entityType -> {
            LMCCConstants.LOG.debug("Registering SpawnEgg for type " + entityType.getDescriptionId());
            SpawnEggItem.BY_ID.put(entityType, this);
            this.defaultType = entityType;

            DispenserBlock.registerBehavior(this, DEFAULT_DISPENSE_BEHAVIOR);
        });
        MODDED_EGGS.add(this);
    }

    @Override
    public @NotNull EntityType<?> getType(@NotNull ItemStack pStack) {
        EntityType<?> entityType = super.getType(pStack);
        return entityType == null ? this.entityTypeSupplier.get() : entityType;
    }

    //Dispense behaviour definition
    private static final DispenseItemBehavior DEFAULT_DISPENSE_BEHAVIOR = (source, stack) -> {
        Direction face = source.state().getValue(DispenserBlock.FACING);
        EntityType<?> type = ((SpawnEggItem)stack.getItem()).getType(stack);

        try {
            type.spawn(source.level(), stack, null, source.pos().relative(face), MobSpawnType.DISPENSER, face != Direction.UP, false);
        } catch (Exception exception) {
            DispenseItemBehavior.LOGGER.error("Error while dispensing spawn egg from dispenser at {}", source.pos(), exception);
            return ItemStack.EMPTY;
        }

        stack.shrink(1);
        source.level().gameEvent(GameEvent.ENTITY_PLACE, source.pos(), GameEvent.Context.of(source.state()));
        return stack;
    };

}
