package qmul_gameai.gdmc;

import net.minecraft.command.*;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import javax.annotation.Nullable;
import java.util.*;

/*
 * Command: cube BLOCK_TYPE SIZE
 *
 * Builds a cube of the given type and size at the current location.
 * Based on https://github.com/dtccx/java_platform/
 */
public class BuildCube implements ICommand {

    private final List aliases;

    public BuildCube() {
        aliases = new ArrayList();
    }

    @Override
    public String getName() {
        return "cube";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "cube <text>";
    }

    @Override
    public List<String> getAliases() {
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        World world = sender.getEntityWorld();

        if (world.isRemote)
        {
            System.out.println("Not processing on Client side");
        }
        else
        {
            System.out.println("Processing on Server side");
            if(args.length < 2)
            {
                sender.sendMessage(new TextComponentString("Invalid arguments"));
                return;
            }

            sender.sendMessage(new TextComponentString("cmd: [" + args[0]
                    + "]"));

            Block block = Block.getBlockFromName(args[0]);
            if (block == null) {
                sender.sendMessage(new TextComponentString("Invalid block name, enter e.g. GOLD_BLOCK"));
            }
            int size = Integer.valueOf(args[1]);
            buildCube(world, sender.getPosition(), block, size);
        }
    }

    public static void buildCube(World world, BlockPos position, Block block, int size) {
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();
        for (int i = x; i <= x + size; i++) {
            for (int j = y; j < y + size; j++) {
                for (int m = z; m < z + size; m++) {
                    world.setBlockState(new BlockPos(i, j , m), block.getDefaultState());
                }
            }
        }
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



