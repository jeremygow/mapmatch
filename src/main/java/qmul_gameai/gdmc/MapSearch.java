package qmul_gameai.gdmc;

import com.opencsv.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapSearch {

    // Minecraft - raw data
    protected int[] mc_x;
    protected int[] mc_z;
    protected int[] mc_h;
    protected boolean[] mc_water;

    // Minecraft - min/max values
    protected int mc_minX;
    protected int mc_minZ;
    protected int mc_maxX;
    protected int mc_maxZ;
    protected int mc_maxH;
    protected int mc_minH;

    // Minecraft - processed 2d data
    protected boolean[][] mc_land;
    protected int[][] mc_height;
    protected int[][] mc_top;
    protected int[][] mc_terrain;

    public final static String MC_DATA = "/Users/jeremy/Dropbox/Code/gdmc/mapmatch/run/Test2_0_0_256_256.csv";
    //public final static String MC_DATA = "/Users/jeremy/Dropbox/Code/gdmc/mapmatch/run/Test2_0_0_10_10.csv";
    public final static String GEO_DATA = "/Users/jeremy/Dropbox/Code/gdmc/mapmatch/run/geo.csv";

    public static void main(String[] args) {

        MapSearch m = new MapSearch();
        m.search();
    }


    public void search() {

        readMinecraftCSV();
        displayMinecraftMaps();

        //readGeodata();

    }

    public void readMinecraftCSV() {

        List<String[]> mcCSV = new ArrayList<>();
        try {
            FileReader file = new FileReader(new File(MC_DATA));
            CSVReader reader = new CSVReaderBuilder(file).withSkipLines(1).build();
            mcCSV = reader.readAll();
            System.out.println("Minecraft data " + mcCSV.size() + " lines");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        processMinecraftCSV(mcCSV);
        buildMinecraftMaps(mcCSV);
    }

    protected void processMinecraftCSV(List<String[]> mcCSV) {

        if (mcCSV.size() > 0) {

            // x, z, height, water, top, terrain
            String[] first = mcCSV.get(0);
            mc_minX = Integer.valueOf(first[0]);
            mc_maxX = mc_minX;
            mc_minZ = Integer.valueOf(first[1]);
            mc_maxZ = mc_minZ;

            mc_x = new int[mcCSV.size()];
            mc_z = new int[mcCSV.size()];
            mc_h = new int[mcCSV.size()];
            mc_water = new boolean[mcCSV.size()];

            String[] line;
            for (int i = 0; i < mcCSV.size(); i++) {
                line = mcCSV.get(i);
                mc_x[i] = Integer.valueOf(line[ExportMap.CSV_X]);
                mc_z[i] = Integer.valueOf(line[ExportMap.CSV_Z]);
                mc_h[i] = Integer.valueOf(line[ExportMap.CSV_HEIGHT]);
                mc_water[i] = Boolean.valueOf(line[ExportMap.CSV_WATER]);

                if (mc_x[i] < mc_minX) mc_minX = mc_x[i];
                if (mc_x[i] > mc_maxX) mc_maxX = mc_x[i];
                if (mc_z[i] < mc_minZ) mc_minZ = mc_z[i];
                if (mc_z[i] > mc_maxZ) mc_maxZ = mc_z[i];
            }
        }
    }

    protected void buildMinecraftMaps(List<String[]> mcdata) {

        int mc_xlen = 1 + mc_maxX - mc_minX;
        int mc_zlen = 1 + mc_maxZ - mc_minZ;

        System.out.println("Map " + mc_xlen + "x" + mc_zlen);
        System.out.println("x range " + mc_minX + "-" + mc_maxX);
        System.out.println("z range " + mc_minZ + "-" + mc_maxZ);

        System.out.println("x values " + mc_x.length);
        System.out.println("z values " + mc_z.length);

        if (mc_xlen > 0 && mc_zlen > 0) {
            mc_land = new boolean[mc_xlen][mc_zlen];
            mc_height = new int[mc_xlen][mc_zlen];
            mc_top = new int[mc_xlen][mc_zlen];
            mc_terrain = new int[mc_xlen][mc_zlen];

            int x, z;
            String[] line;
            for (int i = 0; i < mc_x.length; i++) {

                try {
                    x = mc_x[i] - mc_minX;
                    z = mc_z[i] - mc_minZ;
                    mc_land[x][z] = !mc_water[i];
                    mc_height[x][z] = mc_h[i];

                    line = mcdata.get(i);
                    mc_top[x][z] = encodeTopBlock(line[ExportMap.CSV_TOP]);
                    mc_terrain[x][z] = encodeTerrainBlock(line[ExportMap.CSV_TERRAIN]);

                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    protected int encodeTopBlock(String block) {
        return 0;
    }

    protected int encodeTerrainBlock(String block) {
        return 0;
    }

    protected void displayMinecraftMaps() {

        System.out.println("\nLand/water:");
        for (int i = 0; i < mc_land.length; i++) {
            for (int j = 0; j < mc_land[0].length; j++) {
                if (mc_land[i][j]) {
                    System.out.print("O");
                } else {
                    System.out.print(".");
                }
            }
            System.out.print("\n");
        }
    }


    public void readGeodata() {

        try {
            CSVReader reader2 = new CSVReader(new FileReader(new File(GEO_DATA)));
            List<String[]> geodata = new ArrayList<>();
            geodata = reader2.readAll();

            System.out.println("Geo data " + geodata.size() + " lines");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }


}
