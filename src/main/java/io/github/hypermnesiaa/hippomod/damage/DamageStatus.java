package io.github.hypermnesiaa.hippomod.damage;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

import java.math.BigDecimal;
import java.math.MathContext;

public class DamageStatus {

    boolean enabled = false;

    int cooldown = 1;
    int ticks = 0;

    float damage;
    float absorptionHearts;
    float heal;

    @SubscribeEvent
    public void onPlayerDamage (LivingDamageEvent event) {
        if (enabled) {
            if (event.getEntity() instanceof Player) {
                damage = event.getAmount();
                if (((Player) event.getEntity()).getAbsorptionAmount() > 0) {
                    damage = ((Player) event.getEntity()).getAbsorptionAmount() - absorptionHearts;
                }
                BigDecimal decimal = new BigDecimal(damage);
                MathContext context = new MathContext(3);
                BigDecimal round = decimal.round(context);
                damage = Math.abs(round.floatValue());
                event.getEntityLiving().sendMessage(Component.nullToEmpty(ChatFormatting.RED + "[-] " + damage + " Health"), event.getEntity().getUUID());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerBeforeDamage (LivingHurtEvent event) {
        if (enabled) {
            if (event.getEntity() instanceof Player) {
                absorptionHearts = ((Player) event.getEntity()).getAbsorptionAmount();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerHeal (LivingHealEvent event) {
        if (enabled) {
            if (event.getEntity() instanceof Player) {
                BigDecimal decimal = new BigDecimal(heal);
                MathContext context = new MathContext(3);
                BigDecimal round = decimal.round(context);
                heal = Math.abs(round.floatValue());
                event.getEntityLiving().sendMessage(Component.nullToEmpty(ChatFormatting.GREEN + "[+] " + heal + " Health"), event.getEntity().getUUID());
            }
        }
    }

    @SubscribeEvent
    public void onKeyPress (InputEvent.KeyInputEvent event) {
        // crashes game if used on title screen, fix later
        if (event.getKey() == GLFW.GLFW_KEY_F10) {
            if (!enabled && cooldown == 1) {
                enabled = true;
                ticks = 0;
                cooldown = 0;
                Minecraft.getInstance().player.sendMessage(Component.nullToEmpty(ChatFormatting.GREEN + "[+] Damage Status is enabled!"), Minecraft.getInstance().player.getUUID());
                Minecraft.getInstance().player.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 1, 1);
            } else if (cooldown == 1) {
                enabled = false;
                ticks = 0;
                cooldown = 0;
                Minecraft.getInstance().player.sendMessage(Component.nullToEmpty(ChatFormatting.RED + "[-] Damage Status is disabled."), Minecraft.getInstance().player.getUUID());
                Minecraft.getInstance().player.playSound(SoundEvents.HORSE_HURT, 1, 1);
            }
        }
    }

    @SubscribeEvent
    public void ticks (TickEvent.PlayerTickEvent event) {
        ticks++;
        if (ticks % 20 == 0) {
            cooldown = 1;
        }
    }
}
