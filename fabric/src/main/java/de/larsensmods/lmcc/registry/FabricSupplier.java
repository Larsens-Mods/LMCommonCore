package de.larsensmods.lmcc.registry;

import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class FabricSupplier<O, T extends O> implements DeferredSupplier<T> {

    private final T content;
    private final Registry<O> registry;
    private final ResourceLocation id;
    private Holder<T> holder;

    public FabricSupplier(T content, Registry<O> registry, ResourceLocation id) {
        this.content = content;
        this.registry = registry;
        this.id = id;
    }

    @Override
    public void onRegistration(Consumer<T> callback) {
        callback.accept(this.content);
    }

    @Override
    public T get() {
        return this.content;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public ResourceLocation getRegistryId() {
        return this.registry.key().location();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @Nullable Holder<T> getHolder() {
        if (this.holder != null) {
            return this.holder;
        }
        Holder<O> tmpHolder = this.registry.getHolder(this.id).orElse(null);
        return this.holder = (Holder<T>) tmpHolder;
    }

}
