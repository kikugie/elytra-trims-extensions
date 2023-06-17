package dev.kikugie.elytra_trims_extensions;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class Loader implements ClientModInitializer {
    public static final String MOD_ID = "elytra-trims-extensions";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public final Map<String, BooleanSupplier> PACKS = new Hashtable<>();
    @Override
    public void onInitializeClient() {
        PACKS.put("geode-plus", () -> FabricLoader.getInstance().isModLoaded("geode_plus"));
        PACKS.put("flower-adornments", () -> true);

        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(container -> PACKS.forEach((pack, condition) -> {
            if (condition.getAsBoolean()) {
                LOGGER.info("Registering extension pack: " + pack);
                ResourceManagerHelper.registerBuiltinResourcePack(
                        new Identifier(MOD_ID, pack),
                        container,
                        Text.literal("Elytra Trims Extension"),
                        ResourcePackActivationType.NORMAL
                );
            } else {
                LOGGER.info("Skipping pack: " + pack);
            }}));
    }
}
