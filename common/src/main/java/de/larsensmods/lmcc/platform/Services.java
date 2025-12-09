package de.larsensmods.lmcc.platform;

import de.larsensmods.lmcc.LMCCConstants;
import de.larsensmods.lmcc.platform.services.IPlatformHelper;
import de.larsensmods.lmcc.platform.services.IRegistryHelper;

import java.util.ServiceLoader;

/**
 * Central access point for platform specific services.
 */
public class Services {

    //Platform helper
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    //Registry helper
    public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);

    /**
     * Loads a service using the ServiceLoader mechanism.
     *
     * @param clazz The class of the service to load.
     * @return      The correct service implementation.
     * @param <T>   The type of the service.
     */
    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LMCCConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}