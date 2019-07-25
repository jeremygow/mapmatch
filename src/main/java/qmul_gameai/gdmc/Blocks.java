package qmul_gameai.gdmc;

import java.util.ArrayList;
import java.util.List;

public class Blocks {

    public final static String AIR = "Air";
    public final static String WATER = "Water";

    // Blocks to ignore
    public final static String FLOWER = "Flower";
    public final static String GRASS = "Grass";
    public final static String LEAVES = "Leaves";
    public final static String PLANT = "Plant";
    public final static String MUSHROOM = "Mushroom";
    public final static String WOOD = "Wood";
    public final static String PUMPKIN = "Pumpkin";
    public final static String SUGAR_CANE = "Sugar cane";
    public final static String VINES = "Vines";
    public final static String LILY = "Lily Pad";
    public final static String WOODEN_PLANKS = "Wooden Planks";
    public final static String SPRUCE_STAIRS = "Spruce Wood Stairs";

    // Terrain blocks
    public final static String LAVA = "Lava";
    public final static String IRON_ORE = "Iron Ore";
    public final static String COAL_ORE = "Coal Ore";
    public final static String STONE = "Stone";
    public final static String SNOW = "Snow";
    public final static String DIRT = "Dirt";
    public final static String GRASS_BLOCK = "Grass Block";
    public final static String SAND = "Sand";
    public final static String MAGMA_BLOCK = "Magma Block";
    public final static String GRAVEL = "Gravel";
    public final static String SANDSTONE = "Sandstone";


    public static List<String> ignoreBlocks() {

        List<String> ignore = new ArrayList<String>();
        ignore.add(FLOWER);
        ignore.add(GRASS);
        ignore.add(LEAVES);
        ignore.add(PLANT);
        ignore.add(MUSHROOM);
        ignore.add(WOOD);
        ignore.add(PUMPKIN);
        ignore.add(SUGAR_CANE);
        ignore.add(VINES);
        return ignore;
    }

    public static List<String> terrainBlocks() {

        List<String> terrain = new ArrayList<String>();
        terrain.add(STONE);
        terrain.add(SNOW);
        terrain.add(DIRT);
        terrain.add(COAL_ORE);
        terrain.add(IRON_ORE);
        terrain.add(IRON_ORE);
        terrain.add(LAVA);
        terrain.add(GRASS_BLOCK);
        terrain.add(SAND);
        terrain.add(MAGMA_BLOCK);
        terrain.add(GRAVEL);
        terrain.add(SANDSTONE);
        return terrain;
    }





}

