# IDEA tomcat Tomcat Catalina Log乱码问题

## 原因

Windows系统的cmd是GBK编码的，所以IDEA的下方log输出的部分的编码也是GBK的，然而Tomcat 9.0 版本默认log输出是UTF-8编码的，采用了两种不同的编码方式就会导致乱码。

## 解决方法

1. 修改IDEA为UTF-8编码

   1.  idea安装目录，修改idea64.exe.vmoptions。（version < 2019）

      末尾追加 -Dfile.encoding=UTF-8。

      这个参数的作用是强制系统文件使用UTF-8编码

   2. 顶部--帮助--编辑自定义VM选项（version > 2019）

      末尾追加-Dfile.encoding=UTF-8

2. 指定Tomcat为UTF-8

   编辑 Tomcat运行配置

   在虚拟机选项中加入 -Dfile.encoding=UTF-8

3. 重启IDEA即可。