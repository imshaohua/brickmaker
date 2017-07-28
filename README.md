# brickmaker
brickfy the image, for make image of lego

# steps to make bricks
1. make the thumbnail of the source image(with scale)
2. create target image with 30*height and 30*width of the thumbnail(30 pixels is the size of default brick)
3. paint brick to (x,y,30,30) of the target image
4. paint rectangle to (x,y,30,30) with color of the thumbnail(using SrcOver mode, and the color needs alpha channel=128)
5. save image


# build
mvn package -DskipTests

libs and jar are under target folder

# run
java -jar brick.maker-1.0.1.jar sample.png result.jpg 0.5