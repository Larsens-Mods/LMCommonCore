package de.larsensmods.lmcc.platform;

import de.larsensmods.lmcc.platform.services.IRegistryHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class ForgeRegistryHelper implements IRegistryHelper {

    private DeferredRegister<EntityType<?>> ENTITY_TYPES;
    private DeferredRegister<Item> ITEMS;

    private final Map<String, Function<?, ?>> OVERRIDES = new HashMap<>();

    public void addOverride(String id, Function<?, ?> function) {
        OVERRIDES.put(id, function);
    }

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
        if(OVERRIDES.containsKey(itemID)){
            return ITEMS.register(itemID, () -> ((Function<Item.Properties, Item>) OVERRIDES.get(itemID)).apply(properties));
        }
        return ITEMS.register(itemID, () -> factory.apply(properties));
    }

    //MOD ID REGISTRATION AND SETUP PART
    private String modID = null;
    private static final Set<ForgeRegistryHelper> instances = new HashSet<>();

    @Override
    public IRegistryHelper withModID(@NotNull String modID) {
        if(this.modID == null){
            this.modID = modID;
            this.setupRegisters(modID);
            instances.add(this);
        }else if(!this.modID.equals(modID)){
            ForgeRegistryHelper newIdHelper = new ForgeRegistryHelper();
            newIdHelper.modID = modID;
            newIdHelper.setupRegisters(modID);
            instances.add(newIdHelper);
            return newIdHelper;
        }
        return this;
    }

    private void setupRegisters(String modID){
        ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, modID);
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modID);
    }

    public static Set<ForgeRegistryHelper> getInstanceSet(){
        return Set.copyOf(instances);
    }

}
