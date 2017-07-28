# brickmaker
brickfy the image, for make image of lego

# steps to make bricks
1. make the thumbnail of the source image(with scale)
2. create target image with 30xheight and 30xwidth of the thumbnail(30 pixels is the size of default brick)
3. paint brick to (30x,30y,30,30) of the target image
4. paint rectangle to (30x,30y,30,30) with color of the thumbnail(using SrcOver mode, and the color needs alpha channel=128)
5. save image


# build
mvn package

libs and jar are under target folder

# run
java -jar brick.maker-1.0.1.jar sample.png result.jpg 0.5

# todo
1. add profile for lego

# 中文介绍
积木化图片,主要是为了制作乐高像素图,最简单实现方法

# 实现步骤
1. 等比例缩放原图
2. 只是30x的缩略图(默认颗粒为30像素)
3. 在(30x,30y)的位置绘制颗粒图
4. 在(30x,30y)的位置绘制有色正方形(使用SrcOver模式, 颜色为对应缩略图的(x,y)的颜色,同时alpha通道设置为128)
5. 保存图片

# 打包
mvn package
目标文件在target目录下,执行文件包含jar和libs

# 运行
java -jar brick.maker-1.0.1.jar sample.png result.jpg 0.5
第一个参数为需要积木化的图片, 第二个参数是保存文件, 第三个参数为缩放比例(0.5表示原图的一半)
