package de.larsensmods.lmcc.registry;

import de.larsensmods.lmcc.api.registry.DeferredSupplier;

import java.util.function.Consumer;

public class FabricSupplier<T> implements DeferredSupplier<T> {

    private final T content;

    public FabricSupplier(T content) {
        this.content = content;
    }

    @Override
    public void onRegistration(Consumer<T> callback) {
        callback.accept(this.content);
    }

    @Override
    public T get() {
        return this.content;
    }
}
