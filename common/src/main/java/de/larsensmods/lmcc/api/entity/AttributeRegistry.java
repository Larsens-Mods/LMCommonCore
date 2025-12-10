package de.larsensmods.lmcc.api.entity;

import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import de.larsensmods.lmcc.platform.Services;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.Supplier;

/**
 * Class to enable the registration of entity attributes for all loaders
 */
public class AttributeRegistry {

    /**
     * Register attributes for an entity to the game registry
     * @param entityType Entity type to register the attributes for
     * @param attributes The attributes to register for the type
     */
    public static void register(DeferredSupplier<? extends EntityType<? extends LivingEntity>> entityType, Supplier<AttributeSupplier> attributes){
        Services.REGISTRY.registerToEntityAttributeRegistry(entityType, attributes);
    }

}
