package qmul_gameai.gdmc;

import java.util.ArrayList;
import java.util.List;

//import ccx.build.BuildBlocks;
import net.minecraft.block.Block;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
/*
 * Usage: house material x1 y1 z1 length width height
 */
public class BuildHouse_Coord implements ICommand {

	private final List aliases;
	protected ResourceLocation fullEntityName;
	protected Entity conjuredEntity;

	public BuildHouse_Coord() {
		aliases = new ArrayList();
		aliases.add("house");
	}

	@Override
	public boolean isUsernameIndex(String[] var1, int var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int compareTo(ICommand o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "house";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "house <text>";
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return this.aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		World world = sender.getEntityWorld();

		if (world.isRemote) {
			System.out.println("Not processing on Client side");
		} else {
			System.out.println("Processing on Server side");
			if (args.length != 7) {
				//house material x1 y1 z1 len wid height
				sender.sendMessage(new TextComponentString("Invalid argument"));
				return;
			}

			sender.sendMessage(new TextComponentString("house: [" + args[0] + " at "+ args[1]+" ]"));

			//Block BlockName = Block.getBlockFromName(args[0]);
//			if (BlockName == null) {
//				sender.sendMessage(new TextComponentString("Invalid Blockname, enter valid as 'GOLD_BLOCK'"));
//			}

			Block BlockName = Block.getBlockFromName(args[0]);
			if (BlockName == null) {
				sender.sendMessage(new TextComponentString("Invalid Blockname, enter valid as 'GOLD_BLOCK'"));
			}

			//if (args[1].length() != 0) {
				int x1 = Integer.valueOf(args[1]);
				int y1 = Integer.valueOf(args[2]);
				int z1 = Integer.valueOf(args[3]);
				int height = Integer.valueOf(args[4]);
				int width = Integer.valueOf(args[5]);
				int length = Integer.valueOf(args[6]);
				buildHouse(world, BlockName,x1, y1, z1, height, width, length);
				// world.setBlockState(sender.getPosition(), BlockName.getDefaultState());
			} /*else {
				sender.sendMessage(new TextComponentString("enter valid function as 'buildhouse / buildroad'"));
			}
		}*/
	}

	public static void buildHouse(World world, Block block, int x1, int y1, int z1, int height, int width, int length){
		int width2 = width+4;
		int height2 = height+4;
		int x2 = x1-2;
		int z2 = z1-2;
		for (int i = x1; i < x1+width; i++) {
			for (int j = y1; j < y1+length; j++) {
				for (int m = z1; m < z1+height; m++) {
					//use your customize blocks here.
					world.setBlockState(new BlockPos(i, j , m), block.getDefaultState());
				}
			}
		}
		int y2 = y1+length;
		while(width2>=1&&height2>=1){
			for(int o=x2;o<x2+width2;o++){
				for(int p=z2;p<z2+height2;p++){
					world.setBlockState(new BlockPos(o, y2 ,p ), block.getDefaultState());
				}
			}
			width2=width2-2;
			height2=height2-2;
			x2=x2+1;
			z2=z2+1;
			y2=y2+1;
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		// TODO Auto-generated method stub
		return null;
	}
}
