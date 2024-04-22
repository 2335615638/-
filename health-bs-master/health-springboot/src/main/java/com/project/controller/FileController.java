package com.project.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import com.project.config.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author: zhoujinchuan
 * @Description: (文件上传和下载)
 * @Date: 2022/5/2 12:47
 */
@RestController
@Slf4j
@RequestMapping("/common")
@Api(value = "file", tags = "文件上传")
public class FileController {

    @Value("${project.file.img-upload-path}")
    private String ImagePath;

    @Value("${project.file.file-upload-path}")
    private String FilePath;

    @Value("${project.file.baseUrl}")
    private String baseUrl;

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadImage")
    @ApiOperation("上传图片")
    public Result<String> upload(MultipartFile file,
                                 @ApiParam(value = "文件类型 比如头像 user-avatar") @RequestParam("type") String type) {
        //file是个临时文件，需要转存到指定位置，否者本次请求后临时文件会删除
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //截取文件后缀名
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf('.'));
        // 判断是否是图片类型的文件
        Assert.isTrue(isImageFile(suffixName), "不是图片类型");
        //使用UUID重新生成文件名，防止重复
        String fileName = UUID.randomUUID() + suffixName;

        //判断当前目录是否存在
        File dir = new File(ImagePath + type + File.separator);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //文件转存
        try {
            file.transferTo(new File(ImagePath + type + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(baseUrl+"/common/download/image?type=" + type + "&name=" + fileName);
    }

    /**
     * 判断是否是图片文件的方法
     *
     * @param suffix
     * @return
     */
    private static boolean isImageFile(String suffix) {
        /**
         * 假设常见的图片文件后缀为 ".jpg", ".jpeg", ".png", ".gif"
         */
        return ".jpg".equalsIgnoreCase(suffix) ||
                ".jpeg".equalsIgnoreCase(suffix) ||
                ".png".equalsIgnoreCase(suffix) ||
                ".gif".equalsIgnoreCase(suffix);
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadFile")
    @ApiOperation("上传文件")
    public Result<String> uploadFile(MultipartFile file,
                                     @ApiParam(value = "文件类型 比如头像 user-avatar")@RequestParam("type") String type) {
        //file是个临时文件，需要转存到指定位置，否者本次请求后临时文件会删除
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //截取文件后缀名
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf('.'));
        // 判断是否是图片类型的文件
        Assert.isTrue(isImageFile(suffixName), "不是图片类型");
        //使用UUID重新生成文件名，防止重复
        String fileName = UUID.randomUUID() + suffixName;

        //判断当前目录是否存在
        File dir = new File(FilePath + type + File.separator);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //文件转存
        try {
            file.transferTo(new File(FilePath + type + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(baseUrl+"/common/download/file?type=" + type + "&name=" + fileName);
    }

    /**
     * 图片下载
     *
     * @param name
     * @param response
     */
    @GetMapping("download/image")
    @ApiOperation("图片下载")
    public void imageDownload(@RequestParam("type") String type,
                              @RequestParam("name") String name,
                              HttpServletResponse response) {
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            //通过输入流读取文件
            System.out.println(ImagePath + type + File.separator + name);
            File file = new File(ImagePath + type + File.separator + name);
            Assert.isTrue(file.exists(), "文件不存在");
            fileInputStream = new FileInputStream(file);
            //输出流，通过输出流将文件写回浏览器，在浏览器显示图片
            outputStream = response.getOutputStream();
            //设置返回图片文件
            response.setContentType("image/jpeg");
            IOUtils.copy(fileInputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @GetMapping("download/file")
    @ApiOperation("文件下载")
    public void fileDownload(@RequestParam("type") String type,
                             @RequestParam("name") String name,
                             HttpServletResponse response) {
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            // 通过输入流读取文件
            File file = new File(FilePath + type + File.separator + name);
            Assert.isTrue(file.exists(), "文件不存在");
            fileInputStream = new FileInputStream(file);

            // 获取文件后缀
            String fileName = file.getName();
            int lastDotIndex = fileName.lastIndexOf(".");
            String fileExtension = lastDotIndex == -1 ? "" : fileName.substring(lastDotIndex + 1);


            // 设置响应的文件类型
            String contentType = getContentType(fileExtension);
            response.setContentType(contentType);

            // 输出流，通过输出流将文件写回浏览器
            outputStream = response.getOutputStream();

            // 将文件内容写入输出流
            IOUtils.copy(fileInputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getContentType(String fileExtension) {
        // 根据文件后缀返回对应的ContentType
        switch (fileExtension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "bmp":
                return "image/bmp";
            case "webp":
                return "image/webp";
            case "pdf":
                return "application/pdf";
            case "txt":
                return "text/plain";
            case "html":
                return "text/html";
            case "xml":
                return "application/xml";
            case "json":
                return "application/json";
            case "csv":
                return "text/csv";
            case "mp3":
                return "audio/mpeg";
            case "mp4":
                return "video/mp4";
            case "zip":
                return "application/zip";
            case "tar":
                return "application/x-tar";
            case "gz":
                return "application/gzip";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            default:
                // 默认为二进制流数据
                return "application/octet-stream";
        }
    }

}
