package com.compress.picture;

import lombok.extern.java.Log;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * 使用前请先阅读README哦
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/17 16:16
 */
@Log
public class CompressPicture {

    /**
     * 输入格式为：C:\Users\13597\Desktop\test\old **注意最后没有一个反斜杠哦**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        String targetPath;
        String sourcePath;

        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入源文件目录(要进行压缩的图片)");
        sourcePath = scanner.nextLine();
        System.out.println("请输入导出后文件目录(压缩后图图片的存储目录)");
        targetPath = scanner.nextLine();

        getAllFile(sourcePath, targetPath);
    }

    public static void getAllFile(String filePath, String targetPath) throws IOException {
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                File targetFile = new File(targetPath + "\\" + file1.getName());
                if (!targetFile.exists()) {
                    boolean mkdir = targetFile.mkdir();
                    log.info("创建 " + targetPath + "\\" + file1.getName() + " 文件夹");
                }
                getAllFile(file1.getAbsolutePath(), targetPath + "\\" + file1.getName());
            } else {
                if (file1.getName().endsWith(".jpg") || file1.getName().endsWith(".png")) {
                    //要进行处理的文件
                    File fromPic = new File(filePath + "\\" + file1.getName());
                    log.info("读取源文件：" + file1.getName());
                    //处理后进行输出的文件
                    File toPic = new File(targetPath + "\\" + file1.getName());
                    log.info("对文件进行压缩处理");
                    //图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
                    Thumbnails.of(fromPic).scale(1f).outputQuality(0.25f).toFile(toPic);
                    log.info("压缩完成");
                    log.info("处理后文件输出到 " + targetPath + "\\" + file1.getName());
                }
            }

        }
    }
}
