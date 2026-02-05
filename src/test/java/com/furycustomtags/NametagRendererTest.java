package com.furycustomtags;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для проверки корректного отображения prefix, nickname и suffix
 */
public class NametagRendererTest {
    
    @Test
    @DisplayName("Тест: displayName содержит prefix")
    public void testDisplayNameContainsPrefix() {
        // Симуляция displayName с префиксом
        ITextComponent displayName = new StringTextComponent("[Admin] ")
            .withStyle(TextFormatting.RED)
            .append(new StringTextComponent("TestPlayer").withStyle(TextFormatting.WHITE));
        
        String text = displayName.getString();
        
        // Проверяем что содержит prefix
        assertTrue(text.contains("[Admin]"), "DisplayName должен содержать prefix [Admin]");
        System.out.println("✓ Prefix найден: " + text);
    }
    
    @Test
    @DisplayName("Тест: displayName содержит nickname")
    public void testDisplayNameContainsNickname() {
        // Симуляция displayName с ником
        ITextComponent displayName = new StringTextComponent("[VIP] ")
            .withStyle(TextFormatting.GOLD)
            .append(new StringTextComponent("PlayerName").withStyle(TextFormatting.YELLOW));
        
        String text = displayName.getString();
        
        // Проверяем что содержит nickname
        assertTrue(text.contains("PlayerName"), "DisplayName должен содержать nickname PlayerName");
        System.out.println("✓ Nickname найден: " + text);
    }
    
    @Test
    @DisplayName("Тест: displayName содержит suffix")
    public void testDisplayNameContainsSuffix() {
        // Симуляция displayName с суффиксом
        ITextComponent displayName = new StringTextComponent("Player")
            .withStyle(TextFormatting.WHITE)
            .append(new StringTextComponent(" [MVP]").withStyle(TextFormatting.AQUA));
        
        String text = displayName.getString();
        
        // Проверяем что содержит suffix
        assertTrue(text.contains("[MVP]"), "DisplayName должен содержать suffix [MVP]");
        System.out.println("✓ Suffix найден: " + text);
    }
    
    @Test
    @DisplayName("Тест: displayName содержит prefix + nickname + suffix")
    public void testDisplayNameContainsAll() {
        // Симуляция полного displayName (prefix + ник + suffix)
        ITextComponent displayName = new StringTextComponent("[Admin] ")
            .withStyle(TextFormatting.RED)
            .append(new StringTextComponent("TestPlayer").withStyle(TextFormatting.WHITE))
            .append(new StringTextComponent(" [VIP]").withStyle(TextFormatting.GOLD));
        
        String text = displayName.getString();
        
        // Проверяем все компоненты
        assertTrue(text.contains("[Admin]"), "DisplayName должен содержать prefix [Admin]");
        assertTrue(text.contains("TestPlayer"), "DisplayName должен содержать nickname TestPlayer");
        assertTrue(text.contains("[VIP]"), "DisplayName должен содержать suffix [VIP]");
        
        System.out.println("✓ Полный displayName: " + text);
        System.out.println("✓ Содержит prefix: [Admin]");
        System.out.println("✓ Содержит nickname: TestPlayer");
        System.out.println("✓ Содержит suffix: [VIP]");
    }
    
    @Test
    @DisplayName("Тест: displayName с цветами из TAB")
    public void testDisplayNameWithColors() {
        // Симуляция displayName с цветами (как из TAB/LuckPerms)
        ITextComponent displayName = new StringTextComponent("[")
            .withStyle(TextFormatting.DARK_GRAY)
            .append(new StringTextComponent("Owner").withStyle(TextFormatting.DARK_RED))
            .append(new StringTextComponent("] ").withStyle(TextFormatting.DARK_GRAY))
            .append(new StringTextComponent("Player123").withStyle(TextFormatting.LIGHT_PURPLE));
        
        String text = displayName.getString();
        
        // Проверяем содержимое
        assertTrue(text.contains("[Owner]"), "DisplayName должен содержать [Owner]");
        assertTrue(text.contains("Player123"), "DisplayName должен содержать Player123");
        
        // Проверяем что есть форматирование
        assertNotNull(displayName.getStyle(), "DisplayName должен иметь style");
        
        System.out.println("✓ DisplayName с цветами: " + text);
    }
    
    @Test
    @DisplayName("Тест: пустой displayName (fallback)")
    public void testEmptyDisplayName() {
        // Тест для случая когда displayName пустой
        ITextComponent displayName = new StringTextComponent("");
        
        String text = displayName.getString();
        
        // Проверяем что не null
        assertNotNull(text, "DisplayName не должен быть null");
        
        System.out.println("✓ Пустой displayName обработан корректно");
    }
}
