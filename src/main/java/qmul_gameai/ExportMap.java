package qmul_gameai;

import net.minecraft.command.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;


import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/*
 * Command: exportmap X Y DX (DY)
 * "em" will export 100x100 map at origin
 *
 * Exports 2D map data for rectangle corner X,Y and sides DX,DY
 */
public class ExportMap implements ICommand {

    private final List aliases;

    public static String AIR = "Air";
    public static String WATER = "Water";
    public static String FLOWER = "Flower";
    public static String GRASS = "Grass";
    public static String LEAVES = "Leaves";
    public static String PLANT = "Plant";
    public static String MUSHROOM = "Mushroom";
    public static String WOOD = "Wood";


    public ExportMap() {
        aliases = new ArrayList();
        aliases.add("export");
        aliases.add("em");
    }

    @Override
    public String getName() {
        return "exportmap";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "exportmap <text>";
    }

    @Override
    public List<String> getAliases() {
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        World world = sender.getEntityWorld();


        if (world.isRemote) {
            System.out.println("Not processing on Client side");
        } else {

            int x = args.length > 0 ? Integer.valueOf(args[0]) : 0;
            int z = args.length > 1 ? Integer.valueOf(args[1]) : 0;
            int dx = args.length > 2 ? Integer.valueOf(args[2]) : 100;
            int dz = args.length > 3 ? Integer.valueOf(args[3]) : dx;

            if (dx > 0 && dz > 0) {
                export(world, x, z, dx, dz);
            } else {
                sender.sendMessage(new TextComponentString("specify non-zero area of map to export"));
            }
        }
    }

    public void export(World world, int x, int z, int dx, int dz) {

        String name = "_" + x + "_" + z + "_" + dx + "_" + dz;
        File file = new File(world.getWorldInfo().getWorldName() + name + ".csv");
        try {
            FileWriter writer = new FileWriter(file);
            System.out.println("exporting to " + file.getAbsolutePath());
            export(writer, world,x, z, dx, dz);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void export(Writer writer, World world, int x1, int z1, int dx, int dz) throws IOException {

        List<String> ignore = new ArrayList<String>();
        ignore.add(FLOWER);
        ignore.add(GRASS);
        ignore.add(LEAVES);
        ignore.add(PLANT);
        ignore.add(MUSHROOM);
        ignore.add(WOOD);

        Set<String> terrainTypes = new HashSet<String>();

        writer.write("x, z, height, water, top, terrain\n");

        for (int x = x1; x < x1 + dx; x++) {
            for (int z = z1; z < z1 + dz; z++) {

                int height = 256;
                boolean water = false;
                String top = null;
                String terrain = "";

                for (int y = 256; y > -1; y--) {
                    BlockPos pos = new BlockPos(x, y, z);
                    IBlockState state = world.getBlockState(pos);
                    Block block = state.getBlock();
                    String name = block.getLocalizedName();

                    if (!name.equals(AIR)) {
                        if (ignore.contains(name)) {
                            if (top == null) {
                                top = name;
                            }
                        } else {
                            terrain = name;
                            terrainTypes.add(name);
                            water = name.equals(WATER);
                            height = y;
                            break;
                        }
                    }
                }

                writer.write(x + ", ");
                writer.write(z + ", ");

                writer.write(height + ", ");
                writer.write(water + ", ");
                writer.write(top + ", ");
                writer.write(terrain + "\n");
            }
        }

        System.out.println("Terrain: " + terrainTypes.toString());
    }


    /*
     * Other ICommand methods
     */

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }


    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}



