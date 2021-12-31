package io.github.hypermnesiaa.hippomod;

import io.github.hypermnesiaa.hippomod.damage.DamageStatus;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("hippomod")
public class Hippomod {
    private static final Logger LOGGER = LogManager.getLogger();

    private final DamageStatus damageStatus = new DamageStatus();

    public Hippomod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(damageStatus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup (final FMLCommonSetupEvent event) {
        LOGGER.info("Hola! Soy Dora! Can you say 'Cometí homicidio vehicular'? That means I committed vehicular manslaughter in span-");
    }

}
