package de.larsensmods.lmcc.event;

import de.larsensmods.lmcc.LMCCConstants;
import de.larsensmods.lmcc.api.wrappers.item.WrappedSpawnEggItem;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = LMCCConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerSpawnEggColors(RegisterColorHandlersEvent.Item event){
        for(WrappedSpawnEggItem egg : WrappedSpawnEggItem.MODDED_EGGS){
            event.register((stack, layer) -> FastColor.ARGB32.opaque(egg.getColor(layer)), egg);
        }
    }

}
