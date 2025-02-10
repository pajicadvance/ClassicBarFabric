package me.pajic.classic_bars_fabric.config;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import me.pajic.classic_bars_fabric.Main;
import me.pajic.classic_bars_fabric.gui.GuiHandler;
import me.pajic.classic_bars_fabric.util.ColorUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.List;

public class ModConfig {

    public static ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath(Main.MODID, "mod_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve(Main.MODID + ".json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build()
            ).build();
    
    @SerialEntry public static boolean displayIcons = true;
    @SerialEntry public static boolean displayToughnessBar = true;
    @SerialEntry public static boolean fullAbsorptionBar = false;
    @SerialEntry public static boolean fullArmorBar = false;
    @SerialEntry public static boolean fullToughnessBar = false;
    @SerialEntry public static boolean lowArmorWarning = true;
    @SerialEntry public static boolean showSaturationBar = true;
    @SerialEntry public static boolean showHydrationBar = true;
    @SerialEntry public static boolean showHeldFoodOverlay = true;
    @SerialEntry public static boolean showHeldDrinkOverlay = true;
    @SerialEntry public static boolean showExhaustionOverlay = true;
    @SerialEntry public static boolean showThirstExhaustionOverlay = true;
    @SerialEntry public static double transitionSpeed = 3;

    @SerialEntry public static boolean healthText = true;
    @SerialEntry public static boolean hungerText = true;
    @SerialEntry public static boolean absorptionText = true;
    @SerialEntry public static boolean armorText = true;
    @SerialEntry public static boolean armorToughnessText = true;
    @SerialEntry public static boolean airText = true;
    @SerialEntry public static boolean mountHealthText = true;
    @SerialEntry public static boolean thirstText = true;

    @SerialEntry public static Color hungerBarColor = Color.decode("#B34D00");
    @SerialEntry public static Color hungerBarDebuffColor = Color.decode("#249016");
    @SerialEntry public static Color saturationBarColor = Color.decode("#FFCC00");
    @SerialEntry public static Color saturationBarDebuffColor = Color.decode("#87BC00");
    @SerialEntry public static Color airBarColor = Color.decode("#00E6E6");
    @SerialEntry public static Color frozenHealthColor = Color.decode("#7FAFFF");
    @SerialEntry public static Color thirstBarColor = Color.decode("#1C5EE4");
    @SerialEntry public static Color thirstBarDebuffColor = Color.decode("#5A891C");
    @SerialEntry public static Color hydrationBarColor = Color.decode("#00A3E2");
    @SerialEntry public static Color hydrationBarDebuffColor = Color.decode("#85CF25");
    @SerialEntry public static List<Color> armorColors = ColorUtils.string2AwtColorList(Lists.newArrayList("#AAAAAA", "#FF5500", "#FFC747", "#27FFE3", "#00FF00", "#7F00FF"));
    @SerialEntry public static List<Color> armorToughnessColors = ColorUtils.string2AwtColorList(Lists.newArrayList("#AAAAAA", "#FF5500", "#FFC747", "#27FFE3", "#00FF00", "#7F00FF"));
    @SerialEntry public static List<Color> absorptionColors = ColorUtils.string2AwtColorList(Lists.newArrayList("#D4AF37", "#C2C73B", "#8DC337", "#36BA77", "#4A5BC4", "#D89AE2", "#DF9DC7", "#DFA99D", "#D4DF9D", "#3E84C6", "#B8C1E8", "#DFDFDF"));
    @SerialEntry public static List<Color> absorptionPoisonColors = ColorUtils.string2AwtColorList(Lists.newArrayList("#D4AF37", "#C2C73B", "#8DC337", "#36BA77", "#4A5BC4", "#D89AE2", "#DF9DC7", "#DFA99D", "#D4DF9D", "#3E84C6", "#B8C1E8", "#DFDFDF"));
    @SerialEntry public static List<Color> absorptionWitherColors = ColorUtils.string2AwtColorList(Lists.newArrayList("#D4AF37", "#C2C73B", "#8DC337", "#36BA77", "#4A5BC4", "#D89AE2", "#DF9DC7", "#DFA99D", "#D4DF9D", "#3E84C6", "#B8C1E8", "#DFDFDF"));
    @SerialEntry public static List<Double> normalFractions = Lists.newArrayList(.25, .5, .75);
    @SerialEntry public static List<Color> normalColors = ColorUtils.string2AwtColorList(Lists.newArrayList("#FF0000", "#FFFF00", "#00FF00"));
    @SerialEntry public static List<Double> poisonedFractions = Lists.newArrayList(.25, .5, .75);
    @SerialEntry public static List<Color> poisonedColors = ColorUtils.string2AwtColorList(Lists.newArrayList("#00FF00", "#55FF55", "#00FF00"));
    @SerialEntry public static List<Double> witheredFractions = Lists.newArrayList(.25, .5, .75);
    @SerialEntry public static List<Color> witheredColors = ColorUtils.string2AwtColorList(Lists.newArrayList("#555555", "#AAAAAA", "#555555"));

    @SerialEntry public static List<String> leftorder = Lists.newArrayList("health","armor","absorption","lavacharm","lavacharm2");
    @SerialEntry public static List<String> rightorder = Lists.newArrayList("blood","health_mount","food","thirst_level","feathers","armor_toughness","air","flighttiara","decay");

    public static Screen makeScreen(Screen parentScreen) {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> builder
                .title(Component.translatable("classicbar.config.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("classicbar.config.category.display"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.displayIcons"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.displayIcons.desc")))
                                .binding(displayIcons, () -> displayIcons, newValue -> displayIcons = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.displayToughnessBar"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.displayToughnessBar.desc")))
                                .binding(displayToughnessBar, () -> displayToughnessBar, newValue -> displayToughnessBar = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.fullAbsorptionBar"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.fullAbsorptionBar.desc")))
                                .binding(fullAbsorptionBar, () -> fullAbsorptionBar, newValue -> fullAbsorptionBar = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.fullArmorBar"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.fullArmorBar.desc")))
                                .binding(fullArmorBar, () -> fullArmorBar, newValue -> fullArmorBar = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.fullToughnessBar"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.fullToughnessBar.desc")))
                                .binding(fullToughnessBar, () -> fullToughnessBar, newValue -> fullToughnessBar = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.lowArmorWarning"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.lowArmorWarning.desc")))
                                .binding(lowArmorWarning, () -> lowArmorWarning, newValue -> lowArmorWarning = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.showSaturationBar"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.showSaturationBar.desc")))
                                .binding(showSaturationBar, () -> showSaturationBar, newValue -> showSaturationBar = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.showHydrationBar"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.showHydrationBar.desc")))
                                .binding(showHydrationBar, () -> showHydrationBar, newValue -> showHydrationBar = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.showHeldFoodOverlay"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.showHeldFoodOverlay.desc")))
                                .binding(showHeldFoodOverlay, () -> showHeldFoodOverlay, newValue -> showHeldFoodOverlay = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.showHeldDrinkOverlay"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.showHeldDrinkOverlay.desc")))
                                .binding(showHeldDrinkOverlay, () -> showHeldDrinkOverlay, newValue -> showHeldDrinkOverlay = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.showExhaustionOverlay"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.showExhaustionOverlay.desc")))
                                .binding(showExhaustionOverlay, () -> showExhaustionOverlay, newValue -> showExhaustionOverlay = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.showThirstExhaustionOverlay"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.showThirstExhaustionOverlay.desc")))
                                .binding(showThirstExhaustionOverlay, () -> showThirstExhaustionOverlay, newValue -> showThirstExhaustionOverlay = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Double>createBuilder()
                                .name(Component.translatable("classicbar.config.transitionSpeed"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.transitionSpeed.desc")))
                                .binding(transitionSpeed, () -> transitionSpeed, newValue -> transitionSpeed = newValue)
                                .controller(opt -> DoubleFieldControllerBuilder.create(opt).range(0.0, Double.MAX_VALUE))
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("classicbar.config.category.text"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.healthText"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.healthText.desc")))
                                .binding(healthText, () -> healthText, newValue -> healthText = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.hungerText"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.hungerText.desc")))
                                .binding(hungerText, () -> hungerText, newValue -> hungerText = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.absorptionText"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.absorptionText.desc")))
                                .binding(absorptionText, () -> absorptionText, newValue -> absorptionText = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.armorText"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.armorText.desc")))
                                .binding(armorText, () -> armorText, newValue -> armorText = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.armorToughnessText"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.armorToughnessText.desc")))
                                .binding(armorToughnessText, () -> armorToughnessText, newValue -> armorToughnessText = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.airText"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.airText.desc")))
                                .binding(airText, () -> airText, newValue -> airText = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.mountHealthText"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.mountHealthText.desc")))
                                .binding(mountHealthText, () -> mountHealthText, newValue -> mountHealthText = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("classicbar.config.thirstText"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.thirstText.desc")))
                                .binding(thirstText, () -> thirstText, newValue -> thirstText = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("classicbar.config.category.colors"))
                        .option(Option.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.hungerBarColor"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.hungerBarColor.desc")))
                                .binding(hungerBarColor, () -> hungerBarColor, newValue -> hungerBarColor = newValue)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.hungerBarDebuffColor"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.hungerBarDebuffColor.desc")))
                                .binding(hungerBarDebuffColor, () -> hungerBarDebuffColor, newValue -> hungerBarDebuffColor = newValue)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.saturationBarColor"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.saturationBarColor.desc")))
                                .binding(saturationBarColor, () -> saturationBarColor, newValue -> saturationBarColor = newValue)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.saturationBarDebuffColor"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.saturationBarDebuffColor.desc")))
                                .binding(saturationBarDebuffColor, () -> saturationBarDebuffColor, newValue -> saturationBarDebuffColor = newValue)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.airBarColor"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.airBarColor.desc")))
                                .binding(airBarColor, () -> airBarColor, newValue -> airBarColor = newValue)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.frozenHealthColor"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.frozenHealthColor.desc")))
                                .binding(frozenHealthColor, () -> frozenHealthColor, newValue -> frozenHealthColor = newValue)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.thirstBarColor"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.thirstBarColor.desc")))
                                .binding(thirstBarColor, () -> thirstBarColor, newValue -> thirstBarColor = newValue)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.thirstBarDebuffColor"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.thirstBarDebuffColor.desc")))
                                .binding(thirstBarDebuffColor, () -> thirstBarDebuffColor, newValue -> thirstBarDebuffColor = newValue)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.hydrationBarColor"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.hydrationBarColor.desc")))
                                .binding(hydrationBarColor, () -> hydrationBarColor, newValue -> hydrationBarColor = newValue)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.hydrationBarDebuffColor"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.hydrationBarDebuffColor.desc")))
                                .binding(hydrationBarDebuffColor, () -> hydrationBarDebuffColor, newValue -> hydrationBarDebuffColor = newValue)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .option(ListOption.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.armorColors"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.armorColors.desc")))
                                .binding(armorColors, () -> armorColors, newValue -> armorColors = newValue)
                                .controller(ColorControllerBuilder::create)
                                .initial(Color.WHITE)
                                .build())
                        .option(ListOption.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.armorToughnessColors"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.armorToughnessColors.desc")))
                                .binding(armorToughnessColors, () -> armorToughnessColors, newValue -> armorToughnessColors = newValue)
                                .controller(ColorControllerBuilder::create)
                                .initial(Color.WHITE)
                                .build())
                        .option(ListOption.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.absorptionColors"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.absorptionColors.desc")))
                                .binding(absorptionColors, () -> absorptionColors, newValue -> absorptionColors = newValue)
                                .controller(ColorControllerBuilder::create)
                                .initial(Color.WHITE)
                                .build())
                        .option(ListOption.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.absorptionPoisonColors"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.absorptionPoisonColors.desc")))
                                .binding(absorptionPoisonColors, () -> absorptionPoisonColors, newValue -> absorptionPoisonColors = newValue)
                                .controller(ColorControllerBuilder::create)
                                .initial(Color.WHITE)
                                .build())
                        .option(ListOption.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.absorptionWitherColors"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.absorptionWitherColors.desc")))
                                .binding(absorptionWitherColors, () -> absorptionWitherColors, newValue -> absorptionWitherColors = newValue)
                                .controller(ColorControllerBuilder::create)
                                .initial(Color.WHITE)
                                .build())
                        .option(ListOption.<Double>createBuilder()
                                .name(Component.translatable("classicbar.config.normalFractions"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.normalFractions.desc")))
                                .binding(normalFractions, () -> normalFractions, newValue -> normalFractions = newValue)
                                .controller(opt -> DoubleFieldControllerBuilder.create(opt).range(0.0, 1.0))
                                .initial(0.0)
                                .build())
                        .option(ListOption.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.normalColors"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.normalColors.desc")))
                                .binding(normalColors, () -> normalColors, newValue -> normalColors = newValue)
                                .controller(ColorControllerBuilder::create)
                                .initial(Color.WHITE)
                                .build())
                        .option(ListOption.<Double>createBuilder()
                                .name(Component.translatable("classicbar.config.poisonedFractions"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.poisonedFractions.desc")))
                                .binding(poisonedFractions, () -> poisonedFractions, newValue -> poisonedFractions = newValue)
                                .controller(opt -> DoubleFieldControllerBuilder.create(opt).range(0.0, 1.0))
                                .initial(0.0)
                                .build())
                        .option(ListOption.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.poisonedColors"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.poisonedColors.desc")))
                                .binding(poisonedColors, () -> poisonedColors, newValue -> poisonedColors = newValue)
                                .controller(ColorControllerBuilder::create)
                                .initial(Color.WHITE)
                                .build())
                        .option(ListOption.<Double>createBuilder()
                                .name(Component.translatable("classicbar.config.witheredFractions"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.witheredFractions.desc")))
                                .binding(witheredFractions, () -> witheredFractions, newValue -> witheredFractions = newValue)
                                .controller(opt -> DoubleFieldControllerBuilder.create(opt).range(0.0, 1.0))
                                .initial(0.0)
                                .build())
                        .option(ListOption.<Color>createBuilder()
                                .name(Component.translatable("classicbar.config.witheredColors"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.witheredColors.desc")))
                                .binding(witheredColors, () -> witheredColors, newValue -> witheredColors = newValue)
                                .controller(ColorControllerBuilder::create)
                                .initial(Color.WHITE)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("classicbar.config.category.barOrder"))
                        .option(ListOption.<String>createBuilder()
                                .name(Component.translatable("classicbar.config.leftorder"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.leftorder.desc")))
                                .binding(leftorder, () -> leftorder, newValue -> leftorder = newValue)
                                .controller(StringControllerBuilder::create)
                                .addListener((option, event) -> {
                                    if (event == OptionEventListener.Event.STATE_CHANGE) GuiHandler.setupSides();
                                })
                                .initial("")
                                .build())
                        .option(ListOption.<String>createBuilder()
                                .name(Component.translatable("classicbar.config.rightorder"))
                                .description(OptionDescription.of(Component.translatable("classicbar.config.rightorder.desc")))
                                .binding(rightorder, () -> rightorder, newValue -> rightorder = newValue)
                                .controller(StringControllerBuilder::create)
                                .addListener((option, event) -> {
                                    if (event == OptionEventListener.Event.STATE_CHANGE) GuiHandler.setupSides();
                                })
                                .initial("")
                                .build())
                        .build())
        ).generateScreen(parentScreen);
    }


}
