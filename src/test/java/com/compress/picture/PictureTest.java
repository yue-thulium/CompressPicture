package com.compress.picture;

import net.coobird.thumbnailator.Thumbnails;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/17 15:57
 */
public class PictureTest {

    /**
     * 测试压缩测试
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        //要进行处理的文件
        File fromPic=new File("C:\\Users\\13597\\Desktop\\test\\old\\_MG_0002.jpg");
        //处理后进行输出的文件
        File toPic=new File("C:\\Users\\13597\\Desktop\\test\\new\\_MG_0002.jpg");

        //图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
        Thumbnails.of(fromPic).scale(1f).outputQuality(0.25f).toFile(toPic);
    }


    /**
     * 读取测试
     */
    @Test
    public void test2() {
        getAllFileName("C:\\Users\\13597\\Desktop\\test\\old");
    }

    public void getAllFileName(String filePath) {
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                getAllFileName(file1.getAbsolutePath());
            }else {
                if (file1.getName().endsWith(".jpg") || file1.getName().endsWith(".png")) {
                    String name = file1.getName();
                    System.out.println(name);
                }
            }

        }
    }
}
