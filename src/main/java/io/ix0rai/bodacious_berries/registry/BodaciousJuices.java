package io.ix0rai.bodacious_berries.registry;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.item.Blend;
import io.ix0rai.bodacious_berries.item.ChorusBerryJuice;
import io.ix0rai.bodacious_berries.item.EndBlend;
import io.ix0rai.bodacious_berries.item.GojiBerryBlend;
import io.ix0rai.bodacious_berries.item.Juice;
import io.ix0rai.bodacious_berries.util.JuicerRecipeUtil;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.biome.BiomeKeys.*;

public class BodaciousJuices {
    public static final Item JUICE_RECEPTACLE = Items.GLASS_BOTTLE;
    public static final Item.Settings JUICE_SETTINGS = new Item.Settings().recipeRemainder(JUICE_RECEPTACLE).maxCount(16);
    public static final List<JsonObject> RECIPES = new ArrayList<>();
    public static final Juice DUBIOUS_JUICE = new Juice(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(2).saturationModifier(2F).build()));

    public static void register() {
        register(BodaciousBerries.id("dubious_juice"), DUBIOUS_JUICE);
        register("saskatoon_berry_juice", new Juice(BodaciousItems.SASKATOON_BERRIES, BodaciousBerries.translatableText("hint.more_purple")));
        register("strawberry_juice", new Juice(BodaciousItems.STRAWBERRIES));
        register("raspberry_juice", new Juice(BodaciousItems.RASPBERRIES));
        register("blackberry_juice", new Juice(BodaciousItems.BLACKBERRIES, BodaciousBerries.translatableText("hint.blackberries_and_sweetness")));
        register("rainberry_juice", new Juice(BodaciousItems.RAINBERRIES));
        register("lingonberry_juice", new Juice(BodaciousItems.LINGONBERRIES, BodaciousBerries.translatableText("hint.more_red")));
        register("grape_juice", new Juice(BodaciousItems.GRAPES));
        register("goji_berry_juice", new Juice(BodaciousItems.GOJI_BERRIES, BodaciousBerries.translatableText("hint.sweetness")));
        register("gooseberry_juice", new Juice(BodaciousItems.GOOSEBERRIES, BodaciousBerries.translatableText("hint.red_and_yellow")));
        register("glow_berry_juice", new Juice(Items.GLOW_BERRIES, new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 90, 1), 1.0F)));
        register("sweet_berry_juice", new Juice(Items.SWEET_BERRIES, BodaciousBerries.translatableText("hint.vanilla")));
        register("chorus_berry_juice", new ChorusBerryJuice(BodaciousItems.CHORUS_BERRIES, null));
        register("cloudberry_juice", new Juice(BodaciousItems.CLOUDBERRIES, new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 1200, 1), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 600, 1), 1.0f)));

        createBiomeChorusJuice();
        registerBlends();
    }

    private static void createBiomeChorusJuice() {
        RegistryKey<?>[] biomes = new RegistryKey<?>[]{
                PLAINS, SNOWY_SLOPES, SWAMP,
                DESERT, TAIGA, BIRCH_FOREST,
                OCEAN, MUSHROOM_FIELDS, SUNFLOWER_PLAINS,
                FOREST, FLOWER_FOREST, DARK_FOREST,
                SAVANNA, BADLANDS, MEADOW,
                LUSH_CAVES, DRIPSTONE_CAVES, JUNGLE
        };

        Item[] biomeItems = new Item[]{
                Items.CORNFLOWER, Items.SNOWBALL, Items.CLAY_BALL,
                Items.SAND, Items.SWEET_BERRIES, Items.BIRCH_SAPLING,
                Items.COD, Items.RED_MUSHROOM, Items.SUNFLOWER,
                Items.OAK_SAPLING, Items.PEONY, Items.DARK_OAK_SAPLING,
                Items.ACACIA_SAPLING, Items.RED_SAND, Items.POPPY,
                Items.GLOW_BERRIES, Items.POINTED_DRIPSTONE, Items.COCOA_BEANS
        };

        for (int i = 0; i < biomes.length; i++) {
            // create juice and get id
            RegistryKey<?> key = biomes[i];
            ChorusBerryJuice juice = new ChorusBerryJuice(BodaciousItems.CHORUS_BERRIES, key.getValue());
            Identifier id = BodaciousBerries.id("chorus_berry_juice_" + key.getValue().getPath());

            // create recipe as json object
            JsonObject recipeJson = new JsonObject();
            recipeJson.addProperty("type", "minecraft:crafting_shapeless");
            JsonArray ingredientArray = new JsonArray();
            ingredientArray.add(getAsProperty(Registries.ITEM.getId(biomeItems[i])));
            ingredientArray.add(getAsProperty(BodaciousBerries.id("chorus_berry_juice")));
            recipeJson.add("ingredients", ingredientArray);
            JsonObject result = getAsProperty(id);
            result.addProperty("count", 1);
            recipeJson.add("result", result);

            // register recipe and item
            RECIPES.add(recipeJson);
            Registry.register(Registries.ITEM, id, juice);
        }
    }

    private static JsonObject getAsProperty(Identifier id) {
        JsonObject property = new JsonObject();
        property.addProperty("item", id.toString());
        return property;
    }

    private static void registerBlends() {
        Blend gojiBerryBlend = new GojiBerryBlend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(5).saturationModifier(1.8F).statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 800, 1), 1.0F).build()), BodaciousItems.GOJI_BERRIES, BodaciousItems.GOJI_BERRIES, Items.SUGAR_CANE);
        registerBlend("goji_berry_blend", gojiBerryBlend);
        Blend oppositeJuice = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(4).saturationModifier(1.2F).build()), BodaciousItems.RASPBERRIES, BodaciousItems.BLACKBERRIES, Items.SUGAR_CANE);
        registerBlend("opposite_juice", oppositeJuice);
        Blend rainberryBlend = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(6).saturationModifier(1.6F).build()), BodaciousItems.RAINBERRIES, BodaciousItems.GOJI_BERRIES, Items.GOLDEN_APPLE);
        registerBlend("rainberry_blend", rainberryBlend);
        Blend gooseberryRum = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(7).saturationModifier(1.0f).build()), BodaciousItems.GOOSEBERRIES, BodaciousItems.GOOSEBERRIES, Items.WHEAT);
        registerBlend("gooseberry_rum", gooseberryRum);
        Blend redJuice = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(5).saturationModifier(1.3F).build()), BodaciousItems.STRAWBERRIES, Items.SWEET_BERRIES, BodaciousItems.LINGONBERRIES);
        registerBlend("red_juice", redJuice);
        Blend endBlend = new EndBlend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(3).saturationModifier(3.3F).build()), BodaciousItems.CHORUS_BERRIES, BodaciousItems.RAINBERRIES, Items.CHORUS_FRUIT);
        registerBlend("end_blend", endBlend);
        Blend purpleDelight = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(6).saturationModifier(1.6F).build()), BodaciousItems.CHORUS_BERRIES, BodaciousItems.GRAPES, BodaciousItems.SASKATOON_BERRIES);
        registerBlend("purple_delight", purpleDelight);
        Blend trafficLightJuice = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(5).saturationModifier(1.4F).build()), BodaciousItems.GOOSEBERRIES, Items.GLOW_BERRIES, BodaciousItems.RASPBERRIES);
        registerBlend("traffic_light_juice", trafficLightJuice);
        Blend vanillaDelight = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(4).saturationModifier(2.0F).build()), Items.GLOW_BERRIES, Items.SWEET_BERRIES, Items.APPLE);
        registerBlend("vanilla_delight", vanillaDelight);
    }

    private static void register(String name, Juice juice) {
        Identifier id = BodaciousBerries.id(name);
        JuicerRecipeUtil.registerJuiceRecipe(new Identifier("c", Registries.ITEM.getId(juice.getBerry()).getPath()), id);
        register(id, juice);
    }

    private static void registerBlend(String name, Blend blend) {
        Identifier id = BodaciousBerries.id(name);
        register(id, blend);
        JuicerRecipeUtil.registerJuiceRecipe(Registries.ITEM.getId(blend.getIngredient0()), Registries.ITEM.getId(blend.getIngredient1()), Registries.ITEM.getId(blend.getIngredient2()), id);
    }

    private static void register(Identifier id, Juice juice) {
        Item item = Registry.register(Registries.ITEM, id, juice);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addItem(item));
    }
}
