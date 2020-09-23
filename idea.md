# idea使用

## 快捷键

- 上下移动一行或多行代码

  Alt + Shift + Up/Down

- 复制整行

  Ctrl + D

- Navigate to the declaration of the symbol at caret or show its usages

  Ctrl + B

- 新建类

  Ctrl + Alt + Insert

- 自动代码提示

  Ctrl + Shift + Space

- surround with（if/try-catch/for/lock...）

  Ctrl + Alt + T
  
- 快速返回上次查看代码的位置

  Ctrl + Alt + Left/Right
  
- 关闭当前页签

  Ctrl + F4

## 快速输入html标签

> \>：下一个子标签
> *：多少个标签
> $：标签的名称序号
> {}:标签的内容

```html
<!--输入：h1,按tab键-->
<h1></h1>

<!--输入：div#abc,按Tab键-->
<div id="abc"></div>
 
<!--输入：div.abc,按Tab键-->
<div class="abc"></div>
 
<!--输入: div>p*3 ,按Tab键-->
<div>
    <p></p>
    <p></p>
    <p></p>
</div>
 
<!--输入 a[href=#] 按tab键-->
<a href="#"></a>

<!--输入：ul.menu>li*3>a[href=#]{HTML} 按tab键-->
<ul class="menu">
    <li><a href="#">HTML</a></li>
    <li><a href="#">HTML</a></li>
    <li><a href="#">HTML</a></li>
</ul>
 
<!--输入  ul>li*3>a[href=#]{我是第$个} 再按tab键-->
<ul>
    <li><a href="#">我是第1个</a></li>
    <li><a href="#">我是第2个</a></li>
    <li><a href="#">我是第3个</a></li>
</ul>

<!--输入 img[src='images/$.jpg']*3 再按tab键-->
<img src="images/1.jpg" alt="">     
<img src="images/2.jpg" alt="">     
<img src="images/3.jpg" alt="">

<!--输入 li*3>div.img>img[src='images/$.jpg'] 再按tab键-->
<li>
    <div class="img"><img src="images/1.jpg" alt=""></div>
</li>
<li>
    <div class="img"><img src="images/2.jpg" alt=""></div>
</li>
<li>
    <div class="img"><img src="images/3.jpg" alt=""></div>
</li>
```

## 快速输入System.out.println()及console.log()

- xxxVar.sout + Tab
- sout + Tab
- xxxVar.log + Tab

## IDEA设置项目文件自动Add到Svn/Git

1. 配置自动Add

   ![image-20200923182420640](E:\GitHub\StudyNotes\idea.assets\image-20200923182420640.png)

2. 将未添加的文件添加到本地

   ![img](E:\GitHub\StudyNotes\idea.assets\1462681-20190526110752839-289270172.png)

3. 取消已经添加的文件

   ![img](E:\GitHub\StudyNotes\idea.assets\1462681-20190526110804816-1210997743.png)

## 报错

1. Error:java: Compilation failed: internal java compiler error

__错误现象__

使用Idea导入新项目或升级idea或新建项目时会出现以下异常信息：

```l
Error:java: Compilation failed: internal java compiler error 
```

![报错图片](E:\GitHub\StudyNotes\idea.assets\20180827220008319)

__错误原因__
导致这个错误的原因主要是因为jdk版本问题，此处有两个原因，一个是编译版本不匹配，一个是当前项目jdk版本不支持。

- 查看项目的jdk
  File ->Project Structure->Project Settings ->Project(Ctrl+Alt+shift+S) 打开项目的jdk配置：查看此两处是否与目标jdk一致。

  ![这里写图片描述](E:\GitHub\StudyNotes\idea.assets\20180827220024648)

- 查看工程的jdk
  点击上图中Modules查看对应jdk版本：

  ![这里写图片描述](E:\GitHub\StudyNotes\idea.assets\20180827220037258)

- 查看java编译器版本

  ![这里写图片描述](E:\GitHub\StudyNotes\idea.assets\20180827220049216)

导入java项目时此处处问题的概率比较多。

针对此问题，重新打开或修改pom文件（maven项目）中的内容很可能导致jdk版本重新变为1.5。如果是maven项目，可在pom.xml中指定jdk相关信息：

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <java.version>1.8</java.version>
</properties>
<build>
	<plugins>
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-compiler-plugin</artifactId>
			<configuration>
				<source>${java.version}</source>
				<target>${java.version}</target>
			</configuration>
		</plugin>
	</plugins>
</build>
```

或在maven的conf/settings.xml中配置：

```xml
<profile>
	<id>jdk-1.8</id>
    <activation>
    	<activeByDefault>true</activeByDefault>
    	<jdk>1.8</jdk>
    </activation>
    <properties>
    	<maven.compiler.source>1.8</maven.compiler.source>
    	<maven.compiler.target>1.8</maven.compiler.target>
		<maven.compiler.compilerVersion>
			1.8
		</maven.compiler.compilerVersion>
    </properties>
</profile>
```

