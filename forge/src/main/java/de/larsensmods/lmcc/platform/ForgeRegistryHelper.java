package de.larsensmods.lmcc.platform;

import de.larsensmods.lmcc.LMCCConstants;
import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import de.larsensmods.lmcc.registry.IWrappedRegister;
import de.larsensmods.lmcc.platform.services.IRegistryHelper;
import de.larsensmods.lmcc.registry.ForgeWrappedRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ForgeRegistryHelper implements IRegistryHelper {

    @Override
    public <R> IWrappedRegister<R> getWrappedRegister(ResourceKey<Registry<R>> key, String modID) {
        return new ForgeWrappedRegister<>(modID, key);
    }

    @Override
    public <T extends LivingEntity> void registerToEntityAttributeRegistry(DeferredSupplier<EntityType<T>> entityType, Supplier<AttributeSupplier> attributes) {
        CommonModEventsHandler.attributes.put(entityType, attributes);
    }

    @Override
    public <T extends Mob> void registerToSpawnPlacementsRegistry(DeferredSupplier<EntityType<T>> entityType, SpawnPlacementType spawnPlacementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate) {
        CommonModEventsHandler.spawnPlacements.add(new SpawnPlacementRegistryWrapper<>(entityType, spawnPlacementType, heightmapType, predicate));
    }

    @Mod.EventBusSubscriber(modid = LMCCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    private static class CommonModEventsHandler {

        static final Map<DeferredSupplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier>> attributes = new HashMap<>();
        static final Set<SpawnPlacementRegistryWrapper<? extends Mob>> spawnPlacements = new HashSet<>();

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            LMCCConstants.LOG.info("Registering attributes for entity types");
            for(Map.Entry<DeferredSupplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier>> entry : attributes.entrySet()){
                event.put(entry.getKey().get(), entry.getValue().get());
            }
        }

        @SubscribeEvent
        public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
            for(SpawnPlacementRegistryWrapper<? extends Mob> wrapper : spawnPlacements){
                wrapper.register(event);
            }
        }
    }

    private static class SpawnPlacementRegistryWrapper<T extends Mob> {
        DeferredSupplier<EntityType<T>> entityType;
        SpawnPlacementType spawnPlacementType;
        Heightmap.Types heightmapType;
        SpawnPlacements.SpawnPredicate<T> predicate;

        private SpawnPlacementRegistryWrapper(DeferredSupplier<EntityType<T>> entityType, SpawnPlacementType spawnPlacementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate) {
            this.entityType = entityType;
            this.spawnPlacementType = spawnPlacementType;
            this.heightmapType = heightmapType;
            this.predicate = predicate;
        }

        private void register(SpawnPlacementRegisterEvent event){
            event.register(entityType.get(), spawnPlacementType, heightmapType, predicate, SpawnPlacementRegisterEvent.Operation.REPLACE);
        }
    }
}
