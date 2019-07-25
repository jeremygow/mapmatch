library(rLiDAR)
library(sp)
library(rgdal)
setwd("/media/vanessa/Data/svn/maps/long_island")
files = list.files(pattern = ".las$", recursive = FALSE)

allData <- data.frame(X=double(), Y=double(), Z=double(), intensity=integer(), returnNumber=integer(),
                      numberOfReturns=integer(), scanDirectonFlag=integer(), edgeOfFlightLine=integer(),
                      classification=integer(), scanAngleRank=integer(), userData=integer(),
                      pointSourceID=integer(), gpstime=integer())
for(file in files[1]){
  rLAS <- readLAS(file, short=FALSE)
  allData = rbind(allData, rLAS)
}

#water = rep(0, nrow(allData))
#water[allData$Classification==9]=1

type = as.factor(allData$Classification)

pdf("plot.pdf")
plot(allData$X, allData$Y, pch=".", col=type)
legend("topright", legend=levels(type), pch=19, col=unique(type))
dev.off()


sputm <- SpatialPoints(allData[,1:2], proj4string=CRS("+proj=utm +zone=18 +datum=NAD83"))  
spgeo <- spTransform(sputm, CRS("+proj=longlat"))

gadm <- readRDS("../gadm36_USA_0_sp.rds")
bbox = summary(spgeo)$bbox
plot(gadm, xlim=bbox[1,], ylim=bbox[2,],
     col = 'lightgrey', border = 'darkgrey')

points(spgeo[allData$Classification==2,], pch=".", col="red")
points(spgeo[allData$Classification==9,], pch=".", col="blue")
#points(spgeo[allData$Classification==1,], pch=".", col="grey")
points(spgeo[allData$Classification==17,], pch=".", col="yellow")
points(spgeo[allData$Classification==18,], pch=".", col="lightgreen")
points(spgeo[allData$Classification==10,], pch=".", col="black")


legend("topright", legend=levels(type), pch=19, col=unique(type))

data = as.data.frame(spgeo)
data$class = allData$Classification

eps = 0.00001*10
#0.00001 = 1m
grid = matrix(0, nrow=abs(bbox[1,1]-bbox[1,2])/0.00001, ncol=abs(bbox[2,1]-bbox[2,2])/0.00001)
for(x in 1:(nrow(grid))){
  xpos = bbox[1,1]+x*eps
  print(x)
  for(y in 1:(ncol(grid))){
    ypos = bbox[2,1]+y*eps
    tmp = data[data$X<=xpos + eps & data$X>=xpos - eps & data$Y <=ypos + eps & data$Y >=ypos -eps,]
    if(sum(tmp$class==9)>sum(tmp$class==2)){
      grid[x,y] = 1
    }else if(sum(tmp$class==9)<sum(tmp$class==2)){
      grid[x,y] = -1
    }
  }
}

water = rep(0, nrow(allData))

