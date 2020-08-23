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

- 

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
  File ->Project Structure->Project Settings ->Project或使用快捷键Ctrl+Alt+shift+S打开项目的jdk配置：查看此两处是否与目标jdk一致。

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

