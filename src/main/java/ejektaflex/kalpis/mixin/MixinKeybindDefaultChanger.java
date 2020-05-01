package ejektaflex.kalpis.mixin;

import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBinding.class)
public interface MixinKeybindDefaultChanger  {

    @Accessor("defaultKeyCode")
    void setDefaultKeyCode(InputUtil.KeyCode code);

}


