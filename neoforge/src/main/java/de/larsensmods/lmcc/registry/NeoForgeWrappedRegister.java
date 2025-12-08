package de.larsensmods.lmcc.registry;

import de.larsensmods.lmcc.LMCCore;
import de.larsensmods.lmcc.api.registry.IWrappedRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoForgeWrappedRegister<T> implements IWrappedRegister<T> {

    private final String modID;
    private final DeferredRegister<T> register;

    public NeoForgeWrappedRegister(String modID, ResourceKey<Registry<T>> registryKey) {
        this.modID = modID;
        this.register = DeferredRegister.create(registryKey, modID);
    }

    @Override
    public <O extends T> Supplier<O> register(String name, Supplier<? extends O> supplier) {
        return this.register.register(name, supplier);
    }

    @Override
    public void register() {
        this.register.register(LMCCore.getEventBus());
    }
}
