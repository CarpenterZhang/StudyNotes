# Spring

[Spring官网](https://docs.spring.io/spring/docs/current/spring-framework-reference/)

## 快速开始

__1.引入依赖__

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.7.RELEASE</version>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13</version>
</dependency>
```

__2.创建类__

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hello {
    private String str;

    public void show() {
        System.out.println("Hello " + str);
    }
}
```

__3.创建核心配置文件并注册bean__

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="Hello" class="com.cptz.pojo.Hello">
        <property name="str" value="spring"/>
    </bean>
</beans>
```

__4.测试__

```java
public class HelloTest {
    @Test
    public void testShow() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Hello hello = ctx.getBean("Hello", Hello.class);
        hello.show();
    }
}
```

## 配置说明

__声明bean__

默认单例创建

1. 下标赋值

```xml
<bean id="user" class="com.cptz.pojo.User">
    <constructor-arg index="0" value="1"/>
    <constructor-arg index="1" value="carpenter"/>
    <constructor-arg index="2" value="3"/>
</bean>
```

2. 类型

```xml
<!-- 不建议使用 -->
<bean id="user" class="com.cptz.pojo.User">
    <constructor-arg type="java.lang.String" value="1"/>
    <constructor-arg type="java.lang.String" value="1"/>
    <constructor-arg type="java.lang.String" value="1"/>
</bean>
```

3. 参数名

```xml
<bean id="user" class="com.cptz.pojo.User">
    <constructor-arg name="name" value="carpenter"/>
    <constructor-arg name="id" value="1"/>
    <constructor-arg name="age" value="3"/>
</bean>
```

__别名__

```xml
<alias name="user" alias="userM"/>
```

```xml
<bean id="user" class="com.cptz.pojo.User" name="userAlias">
    ...
</bean>
```

__作用域__

![image-20200815215917148](E:\GitHub\StudyNotes\spring.assets\image-20200815215917148.png)

- singleton 默认 单例模式
- prototype 原型模式 每次new
- session request等仅在web项目中生效

__导入__

```xml
<import resource="classpath*:mapper.xml"/>
```

## 依赖注入

__构造器注入__

__setter注入__

bean

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private Address address;
    private String[] books;
    private List<String> hobbies;
    private Map<String, String> card;
    private Set<String> games;
    private String wife;
    private Properties info;
}
```

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String address;
}
```

核心配置文件

```xml
<bean id="student" class="com.cptz.pojo.Student">
    <!-- 普通注入 -->
    <property name="name" value="Carpenter"/>
    <!-- 引用注入 ref -->
    <property name="address" ref="address"/>
    <!-- 数组注入 -->
    <property name="books">
        <array>
            <value>红楼梦</value>
            <value>水浒传</value>
            <value>三国演义</value>
        </array>
    </property>
    <property name="hobbies">
        <list>
            <value>唱</value>
            <value>跳</value>
            <value>rap</value>
            <value>篮球</value>
        </list>
    </property>
    <property name="card">
        <map>
            <entry key="身份证" value="123123123"/>
            <entry key="银行卡" value="345345345"/>
        </map>
    </property>
    <property name="games">
        <set>
            <value>LOL</value>
            <value>CF</value>
            <value>COC</value>
        </set>
    </property>
    <!-- 空值注入 -->
    <property name="wife">
        <null/>
    </property>
    <!-- properties 注入-->
    <property name="info">
        <props>
            <prop key="driver">com.mysql.jdbc</prop>
            <prop key="url">127.0.0.1:3306/mysql</prop>
            <prop key="name">root</prop>
            <prop key="pwd">123456</prop>
        </props>
    </property>
</bean>

<bean id="address" class="com.cptz.pojo.Address">
    <property name="address" value="Tianjin"/>
</bean>
```

__p命名空间注入(property)__

必须set方法

- ```xml
  xmlns:p="http://www.springframework.org/schema/p"
  ```

- ```xml
  <bean id="user" class="com.cptz.pojo.User" p:name="carpenter" p:age="3"/>
  ```

__c命名空间注入(constructor)__

必须constructor方法

- ```xml
  xmlns:c="http://www.springframework.org/schema/c"
  ```

- ```xml
  <bean id="user2" class="com.cptz.pojo.User" c:name="CarpenterZ" c:age="15"/>
  ```

## 自动装配

1. xml配置

- autowire属性 byName，byType

```xml
<bean id="dog" class="com.cptz.pojo.Dog"/>
<bean id="cat" class="com.cptz.pojo.Cat"/>

<bean id="people" class="com.cptz.pojo.People" autowire="byType">
    <property name="name" value="Carpenter"/>
</bean>
```

2. 注解

- 导入context约束

  ```xml
  xmlns:context="http://www.springframework.org/schema/context"
  http://www.springframework.org/schema/context
              https://www.springframework.org/schema/context/spring-context.xsd
  ```

- 开启注解支持

  ```xml
  <context:annotation-config/>
  ```

- @Autowired

  __默认byType，之后byName__

  可以在属性上使用，也可以在setter方法上使用，甚至可以省略setter方法。

  允许为空：

  ```java
  @Autowired(required = false)
  // 或
  @Nullable
  ```

- @Qualifier("dog")

  当无法区分自动注入哪个类时，此注解指定。

- @Resource(name = "dog")

  相当于@Autowired + @Qualifier

## 注解

导入context约束

```xml
xmlns:context="http://www.springframework.org/schema/context"
http://www.springframework.org/schema/context
            https://www.springframework.org/schema/context/spring-context.xsd
```

开启注解支持

```xml
<context:annotation-config/>
```

指定要扫描的包，仅包下注解才会生效

```xml
<context:component-scan base-package="com.cptz.pojo"/>
```

__注解__

- @Component

  组件，注解在类上，通知spring接管此类。

- @Value("Carpenter")

  注解在属性上，注入具体属性值。

- @Controller

- @Service

- @Repository

- @Scope("Singleton")

  对应作用域注解

- @Configuration，@Bean

  使用Java的方式配置spring

  JavaConfig在spring5时成为推荐

  说明这是一个配置类，@Bean就相当于xml中的bean标签注册的bean，如果只使用注解初始化，则使用AnnotationConfigApplicationContext初始化容器。

  User.java

  ```java
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Component
  public class User {
      @Value("Carpenter")
      private String name;
  }
  ```

  MyConfig.java

  ```java
  @Configuration
  public class MyConfig {
      @Bean
      public User getUser() {
          return new User();
      }
  }
  ```

  测试

  ```java
  @Test
  public void testJavaConfig() {
      ApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfig.class);
      User user = ctx.getBean("getUser", User.class);
      System.out.println(user);
  }
  ```

## AOP

__快速开始__

1. 导入依赖

```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.6</version>
</dependency>
```

2. 创建服务类

```java
public interface UserService {
    public void add();
    public void delete();
    public void update();
    public void select();
}

public class UserServiceImpl implements UserService {
    public void add() {
        System.out.println(">>>>  UserServiceImpl.add ");
    }

    public void delete() {
        System.out.println(">>>>  UserServiceImpl.delete ");
    }

    public void update() {
        System.out.println(">>>>  UserServiceImpl.update ");
    }

    public void select() {
        System.out.println(">>>>  UserServiceImpl.select ");
    }
}
```

3. 添加约束，注册bean

```xml
xmlns:aop="http://www.springframework.org/schema/aop"
http://www.springframework.org/schema/aop
https://www.springframework.org/schema/aop/spring-aop.xsd
<bean id="userService" class="com.cptz.service.UserServiceImpl"/>
```

4. 定义切面

   4.1 方式一：以继承的方式

   - MethodBeforeAdvice
   - AfterReturningAdvice

   __定义前后事件类__

   ```java
   public class Log implements MethodBeforeAdvice {
   
       public void before(Method method, Object[] args, Object target) throws Throwable {
           System.out.println(">>>>>before: " + method.getName());
       }
   }
   
   public class AfterLog implements AfterReturningAdvice {
       public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
           System.out.println(">>>>>>>>> afterReturning: " + method.getName() + " return value: " + returnValue);
       }
   }
   ```

   __注册__

   ```xml
   <bean id="log" class="com.cptz.log.Log"/>
   <bean id="afterLog" class="com.cptz.log.AfterLog"/>
   ```

   __定义切面__

   ```xml
   <aop:config>
       <!-- point cut -->
       <aop:pointcut id="pointcut" expression="execution(* com.cptz.service.UserServiceImpl.*(..))"/>
       <aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
       <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>
   </aop:config>
   ```

   4.2 自定义切面类

   __创建切点前后事件的bean__

   ```java
   public class DiyPointCut {
       public void before() {
           System.out.println(">>>>>>>> DiyPointCut.before ");
       }
   
       public void after() {
           System.out.println(">>>>>>>> DiyPointCut.after ");
       }
   }
   ```

   __注册__

   ```xml
   <bean id="diy" class="com.cptz.diy.DiyPointCut"/>
   ```

   __定义切面__

   ```xml
   <aop:config>
       <aop:aspect ref="diy">
           <aop:pointcut id="point" expression="execution(* com.cptz.service.UserServiceImpl.*(..))"/>
           <aop:before method="before" pointcut-ref="point"/>
           <aop:after method="after" pointcut-ref="point"/>
       </aop:aspect>
   </aop:config>
   ```

   4.3 注解实现切面

   __添加约束，打开Aop注解支持开关__

   ```xml
   <aop:aspectj-autoproxy/>
   属性 proxy-target-class=“true" 使用jdk动态代理
   属性 proxy-target-class=“false" 使用cglib动态代理
   ```

   __创建注解类__

   ```java
   @Aspect // 标注这个类是一个切面
   public class AnnotationPointCut {
       @Before("execution(* com.cptz.service.UserServiceImpl.*(..))")
       public void before() {
           System.out.println("AnnotationPointCut.before");
       }
   
       @After("execution(* com.cptz.service.UserServiceImpl.*(..))")
       public void after() {
           System.out.println("AnnotationPointCut.after");
       }
   }
   ```

__execution表达式__

execution(* com.cptz.service.UserServiceImpl.*(..))

## 整合mybatis

[mybatis-spring官网](http://mybatis.org/spring/zh/index.html)

__快速开始1__

1. 进入依赖

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.7.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.2.8.RELEASE</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.47</version>
</dependency>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.5</version>
</dependency>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.5</version>
</dependency>
```

2. 编写核心配置文件

   __注入dataSource数据源__

```xml
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimeZone=UTC"/>
    <property name="username" value="root"/>
    <property name="password" value="123456"/>
</bean>
```

​			__SqlSessionFactory及SqlSession__

```xml
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource" />
    <property name="configLocation" value="mybatis.xml"/>
    <property name="mapperLocations" value="classpath*:mapper/**/*.xml" />
</bean>

<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
    <constructor-arg index="0" ref="sqlSessionFactory" />
</bean>
```

​			__注册业务实体类__

```xml
<bean id="userMapper" class="com.cptz.mapper.impl.UserMapperImpl">
    <property name="sqlSession" ref="sqlSession"/>
</bean>
```

3. 引用的mybatis.xml文件

```xml
<configuration>
    <properties resource="db.properties"/>
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
</configuration>
```

4. UserMapper.xml

```xml
<mapper namespace="com.cptz.mapper.UserMapper">
    <select id="selectUser" resultType="com.cptz.pojo.User">
        select * from user
    </select>
</mapper>
```

4. 编写业务实体类

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private String pwd;
}
```

5. 编写Dao层接口类及实现类

```java
public interface UserMapper {
    public List<User> selectUser();
}
```

```java
public class UserMapperImpl implements UserMapper {

    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<User> selectUser() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.selectUser();
    }
}
```

6. 测试

```java
@Test
public void userTest() {
    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    UserMapper userMapper = ctx.getBean("userMapper", UserMapper.class);
    userMapper.selectUser().forEach(System.out::println);
}
```

__快速开始2__

1. 引入依赖

2. 编写核心配置文件

   - 注入dataSource数据源

   - SqlSessionFactory

   ```xml
   <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
       <property name="dataSource" ref="dataSource" />
       <property name="configLocation" value="mybatis.xml"/>
       <property name="mapperLocations" value="classpath*:mapper/**/*.xml" />
   </bean>
   ```

   - 注册mapper

	```xml
	<bean id="userMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
       <property name="mapperInterface" value="com.cptz.mapper.UserMapper" />
       <property name="sqlSessionFactory" ref="sqlSessionFactory" />
</bean>
	```

3. 引用的mybatis.xml文件

4. 编写业务实体类

5. 编写Dao层接口类

   ```java
   public interface UserMapper {
       public List<User> selectUser();
   }
   ```

6. 测试

   ```java
   @Test
   public void userTest() {
       ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
       UserMapper userMapper = ctx.getBean("userMapper", UserMapper.class);
       userMapper.selectUser().forEach(System.out::println);
   }
   ```


__事务__

以织入方式添加事务

1. 引入依赖

   ```xml
   <dependency>
       <groupId>org.aspectj</groupId>
       <artifactId>aspectjweaver</artifactId>
       <version>1.9.6</version>
   </dependency>
   ```

2. 配置核心配置文件

   手动获取sqlsession方式

   1. 配置SqlSessionTemplate

   ```xml
   <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
       <constructor-arg index="0" ref="sqlSessionFactory" />
   </bean>
   ```

   2. 注册mapper bean

   ```xml
   <bean id="userMapper" class="com.cptz.mapper.UserMapperImpl">
       <property name="sqlSessionFactory" ref="sqlSessionFactory" />
   </bean>
   ```

   3. 配置声明式事务

   ```xml
   <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
       <constructor-arg ref="dataSource" />
   </bean>
   ```

   4. 结合AOP实现事务的织入

   ```xml
   <!-- 配置事务通知 -->
   <tx:advice id="txAdvice" transaction-manager="transactionManager">
       <tx:attributes>
           <!-- 给哪些方法配置事务 -->
           <!-- 事务传播特性 -->
           <tx:method name="*" propagation="REQUIRED"/>
       </tx:attributes>
   </tx:advice>
   
   <!-- 配置事务切入 -->
   <aop:config>
       <aop:pointcut id="pointCut" expression="execution(* com.cptz.mapper.*.*(..))"/>
       <aop:advisor advice-ref="txAdvice" pointcut-ref="pointCut"/>
   </aop:config>
   ```

3. 编写mapper实现类，继承==SqlSessionDaoSupport==

```java
public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper {

    @Override
    public List<User> selectUser() {
        UserMapper mapper = getSqlSession().getMapper(UserMapper.class);
        User user = new User(4, "王五", "444444");

        mapper.insert(user);
        mapper.delete(4);

        return mapper.selectUser();
    }
    ...
}
```



