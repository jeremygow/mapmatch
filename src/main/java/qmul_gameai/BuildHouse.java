package qmul_gameai;

import java.util.ArrayList;
import java.util.List;

//import ccx.build.BuildBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/*
 * Usage: house2 GOLD_BLOCK(Material) 20 20 20 (len wid height)
 */
public class BuildHouse implements ICommand{
	private final List aliases;
	  
    //protected ResourceLocation fullEntityName;
    //protected Entity conjuredEntity;
  
    public BuildHouse()
    { 
        aliases = new ArrayList(); 
        aliases.add("house2");
    } 

    @Override 
    public boolean isUsernameIndex(String[] var1, int var2) 
    { 
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
		return "house2";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "house2 <text>";
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
		    
	        if (world.isRemote) 
	        { 
	            System.out.println("Not processing on Client side"); 
	        } 
	        else 
	        { 
	            System.out.println("Processing on Server side"); 
	            if(args.length <= 2)
	            { 
	                sender.sendMessage(new TextComponentString("Invalid argument")); 
	                return; 
	            } 
	    
	            sender.sendMessage(new TextComponentString("house2: [" + args[0]
	                  + "]")); 
	     
	            //fullEntityName = new ResourceLocation(args[0]); 
	            Block BlockName = Block.getBlockFromName(args[0]);
	            if (BlockName == null) {
	            		sender.sendMessage(new TextComponentString("Invalid Blockname, enter valid as 'GOLD_BLOCK'"));
	            }
//	            if (EntityList.isRegistered(fullEntityName))
	            //if (args[1].length() != 0)
	            //{
	            		int height = Integer.valueOf(args[1]);
	            		int width = Integer.valueOf(args[2]);
	            		int length = Integer.valueOf(args[3]);
	                //conjuredEntity = EntityList.createEntityByID(41, world);  
	                
//	                conjuredEntity.setPosition(sender.getPosition().getX(),       
//	                		sender.getPosition().getY(), sender.getPosition().getZ()); 
//	                world.spawnEntity(conjuredEntity); 
				 buildHouse(world, sender.getPosition(), BlockName, height, width, length);
	                //world.setBlockState(sender.getPosition(), BlockName.getDefaultState());
	            //}
	           /* else
	           {
	                sender.sendMessage(new TextComponentString("enter valid function as 'buildhouse / buildroad'")); 
	            } */
	        } 
	}

	public static void buildHouse(World world, BlockPos position, Block block, int height, int width, int length){
		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();
		int width2 = width+4;
		int height2 = height+4;
		int x2 = x-2;
		int z2 = z-2;
		for (int i = x; i <x + width; i++) {
			for (int j = y; j < y + length; j++) {
				for (int m = z; m < z + height; m++) {
					world.setBlockState(new BlockPos(i, j , m), block.getDefaultState());
				}
			}
		}
		int y2 = y+length;
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
