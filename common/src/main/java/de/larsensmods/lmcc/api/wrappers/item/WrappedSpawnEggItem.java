package de.larsensmods.lmcc.api.wrappers.item;

import de.larsensmods.lmcc.Constants;
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

public class WrappedSpawnEggItem extends SpawnEggItem {

    private final DeferredSupplier<? extends EntityType<? extends Mob>> entityTypeSupplier;

    public WrappedSpawnEggItem(DeferredSupplier<? extends EntityType<? extends Mob>> entityTypeSupplier, int backgroundColor, int highlightColor, Properties itemProperties) {
        super(null, backgroundColor, highlightColor, itemProperties);
        this.entityTypeSupplier = entityTypeSupplier;
        SpawnEggItem.BY_ID.remove(null);
        this.entityTypeSupplier.onRegistration(entityType -> {
            Constants.LOG.debug("Registering SpawnEgg for type " + entityType.getDescriptionId());
            SpawnEggItem.BY_ID.put(entityType, this);
            this.defaultType = entityType;

            DispenserBlock.registerBehavior(this, DEFAULT_DISPENSE_BEHAVIOR);
        });
    }

    @Override
    public EntityType<?> getType(ItemStack pStack) {
        EntityType<?> entityType = super.getType(pStack);
        return entityType == null ? this.entityTypeSupplier.get() : entityType;
    }

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
