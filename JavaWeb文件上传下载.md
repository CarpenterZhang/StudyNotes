# JavaWeb文件上传/下载

## 文件上传

1. 导入依赖

```xml
<!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.4</version>
</dependency>
<!-- https://mvnrepository.com/artifact/commons-io/commons-io -->
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.7</version>
</dependency>
```

2. 前端
   - 表单method=post
   - 表单enctype=``"multipart/form-data"
   - 输入框input type="file"

```html
<form action="/upload" method="post" enctype="multipart/form-data">
	<input type="file" name="uploadFile"/>
    <input type="submit" value="上传"/>
</form>
```

3. 后端

   用户上传提交的内容会存放到临时的文件中，我们使用getpart来获取Part对象

```java
package com.cptz.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet("/DownLoadServlet")
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到上传文件的保存目录，将上传的文件存放在WEB-INF目录下面 不允许外界直接访问，保证上传文件的安全性
        String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
        File file = new File(savePath);
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath + "目标目录不存在，需要进行创建");
            file.mkdir();
        }
        String message = null;
        try {
            //1 创建DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2 创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //判断提交上来的数据是不是表单上的数据
            upload.setHeaderEncoding("UTF-8");
            if (!ServletFileUpload.isMultipartContent(request)) {
                return;
            }

            //4 使用ServletFileUpload解析器来解析上传数据，解析结果返回的是一个List<FileItem>
            //集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    System.out.println(name + "=" + value);
                } else {
                    String filename = item.getName();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    /**
                     * 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，
                     * 如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                     */
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    //获取item输入流
                    InputStream inputStream = item.getInputStream();
                    //创建一个文件输出流
                    FileOutputStream fileOutputStream = new FileOutputStream(savePath + "\\" + filename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流是否已经读完的标识
                    int len = 0;
                    while ((len = inputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    inputStream.close();
                    fileOutputStream.close();
                    item.delete();
                    message = "文件上传成功";
                }
            }
        } catch (Exception e) {
            message = "文件上传失败";
            e.printStackTrace();
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }
}
```



## 文件下载

```java
package com.cptz.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@WebServlet("/DownLoadServlet")
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = "testIamge.jpg";

        // 当文件名不是英文名的时候，最好使用url解码器去编码一下，
        filename = URLEncoder.encode(filename, "UTF-8");
        // 设置响应类型
        response.setContentType("image/jpeg");
        // Content-Disposition=attachment;filename=xxx 让浏览器以文件的方式强制下载
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);

        InputStream is = getServletContext().getResourceAsStream("/testImage.jpg");
        OutputStream os = response.getOutputStream();

        byte[] buff = new byte[1024 * 10];//可以自己 指定缓冲区的大小
        int len = 0;
        while ((len = is.read(buff)) > -1) {
            os.write(buff, 0, len);
        }

        is.close();
        os.close();
    }
}

```

