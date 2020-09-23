# SpringBoot

[官网](https://docs.spring.io/spring-boot/docs/current/reference/html/)

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

3. org\springframework\boot\spring-boot-autoconfigure\2.3.3.RELEASE\spring-boot-autoconfigure-2.3.3.RELEASE.jar!\META-INF\spring.factories：自动配置的核心文件

   org.springframework.boot.autoconfigure

   1. org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration


## 配置文件

1. @ConfigurationProperties赋值

__报红__

​		引入依赖即可

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

Dog.java

```java
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dog {
    private String name;
    private Integer age;
}
```

Person.java

```java
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "person")
public class Person {
    private String name;
    private Integer age;
    private Boolean happy;
    private Date birth;
    private Map<String, Object> maps;
    private List<Object> lists;
    private Dog dog;
}
```

application.yml

```yaml
person:
  name: cptz
  age: 3
  happy: false
  birth: 2020/03/30
  maps: {k1: v1, k2: v2}
  lists:
    - code
    - music
    - girl
  dog:
    name: 旺财
    age: 3
```

2. @PropertySource指定文件赋值

__SPELL表达式__

Person.java

```java
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:custom.properties")
public class Person {
    @Value("${person.name}")
    private String name;
}
```

custom.properties

```properties
person.name= cptz
```

3. 其他用法

__随机数__

![image-20200913174804090](E:\GitHub\StudyNotes\SpringBoot.assets\image-20200913174804090.png)

__引用值__

![image-20200913174909905](E:\GitHub\StudyNotes\SpringBoot.assets\image-20200913174909905.png)

4. 松散绑定
5. 配置位置

>`SpringApplication` loads properties from `application.properties` files in the following locations and adds them to the Spring `Environment`:
>
>1. A `/config` subdirectory of the current directory
>2. The current directory
>3. A classpath `/config` package
>4. The classpath root
>
>The list is ordered by precedence（优先级） (properties defined in locations higher in the list override those defined in lower locations).

6. 多环境配置

   1. 多文件

      ![image-20200913182654981](E:\GitHub\StudyNotes\SpringBoot.assets\image-20200913182654981.png)

      ```yml
      spring:
        profiles:
          active: test
      ```

   2. 单文件

      ```yml
      server:
        port: 8000
      spring:
        profiles:
          active: prod
      ---
      server:
        port: 8888
      spring:
        profiles: dev
      ---
      server:
        port: 8888
      spring:
        profiles: prod
      ```

7. 输出哪些配置生效

   1. ```yml
      debug: true
      ```

## JSR303校验

1. 引入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

1. 注解

```java
@Validated // 数据校验
public class Person {
    @Email(message = "Illegal!")
    private String name;
    ...
}
```

![img](E:\GitHub\StudyNotes\SpringBoot.assets\4034970a304e251f166ab881bb1a66117e3e5348.jpeg)

## 自动装配原理再探

spring.factories (org/springframework/boot/spring-boot-autoconfigure/2.3.3.RELEASE/spring-boot-autoconfigure-2.3.3.RELEASE.jar!/META-INF/)

```properties
org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration
```

1. xxxxAutoConfiguration：自动装配类

HttpEncodingAutoConfiguration.java

```java
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ServerProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(CharacterEncodingFilter.class)
@ConditionalOnProperty(prefix = "server.servlet.encoding", value = "enabled", matchIfMissing = true)
public class HttpEncodingAutoConfiguration
```

1. @Configuration：声明这是一个配置类
2. @EnableConfigurationProperties：从ServerProperties类中自动装配默认配置
3. @Conditionalxxx：判断是否配置此类

ServerProperties.java

```java
@ConfigurationProperties(prefix = "server", ignoreUnknownFields = true)
public class ServerProperties
```

1. @ConfigurationProperties：如果配置，则yml文件前缀为server

## SpringBoot Web

- 导入静态资源
- 首页
- jsp，模板引擎Thymeleaf
- 装配扩展SpringMVC
- 增删改查
- 拦截器
- 国际化

1. 静态资源

   org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#addResourceHandlers

   1. application.yml

      ```yml
      spring:
        mvc:
          static-path-pattern: 
      ```

   2. classpath:/META-INF/resources/webjars/

   3. classpath:/META-INF/resources/"

   4. classpath:/resources/

   5. classpath:/static/

   6. classpath:/public/

   任何放在自定义的目录下或其它5个路径下的文件均可被静态访问。

2. 首页

   1. application.yml

      ```yml
      spring:
        mvc:
          static-path-pattern: 
      ```

   2. classpath:/META-INF/resources/"

   3. classpath:/resources/

   4. classpath:/static/

   5. classpath:/public/

   在自定义的目录下或其它四个目录下的index.html文件可作为首页。

   自定义项目根目录上下文

   ```yml
   server:
     servlet:
       context-path: /web
   ```

3. 模板引擎

   [Thymeleaf官网](https://www.thymeleaf.org/documentation.html)

   1. 引入依赖

      ```xml
      <dependency>
          <groupId>org.thymeleaf</groupId>
          <artifactId>thymeleaf</artifactId>
      </dependency>
      ```

   2. 配置

      org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties类中明确指出

      1. spring.thymeleaf配置
      2. classpath:/templates/xxx.html

      文件均可被解析。

4. 装配扩展SpringMVC

   [官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-spring-mvc-auto-configuration)

   > If you want to keep those Spring Boot MVC customizations and make more [MVC customizations](https://docs.spring.io/spring/docs/5.2.9.RELEASE/spring-framework-reference/web.html#mvc) (interceptors, formatters, view controllers, and other features), you can add your own `@Configuration` class of type `WebMvcConfigurer` but **without** `@EnableWebMvc`.
   >
   > If you want to provide custom instances of `RequestMappingHandlerMapping`, `RequestMappingHandlerAdapter`, or `ExceptionHandlerExceptionResolver`, and still keep the Spring Boot MVC customizations, you can declare a bean of type `WebMvcRegistrations` and use it to provide custom instances of those components.
   >
   > If you want to take complete control of Spring MVC, you can add your own `@Configuration` annotated with `@EnableWebMvc`, or alternatively add your own `@Configuration`-annotated `DelegatingWebMvcConfiguration` as described in the Javadoc of `@EnableWebMvc`.

   如果想扩展mvc，需自定义类，并在类上声明`@Configuration`，且实现`WebMvcConfigurer` 接口。

   1. 自定义视图解析器

      在org.springframework.web.servlet.view.ContentNegotiatingViewResolver#initServletContext方法中：

      ```java
      @Override
      protected void initServletContext(ServletContext servletContext) {
         Collection<ViewResolver> matchingBeans =
               BeanFactoryUtils.beansOfTypeIncludingAncestors(obtainApplicationContext(), ViewResolver.class).values();
      ...
      }
      ```

      因此只需实现`ViewResolver`接口，且注册为组件，即可成为视图解析器。

      

