package de.larsensmods.lmcc.registry;

import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class FabricWrappedRegister<T> implements IWrappedRegister<T> {

    private final String modID;
    private final Registry<T> registry;

    public FabricWrappedRegister(String modID, ResourceKey<Registry<T>> registryKey) {
        this.modID = modID;
        this.registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(registryKey.location());
    }


    @Override
    public <O extends T> DeferredSupplier<O> register(String name, Supplier<? extends O> supplier) {
        O registeredObject = Registry.register(this.registry, ResourceLocation.fromNamespaceAndPath(this.modID, name), supplier.get());
        return new FabricSupplier<>(registeredObject);
    }

    @Override
    public void register() {}
}
