# 图片压缩DEMO



> 项目为一个测试用的小Demo，一方面是压缩拍摄的艺术照，另一方面为大项目做模块测试使用



* 项目使用的依赖：
  * Thumbnailator<0.4.8>  Google开源Java类库
  * junit<4.12> 单元测试
  * lombok<1.18.16>  好用的代码生成工具
  * log4j<1.2.12> 日志



> 测试包中包含主函数功能中各个部分的拆分分解
>
> java包中的Main方法依赖控制台输入
>
> > 输入格式为：C:\Users\13597\Desktop\test\old **注意最后没有一个反斜杠哦**
> >
> > 目标目录不需要创建源文件夹中的文件夹结构哦，会自动进行创建
> >
> > 当前仅支持jpg、png格式，如果想增加格式，可以在下面的代码中进行修改
> >
> > ```java
> > //源码中53行
> > if (file1.getName().endsWith(".jpg") || file1.getName().endsWith(".png"))
> > ```



下面为Thumbnailator的使用方法，感谢[Java我人生](https://blog.csdn.net/chenleixing/article/details/44685817)的博客，下面是对其功能的使用方式搬运，如果想了解详细内容请支持原作者！



> 基础准备代码

```java
	//前提准备，后续中使用
	File fromPic=new File("测试源图片路径");
	File toPic=new File("生成的目标路径");
	File waterPic=new File("水印图片源路径");
```

> 按指定大小把图片进行缩放（会遵循原图高宽比例）

```java
	//按指定大小把图片进行缩和放（会遵循原图高宽比例） 
	//此处把图片压成400×500的缩略图
	Thumbnails.of(fromPic).size(400,500).toFile(toPic);//变为400*300,遵循原图比例缩或放到400*某个高度
```

> 按照指定比例进行缩小和放大

```java
	//按照比例进行缩小和放大
	Thumbnails.of(fromPic).scale(0.2f).toFile(toPic);//按比例缩小
	Thumbnails.of(fromPic).scale(2f);//按比例放大
```

> 按指定的大小进行缩放（不遵循原图比例）

```java
	//不按比例，就按指定的大小进行缩放
	Thumbnails.of(fromPic).size(100, 100).keepAspectRatio(false).toFile(toPic);
	//或者Thumbnails.of(fromPic).forceSize(100,100).toFile(toPic);
```

> 旋转图片

```java
	//旋转图片，rotate(角度),正数则为顺时针，负数则为逆时针
	Thumbnails.of(fromPic).size(200,200).rotate(90).toFile(toPic);
```

> 图片尺寸不变，压缩图片文件大小

```java
	//图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
	Thumbnails.of(fromPic).scale(1f).outputQuality(0.25f).toFile(toPic);
```

> 给图片加水印

```java
	//给图片加水印，watermark(位置，水印图，透明度)Positions.CENTER表示加在中间
	Thumbnails.of(fromPic).size(400,400)
		.watermark(Positions.CENTER,ImageIO.read(waterPic),0.5f)
		.outputQuality(0.8f).toFile(toPic);
```

> 图片裁剪

```java
	//用sourceRegion()实现图片裁剪
	//图片中心300*300的区域,Positions.CENTER表示中心，还有许多其他位置可选
	Thumbnails.of(fromPic).sourceRegion(Positions.CENTER,300,300)
		.size(300,300).toFile(toPic);

	//图片中上区域300*300的区域
	Thumbnails.of(fromPic).sourceRegion(Positions.TOP_CENTER,300,300)
		.size(300,300).toFile(toPic);
```

> 转换图片格式

```java
	//用outputFormat(图像格式)转换图片格式，保持原尺寸不变
	Thumbnails.of(fromPic).scale(1f).outputFormat("png")
		.toFile("picture/png格式的图片.png");
```

> 输出成文件流OutputStream

```java
	//输出成文件流OutputStream
	OutputStream os=new FileOutputStream(toPic);
	Thumbnails.of(fromPic).size(120,120).toOutputStream(os);
```

> 输出成BufferedImage

```java
	//输出BufferedImage,asBufferedImage()返回BufferedImage
	BufferedImage bi=Thumbnails.of(fromPic).size(120,120).asBufferedImage();
	ImageIO.write(bi,"jpg",toPic);
```

> 压缩至指定图片尺寸，保持图片不变形，多余部分裁剪掉

```java
	//压缩至指定图片尺寸（例如：横400高300），保持图片不变形，多余部分裁剪掉(这个是引的网友的代码)
	BufferedImage image = ImageIO.read(fromPic);
	Builder<BufferedImage> builder = null;
 
	int imageWidth = image.getWidth();
	int imageHeitht = image.getHeight();
	if ((float)300 / 400 != (float)imageWidth / imageHeitht) {
		if (imageWidth > imageHeitht) {
			image = Thumbnails.of(fromPic).height(300).asBufferedImage();
		} else {
			image = Thumbnails.of(fromPic).width(400).asBufferedImage();
		}
		builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, 400, 300).size(400, 300);
	} else {
		builder = Thumbnails.of(image).size(400, 300);
	}
	builder.outputFormat("jpg").toFile(toPic);
```

