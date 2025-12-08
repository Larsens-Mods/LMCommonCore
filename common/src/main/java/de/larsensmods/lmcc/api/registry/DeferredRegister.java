package de.larsensmods.lmcc.api.registry;

import de.larsensmods.lmcc.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class DeferredRegister<T> {

    private final IWrappedRegister<T> wrappedRegister;
    private final ResourceKey<Registry<T>> registryKey;
    private final String modID;

    private DeferredRegister(IWrappedRegister<T> wrappedRegister, ResourceKey<Registry<T>> registryKey, String modID) {
        this.wrappedRegister = wrappedRegister;
        this.registryKey = registryKey;
        this.modID = modID;
    }

    public <O extends T> Supplier<O> register(String name, Supplier<? extends O> supplier){
        return this.wrappedRegister.register(name, supplier);
    }

    public void register(){
        this.wrappedRegister.register();
    }

    public static <R> DeferredRegister<R> create(ResourceKey<Registry<R>> registryKey, String modID){
        IWrappedRegister<R> wrappedRegister = Services.REGISTRY.getWrappedRegister(registryKey, modID);
        return new DeferredRegister<>(wrappedRegister, registryKey, modID);
    }

}
