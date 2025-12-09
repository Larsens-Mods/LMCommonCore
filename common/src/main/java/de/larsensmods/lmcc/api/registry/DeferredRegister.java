package de.larsensmods.lmcc.api.registry;

import de.larsensmods.lmcc.platform.Services;
import de.larsensmods.lmcc.registry.IWrappedRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

/**
 * Platform-Agnostic wrapper around the different registration systems of the supported loaders,
 * inspired by (Neo)Forges registration system
 * @param <T> Registry type handled by this DeferredRegister (see {@link net.minecraft.core.registries.Registries})
 */
public class DeferredRegister<T> {

    private final IWrappedRegister<T> wrappedRegister;
    private final ResourceKey<Registry<T>> registryKey;
    private final String modID;

    //hide constructor
    private DeferredRegister(IWrappedRegister<T> wrappedRegister, ResourceKey<Registry<T>> registryKey, String modID) {
        this.wrappedRegister = wrappedRegister;
        this.registryKey = registryKey;
        this.modID = modID;
    }

    /**
     * Register a new object to the wrapped registry of this instance
     * @param name The name under that the object should be registered
     * @param supplier A {@link Supplier} supplying the object to be registered
     * @return A {@link DeferredSupplier} instance wrapping the registered object
     * @param <O> Type of the object to register
     */
    public <O extends T> DeferredSupplier<O> register(String name, Supplier<? extends O> supplier){
        return this.wrappedRegister.register(name, supplier);
    }

    /**
     * Finalize the registration on the underlying register. Must be called after everything is written to the register
     * to complete the registration, otherwise errors can occur
     */
    public void register(){
        this.wrappedRegister.register();
    }

    /**
     * Creates a new DeferredRegister instance for your mod and a specific registry
     * @param registryKey Key of the registry the new instance should be supporting, see {@link net.minecraft.core.registries.Registries}
     * @param modID The modID of your mod
     * @return A new DeferredRegister instance for your mod and the given registry
     * @param <R> The type of the registry the instance is created for
     */
    public static <R> DeferredRegister<R> create(ResourceKey<Registry<R>> registryKey, String modID){
        IWrappedRegister<R> wrappedRegister = Services.REGISTRY.getWrappedRegister(registryKey, modID);
        return new DeferredRegister<>(wrappedRegister, registryKey, modID);
    }

}
