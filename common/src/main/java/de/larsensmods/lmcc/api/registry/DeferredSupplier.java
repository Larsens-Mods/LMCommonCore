package de.larsensmods.lmcc.api.registry;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Special type of {@link Supplier} enabling the execution of code once the referenced object is made fully available
 * by the underlying loader
 * @param <T> Type held in this instance
 */
public interface DeferredSupplier<T> extends Supplier<T> {

    /**
     * Register a callback to be executed once the underlying object is fully registered
     * @param callback {@link Consumer} called with the fully registered object once it is available
     */
    void onRegistration(Consumer<T> callback);

}
