package qmul_gameai;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = MapMatch.MODID, name = MapMatch.NAME, version = MapMatch.VERSION)
public class MapMatch {

    public static final String MODID = "mapmatch";
    public static final String NAME = "MapMatch";
    public static final String VERSION = "0.1";


    @Instance
    public static MapMatch instance;


    @EventHandler
    public static void init(FMLInitializationEvent event) {
    }
    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public static void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new ExportMap());
        event.registerServerCommand(new BuildCube());
        event.registerServerCommand(new BuildHouse());
        event.registerServerCommand(new BuildHouse_Coord());
    }
}
