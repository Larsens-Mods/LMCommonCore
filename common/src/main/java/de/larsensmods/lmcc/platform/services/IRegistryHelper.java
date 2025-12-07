package de.larsensmods.lmcc.platform.services;

import de.larsensmods.lmcc.api.LMCCRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Service Interface providing a common base for the registration flow. Instances should ONLY be obtained through the
 * {@link LMCCRegistry#getRegistry(String)} method to prevent exceptions from occurring
 */
public interface IRegistryHelper {

    /**
     * Registers an {@link EntityType} through the loader specific registration method. This method takes care of most loader specific specialties
     * @param entityID The ID to use when registering the type
     * @param entityTypeSupplier A {@link Supplier} for the type you want to register
     * @return A {@link Supplier} providing you with the fully registered type once the game is loaded
     * @param <T> The entity class, must be a subclass of {@link Entity}
     */
    <T extends Entity> Supplier<EntityType<T>> registerEntityType(String entityID, Supplier<EntityType<T>> entityTypeSupplier);

    /**
     * Registers an {@link Item} through the loader specific registration method. This method takes care of most loader specific specialties
     * @param itemID The ID to use when registering the item
     * @param factory A {@link Function} constructing the {@link Item} instance using given {@link net.minecraft.world.item.Item.Properties}
     * @param properties The {@link net.minecraft.world.item.Item.Properties} to use when constructing the item instance
     * @return A {@link Supplier} providing you with the fully registered item once the game is loaded
     */
    Supplier<Item> registerItem(String itemID, Function<Item.Properties, Item> factory, Item.Properties properties);

    //---BEGIN INTERNAL UTILITIES---
    /**
     * Used for obtaining an instance with a set ModID
     * @param modID The ModID to obtain an instance for
     * @return An instance setup for a specific ModID
     */
    IRegistryHelper withModID(@NotNull String modID);

}
