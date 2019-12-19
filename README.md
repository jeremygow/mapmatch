# mapmatch

## Installation

* Install gradle
* Download mcforge mod for Minecraft 12.2 https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.12.2.html
* Clone this repo
* Copy the following files from mcforge to repo folder: build.gradle, gradlew.bat, gradlew, the gradle folder

```
gradlew runServer
```
```
gradlew runClient
```
This starts up Minecraft. You should be able to see "mapmatch" as a mod. Activate it.

## Some Links
* Setting up as a Forge mod: https://mcforge.readthedocs.io/en/latest/gettingstarted/

 
Overview of LiDAR datasets: https://en.wikipedia.org/wiki/National_Lidar_Dataset_(United_States)
New York: http://gis.ny.gov/elevation/lidar-coverage.htm
Actual files for Long Island: ftp://ftp.gis.ny.gov/elevation/LIDAR/USGS_LongIsland2014/

## Known issues
### No gradlew
Try `./gradlew` instead
