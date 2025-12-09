package de.larsensmods.lmcc.api.registry;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface DeferredSupplier<T> extends Supplier<T> {

    void onRegistration(Consumer<T> callback);

}
