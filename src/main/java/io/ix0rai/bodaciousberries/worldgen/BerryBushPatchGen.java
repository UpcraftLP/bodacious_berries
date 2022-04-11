package io.ix0rai.bodaciousberries.worldgen;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.BerryBush;
import io.ix0rai.bodaciousberries.block.DoubleBerryBush;
import io.ix0rai.bodaciousberries.block.GrowingBerryBush;
import io.ix0rai.bodaciousberries.mixin.accessors.BiomeCategoryAccessor;
import io.ix0rai.bodaciousberries.registry.Bushes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class BerryBushPatchGen {
    private static final int RARE_BERRY_BUSH_RARITY = 80;
    private static final int MEDIUM_BERRY_BUSH_RARITY = 64;
    private static final int COMMON_BERRY_BUSH_RARITY = 40;

    public static Feature<DefaultFeatureConfig> GRAPEVINE_FEATURE;
    public static Feature<DoubleBushFeatureConfig> DOUBLE_BUSH_FEATURE;

    public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_SASKATOON_BERRY;
    public static RegistryEntry<PlacedFeature> PATCH_SASKATOON_BERRY_PLACED;
    public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_STRAWBERRY;
    public static RegistryEntry<PlacedFeature> PATCH_STRAWBERRY_PLACED;
    public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_RASPBERRY;
    public static RegistryEntry<PlacedFeature> PATCH_RASPBERRY_PLACED;
    public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_BLACKBERRY;
    public static RegistryEntry<PlacedFeature> PATCH_BLACKBERRY_PLACED;
    public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_CHORUS_BERRY;
    public static RegistryEntry<PlacedFeature> PATCH_CHORUS_BERRY_PLACED;
    public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_RAINBERRY;
    public static RegistryEntry<PlacedFeature> PATCH_RAINBERRY_PLACED;
    public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_LINGONBERRY;
    public static RegistryEntry<PlacedFeature> PATCH_LINGONBERRY_PLACED;
    public static RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> PATCH_GRAPEVINE;
    public static RegistryEntry<PlacedFeature> PATCH_GRAPEVINE_PLACED;
    public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_GOJI_BERRY;
    public static RegistryEntry<PlacedFeature> PATCH_GOJI_BERRY_PLACED;
    public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_GOOSEBERRY;
    public static RegistryEntry<PlacedFeature> PATCH_GOOSEBERRY_PLACED;
    public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_CLOUDBERRY;
    public static RegistryEntry<PlacedFeature> PATCH_CLOUDBERRY_PLACED;

    public static void registerFeatures() {
        //features
        GRAPEVINE_FEATURE = Registry.register(Registry.FEATURE, Bodaciousberries.id("grapevines"), new GrapevineFeature(DefaultFeatureConfig.CODEC));
        DOUBLE_BUSH_FEATURE = Registry.register(Registry.FEATURE, Bodaciousberries.id("double_bush"), new DoubleBushFeature(DoubleBushFeatureConfig.CODEC));

        //configured features
        PATCH_SASKATOON_BERRY = berryPatchConfiguredFeature("patch_saskatoon_berry", Bushes.SASKATOON_BERRY_BUSH, Bushes.DOUBLE_SASKATOON_BERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_STRAWBERRY = berryPatchConfiguredFeature("patch_strawberry", Bushes.STRAWBERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_BLACKBERRY = berryPatchConfiguredFeature("patch_blackberry", Bushes.BLACKBERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_RASPBERRY = berryPatchConfiguredFeature("patch_raspberry", Bushes.RASPBERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_CHORUS_BERRY = berryPatchConfiguredFeature("patch_chorus_berry", Bushes.CHORUS_BERRY_BUSH, Blocks.END_STONE);
        PATCH_RAINBERRY = berryPatchConfiguredFeature("patch_rainberry", Bushes.RAINBERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_LINGONBERRY = berryPatchConfiguredFeature("patch_lingonberry", Bushes.LINGONBERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_GRAPEVINE = ConfiguredFeatures.register(Bodaciousberries.idString("patch_grapevine"), GRAPEVINE_FEATURE, DefaultFeatureConfig.INSTANCE);
        PATCH_GOJI_BERRY = berryPatchConfiguredFeature("patch_goji_berry", Bushes.GOJI_BERRY_BUSH, Bushes.DOUBLE_GOJI_BERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_GOOSEBERRY = berryPatchConfiguredFeature("patch_gooseberry", Bushes.GOOSEBERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_CLOUDBERRY = berryPatchConfiguredFeature("patch_cloudberry", Bushes.CLOUDBERRY_BUSH, Blocks.GRASS_BLOCK);

        //placed features
        PATCH_SASKATOON_BERRY_PLACED = berryPatchPlacedFeature("patch_saskatoon_berry_placed", COMMON_BERRY_BUSH_RARITY, PATCH_SASKATOON_BERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_STRAWBERRY_PLACED = berryPatchPlacedFeature("patch_strawberry_placed", COMMON_BERRY_BUSH_RARITY, PATCH_STRAWBERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_BLACKBERRY_PLACED = berryPatchPlacedFeature("patch_blackberry_placed", MEDIUM_BERRY_BUSH_RARITY, PATCH_BLACKBERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_RASPBERRY_PLACED = berryPatchPlacedFeature("patch_raspberry_placed", MEDIUM_BERRY_BUSH_RARITY, PATCH_RASPBERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_CHORUS_BERRY_PLACED = berryPatchPlacedFeature("patch_chorus_berry_placed", RARE_BERRY_BUSH_RARITY, PATCH_CHORUS_BERRY, PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP);
        PATCH_RAINBERRY_PLACED = berryPatchPlacedFeature("patch_rainberry_placed", RARE_BERRY_BUSH_RARITY, PATCH_RAINBERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_LINGONBERRY_PLACED = berryPatchPlacedFeature("patch_lingonberry_placed", MEDIUM_BERRY_BUSH_RARITY, PATCH_LINGONBERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_GRAPEVINE_PLACED = PlacedFeatures.register(Bodaciousberries.idString("patch_grapevine_placed"), PATCH_GRAPEVINE,
                List.of(CountPlacementModifier.of(127), HeightRangePlacementModifier.uniform(YOffset.fixed(50), YOffset.fixed(255)), BiomePlacementModifier.of(), RarityFilterPlacementModifier.of(MEDIUM_BERRY_BUSH_RARITY))
        );
        PATCH_GOJI_BERRY_PLACED = berryPatchPlacedFeature("patch_goji_berry_placed", RARE_BERRY_BUSH_RARITY, PATCH_GOJI_BERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_GOOSEBERRY_PLACED = berryPatchPlacedFeature("patch_gooseberry_placed", MEDIUM_BERRY_BUSH_RARITY, PATCH_GOOSEBERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_CLOUDBERRY_PLACED = berryPatchPlacedFeature("patch_cloudberry_placed", RARE_BERRY_BUSH_RARITY, PATCH_CLOUDBERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);

        //register them for generation
        final List<Biome.Category> saskatoonBerryCategories = List.of(Biome.Category.FOREST, Biome.Category.TAIGA, Biome.Category.MOUNTAIN);
        final List<Biome.Category> strawberryCategories = List.of(Biome.Category.PLAINS, Biome.Category.FOREST, Biome.Category.SWAMP);
        final List<Biome.Category> lingonberryCategories = List.of(Biome.Category.TAIGA, Biome.Category.FOREST);
        final List<Biome.Category> gojiBerryCategories = List.of(Biome.Category.MOUNTAIN, Biome.Category.EXTREME_HILLS);
        final List<Biome.Category> gooseberryCategories = List.of(Biome.Category.FOREST, Biome.Category.MOUNTAIN);

        generateBerryPatches("add_saskatoon_berry_patches", saskatoonBerryCategories, PATCH_SASKATOON_BERRY_PLACED);
        generateBerryPatches("add_strawberry_patches", strawberryCategories, PATCH_STRAWBERRY_PLACED);
        generateBerryPatches("add_raspberry_patches", saskatoonBerryCategories, PATCH_RASPBERRY_PLACED);
        generateBerryPatches("add_blackberry_patches", saskatoonBerryCategories, PATCH_BLACKBERRY_PLACED);
        generateBerryPatches("add_chorus_berry_patches", List.of(Biome.Category.THEEND), PATCH_CHORUS_BERRY_PLACED);
        generateBerryPatches("add_rainberry_patches", strawberryCategories, PATCH_RAINBERRY_PLACED);
        generateBerryPatches("add_lingonberry_patches", lingonberryCategories, PATCH_LINGONBERRY_PLACED);
        generateBerryPatches("add_grapevine_patches", List.of(Biome.Category.JUNGLE), PATCH_GRAPEVINE_PLACED);
        generateBerryPatches("add_goji_berry_patches", gojiBerryCategories, PATCH_GOJI_BERRY_PLACED);
        generateBerryPatches("add_gooseberry_patches", gooseberryCategories, PATCH_GOOSEBERRY_PLACED);
        //cloudberries generated below the minimum height will just die :(
        generateBerryPatches("add_cloudberry_patches", List.of(Biome.Category.MOUNTAIN), PATCH_CLOUDBERRY_PLACED);
    }

    /**
     * creates and registers a berry patch feature, with the default age of the berry bush being the maximum age of said berry bush
     * @param name the name of the feature
     * @param bush the bush to generate
     * @param placedOn which block the bush should be placed on
     * @return a berry patch configured feature
     */
    public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> berryPatchConfiguredFeature(String name, BerryBush bush, Block placedOn) {
        return ConfiguredFeatures.register(Bodaciousberries.idString(name), Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(bush.getBaseState().with(bush.getAge(), bush.getMaxAge()))),
                        List.of(placedOn)
                )
        );
    }

    /**
     * creates and registers a berry patch feature, with the default age of the berry bush being the maximum age of said berry bush
     * <br>also generates the double version of said berry bush alongside it
     * @param name the name of the feature
     * @param smallBush the bush to generate
     * @param tallBush the tall version of the bush
     * @param placedOn which block the bush should be placed on
     * @return a berry patch configured feature
     */
    public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> berryPatchConfiguredFeature(String name, GrowingBerryBush smallBush, DoubleBerryBush tallBush, Block placedOn) {
        return ConfiguredFeatures.register(Bodaciousberries.idString(name), Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(DOUBLE_BUSH_FEATURE,
                        new DoubleBushFeatureConfig(BlockStateProvider.of(smallBush.getBaseState().with(smallBush.getAge(), smallBush.getMaxAge())),
                                BlockStateProvider.of(tallBush.getDefaultState().with(DoubleBerryBush.AGE, tallBush.getMaxAge()))),
                        List.of(placedOn)
                )
        );
    }

    /**
     * creates and registers a placed berry patch feature
     * @param name the name of the feature
     * @param rarity the rarity of the feature: lower is more common
     * @param feature the feature to place
     * @return a placed berry patch feature
     */
    public static RegistryEntry<PlacedFeature> berryPatchPlacedFeature(String name, int rarity, RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> feature, PlacementModifier heightMap) {
        return PlacedFeatures.register(Bodaciousberries.idString(name), feature,
                List.of(
                        RarityFilterPlacementModifier.of(rarity),
                        SquarePlacementModifier.of(),
                        heightMap
                )
        );
    }

    /**
     * generates the placed berry patch feature in the specified biomes
     * @param name the name of the feature
     * @param categories the biomes to generate the feature in
     * @param placedFeature the feature to place
     */
    public static void generateBerryPatches(String name, List<Biome.Category> categories, RegistryEntry<PlacedFeature> placedFeature) {
        BiomeModifications.create(Bodaciousberries.id(name)).add(
                ModificationPhase.ADDITIONS,
                //decide if the biome should receive our feature
                context -> {
                    Biome.Category category = ((BiomeCategoryAccessor) (Object) context.getBiome()).getCategory();
                    return categories.contains(category) && category != Biome.Category.NONE;
                },
                //add feature to the biome under the step vegetal decoration
                context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, placedFeature.value())
        );
    }
}
