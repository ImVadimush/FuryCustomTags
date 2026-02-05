package com.furycustomtags;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

/**
 * Главный класс мода Fury Custom Tags
 * Отображает твой prefix + ник + suffix в режиме F5
 */
@Mod("furycustomtags")
public class FuryCustomTagsMod {
    
    public FuryCustomTagsMod() {
        // Регистрируем обработчик рендера неймтегов
        MinecraftForge.EVENT_BUS.register(NametagRenderer.class);
    }
}
