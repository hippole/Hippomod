/*package io.github.hypermnesiaa.hippomod.damage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class DamageStatus {

    boolean enabled = false;

    int cooldown = 1;
    int ticks = 0;
    int armorProtectionLevel;


    double defense;
    double damage;
    double toughness;

    Gson gson = new Gson();

    @SubscribeEvent
    public void whenAttacked (LivingDamageEvent event) {
        if (enabled) {
            event.getResult();
            if (event.getEntity() instanceof Player) {
                defense = event.getEntityLiving().getAttributeValue(Attributes.ARMOR);
                damage = event.getAmount();
                toughness = event.getEntityLiving().getAttributeValue(Attributes.ARMOR_TOUGHNESS);
                double damageTaken = damage * (1 - (Math.min(20, Math.max(defense / 5, defense - 4 * damage / toughness + 8))) / 25);
                if (event.getEntityLiving().hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
                    double resistance = 1;
                    for (int amplifier = event.getEntityLiving().getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier() + 1; amplifier > 0; amplifier--) {
                        resistance = resistance - 0.20;
                    }
                    resistance = Math.round(resistance * 10.0) / 10.0;
                    if (resistance <= 0) {
                        event.getEntityLiving().sendMessage(Component.nullToEmpty(ChatFormatting.LIGHT_PURPLE + "[%] Invulnerable!"), event.getEntity().getUUID());
                        return;
                    }
                    damageTaken = damageTaken * resistance;
                }
                damageTaken = Math.round(damageTaken * 1000.0) / 1000.0;
                if (!event.getSource().isBypassArmor()) {
                    checkArmor();
                    System.out.println(armorProtectionLevel);
                }
                event.getEntityLiving().sendMessage(Component.nullToEmpty(ChatFormatting.RED + "[-] " + damageTaken + " Health"), event.getEntity().getUUID());
                System.out.println(parseEnchantments(String.valueOf(((Player) event.getEntity()).getInventory().getArmor(0).getEnchantmentTags())));
                System.out.println(((Player) event.getEntity()).getInventory().getArmor(0).getEnchantmentTags());
            }
        }
    }

    @SubscribeEvent
    public void onKeyPress (InputEvent.KeyInputEvent event) {
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

    public void checkArmor () {
        LocalPlayer mc = Minecraft.getInstance().player;
        for (int i = 0; i < 4; i++) {
            System.out.println(parseEnchantments(mc.getInventory().getArmor(i).getEnchantmentTags().toString()));
            int lvl = parseEnchantments(mc.getInventory().getArmor(i).getEnchantmentTags().toString());
            armorProtectionLevel = armorProtectionLevel + ((4 * lvl) / 100);
        }
    }

    public int parseEnchantments (String json) {
        //[{id:"minecraft:protection",lvl:4},{id:"minecraft:unbreaking",lvl:4}]
        //Caused by: java.lang.ClassCastException: class com.google.gson.internal.LinkedTreeMap cannot be cast to class com.google.gson.JsonObject (com.google.gson.internal.LinkedTreeMap and com.google.gson.JsonObject are in module com.google.gson@2.8.8 of loader 'MC-BOOTSTRAP' @1f021e6c)
        JsonArray array = new Gson().fromJson(json, JsonArray.class);
        for (JsonElement element : array) {
            JsonObject object = element.getAsJsonObject();
            if (object.get("id").toString().contains("minecraft:protection")) {
                return Integer.parseInt(String.valueOf(object.get("lvl").toString().charAt(1)));
            }
        }
        return 0;
    }
}
*/