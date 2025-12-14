package de.larsensmods.lmcc.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(CallbackInfo info) {
        /*LMCCConstants.LOG.info("This line is printed by the LMCommonCore common mixin!");
        LMCCConstants.LOG.info("MC Version: {}", Minecraft.getInstance().getVersionType());*/
    }
}
