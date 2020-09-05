# MySql数据库乱码

## 前提条件

​		首先确定从controller->service->mapper级别时，打印出的均无乱码，否则非本题范围。

## 解决方案

1. mysql配置文件级别

   my.ini

```ini
[mysql]
default-character-set=utf8
[mysqld]
character-set-server=utf8
```

2. database级别

<img src="E:\GitHub\StudyNotes\MySql数据库乱码.assets\image-20200829144651326.png" alt="image-20200829144651326" style="zoom:80%;" />

```sql
CREATE DATABASE bookstore DEFAULT CHARACTER SET utf8 
COLLATE utf8_bin;  #对数据库创建进行编码
```

3. table级别

```sql
CREATE TABLE tb (
	id INT (11) NOT NULL AUTO_INCREMENT,
	name VARCHAR (200)
) DEFAULT CHARSET = utf8;
```

4. 字段级别

```sql
CREATE TABLE tb (
	id INT (11) NOT NULL AUTO_INCREMENT,
	name VARCHAR (200) CHARACTER SET utf8 DEFAULT NULL
);
```

5. url驱动级别

```properties
jdbc.url=jdbc:mysql://localhost:3306/bookstore?useSSL=false&useUnicode=true&charactorEncoding=utf-8&serverTimeZone=UTC
```

