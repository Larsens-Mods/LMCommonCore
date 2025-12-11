package de.larsensmods.lmcc.api.entity;

import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import de.larsensmods.lmcc.platform.Services;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * Class to enable registration of spawn placements across all supported loaders
 */
public class SpawnPlacementsRegistry {

    /**
     * Register spawn placements to the game
     * @param entityType {@link DeferredSupplier} of the entity type to register the placement for
     * @param spawnPlacementType Type of the spawn placement
     * @param heightmapType Heightmap type of the spawn placement
     * @param predicate Predicate to check if spawning can occur
     * @param <T> Entity class of the type to register for
     */
    public static <T extends Mob> void register(DeferredSupplier<EntityType<T>> entityType, SpawnPlacementType spawnPlacementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate) {
        Services.REGISTRY.registerToSpawnPlacementsRegistry(entityType, spawnPlacementType, heightmapType, predicate);
    }

}
