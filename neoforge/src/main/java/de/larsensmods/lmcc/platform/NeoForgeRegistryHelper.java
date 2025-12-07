package de.larsensmods.lmcc.platform;

import de.larsensmods.lmcc.platform.services.IRegistryHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements IRegistryHelper {

    private DeferredRegister<EntityType<?>> ENTITY_TYPES;
    private DeferredRegister<Item> ITEMS;

    public void finishRegistration(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
        ITEMS.register(eventBus);
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntityType(String entityID, Supplier<EntityType<T>> entityTypeSupplier) {
        return ENTITY_TYPES.register(entityID, entityTypeSupplier);
    }

    @Override
    public Supplier<Item> registerItem(String itemID, Function<Item.Properties, Item> factory, Item.Properties properties) {
        return ITEMS.register(itemID, () -> factory.apply(properties));
    }

    //MOD ID REGISTRATION AND SETUP PART
    private String modID = null;
    private static final Set<NeoForgeRegistryHelper> instances = new HashSet<>();

    @Override
    public IRegistryHelper withModID(@NotNull String modID) {
        if(this.modID == null){
            this.modID = modID;
            this.setupRegisters(modID);
            instances.add(this);
        }else if(!this.modID.equals(modID)){
            NeoForgeRegistryHelper newIdHelper = new NeoForgeRegistryHelper();
            newIdHelper.modID = modID;
            newIdHelper.setupRegisters(modID);
            instances.add(newIdHelper);
            return newIdHelper;
        }
        return this;
    }

    private void setupRegisters(String modID){
        ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, modID);
        ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, modID);
    }

    public static Set<NeoForgeRegistryHelper> getInstanceSet(){
        return Set.copyOf(instances);
    }

}
