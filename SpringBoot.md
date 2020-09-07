# SpringBoot

## 快速开始



## 深入学习

1. 端口号

   application.yml

   ```yaml
   server: 
   	port: 8888
   ```

2. banner

   ![image-20200906223225616](E:\GitHub\StudyNotes\SpringBoot.assets\image-20200906223225616.png)

   将字符画放入此文件中即可！

3. 原理初探

   自动配置：

   pom.xml

   - spring-boot-dependencies：核心依赖在父工程中。
   - 版本仓库中提供默认版本，因此在引入依赖时，有些不用指定版本。

   启动器

   - ```xml
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter</artifactId>
     </dependency>
     ```

   - 

## 原理初探

1. @SpringBootConfiguration：SpringBoot配置类
1. @Configuration：Spring配置类
      1. @Component：Spring组件
2. @EnableAutoConfiguration：自动配置
   1. @AutoConfigurationPackage：自动配置包
      1. @Import({Registrar.class})：自动配置包注册
   2. @Import({AutoConfigurationImportSelector.class})：自动配置导入选择

```java
// 获取所有配置
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
    List<String> configurations = SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.getBeanClassLoader());
    Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you are using a custom packaging, make sure that file is correct.");
    return configurations;
}
```

3. META-INF/spring.factories：自动配置的核心文件

   org.springframework.boot.autoconfigure

4. 

1. @RestController

   

