package com.furycustomtags;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Класс для рендера неймтега над локальным игроком
 * Отображает prefix + ник + suffix из LuckPerms/TAB
 */
public class NametagRenderer {
    
    // Константы для оптимизации (кэшируем значения)
    private static final double MAX_DISTANCE_SQ = 4096.0D; // Максимальная дистанция рендера (64 блока)
    private static final float SCALE = 0.025F; // Масштаб текста
    private static final double Y_OFFSET = 0.5D; // Смещение над головой
    
    /**
     * Обработчик события рендера игрока
     * Вызывается после рендера каждого игрока
     */
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {
        PlayerEntity player = event.getPlayer();
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity localPlayer = mc.player;
        
        // Проверяем что это локальный игрок (ты)
        if (localPlayer == null || player != localPlayer) {
            return;
        }
        
        // Рендерим только в режиме F5 (third person)
        if (mc.options.getCameraType().isFirstPerson()) {
            return;
        }
        
        // Получаем displayName (уже содержит prefix + ник + suffix из LuckPerms/TAB)
        ITextComponent displayName = player.getDisplayName();
        
        // Рендерим неймтег
        renderNameTag(player, displayName, event.getMatrixStack(), event.getBuffers(), event.getLight(), mc);
    }
    
    /**
     * Метод для рендера неймтега над игроком
     * 
     * @param player Игрок
     * @param displayName Отображаемое имя (с prefix и suffix)
     * @param matrixStack Стек матриц для трансформаций
     * @param buffer Буфер рендера
     * @param light Уровень освещения
     * @param mc Инстанс Minecraft
     */
    private static void renderNameTag(PlayerEntity player, ITextComponent displayName, 
                                      MatrixStack matrixStack, IRenderTypeBuffer buffer, 
                                      int light, Minecraft mc) {
        EntityRendererManager renderManager = mc.getEntityRenderDispatcher();
        FontRenderer fontRenderer = mc.font;
        
        // Проверяем дистанцию (оптимизация - не рендерим если слишком далеко)
        double distanceSq = renderManager.distanceToSqr(player);
        if (distanceSq > MAX_DISTANCE_SQ) {
            return;
        }
        
        matrixStack.pushPose();
        
        // Позиционируем над головой игрока
        matrixStack.translate(0.0D, player.getBbHeight() + Y_OFFSET, 0.0D);
        
        // Поворачиваем неймтег в сторону камеры
        matrixStack.mulPose(renderManager.cameraOrientation());
        
        // Масштабируем (инвертируем Y чтобы текст был нормально)
        matrixStack.scale(-SCALE, -SCALE, SCALE);
        
        Matrix4f matrix = matrixStack.last().pose();
        
        // Вычисляем ширину текста для центрирования
        float textWidth = (float)(-fontRenderer.width(displayName) / 2);
        
        // Рендерим фон (черный, полупрозрачный)
        int backgroundColor = (int)(mc.options.getBackgroundOpacity(0.25F) * 255.0F) << 24;
        fontRenderer.drawInBatch(displayName, textWidth, 0, 0x20FFFFFF, false, 
                                matrix, buffer, true, backgroundColor, light);
        
        // Рендерим текст (белый, полная непрозрачность)
        fontRenderer.drawInBatch(displayName, textWidth, 0, -1, false, 
                                matrix, buffer, false, 0, light);
        
        matrixStack.popPose();
    }
}
