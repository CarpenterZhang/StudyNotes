# mybatis

[mybatis3 中文官网](https://mybatis.org/mybatis-3/zh/index.html)

## 快速使用

__1.新建项目__

![image-20200809135722482](E:\documents\note\mybatis.assets\image-20200809135722482.png)

__2.依赖__

```xml
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
	<version>8.0.21</version>
</dependency>
<dependency>
	<groupId>org.mybatis</groupId>
	<artifactId>mybatis</artifactId>
	<version>3.5.5</version>
</dependency>
<dependency>
	<groupId>junit</groupId>
	<artifactId>junit</artifactId>
	<version>4.13</version>
</dependency>
<dependency>
	<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
	<version>1.18.12</version>
</dependency>
```

__3.创建核心配置文件__

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
  <properties resource="db.properties"/>
  <settings>
      <setting name="mapUnderscoreToCamelCase" value="true"/>
      <setting name="logImpl" value="LOG4J"/>
  </settings>
  <environments default="development">
    <environment id="development">
      <transactionManager type="JDBC"/>
      <dataSource type="POOLED">
        <property name="driver" value="${driver}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${username}"/>
        <property name="password" value="${password}"/>
      </dataSource>
    </environment>
  </environments>
  <mappers>
    <!-- 注册mapper -->
    <mapper resource="mapper/UserMapper.xml"/>
  </mappers>
</configuration>
```

__4.创建工具类，获取SqlSession__

```java
public class MybatisUtil {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession getSqlsession() {
        return sqlSessionFactory.openSession();
    }
}
```

__5.编写mapper.xml__

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.cptz.mapper.UserMapper">
    <select id="getUserList" resultType="com.cptz.pojo.User">
        select * from user
    </select>
</mapper>
```

1. namespace严格与mapper一致，方便接口绑定
2. sql语句id严格与mapper接口中方法名一致
3. resultType为全限定类名

__6.编写User pojo实体类__

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

__7.设置maven静态资源过滤__

```xml
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
    </resources>
</build>
```

__8.调用测试__

```java
@Test
public static void main(String[] args) throws IOException {
    SqlSession sqlsession = MybatisUtil.getSqlsession();
    UserMapper userMapper = sqlsession.getMapper(UserMapper.class);
    List<User> userList = userMapper.getUserList();
    for (User user : userList) {
        System.out.println(user);
    }
    sqlsession.close();
}
```

## 核心配置文件解读

1. typeAliases

   设置此项后，在mapper.xml中，resultType="com.cptz.pojo.User"即可简化为resultType="User"。

```xml
<typeAliases>
    <package name="com.cptz.pojo"/>
</typeAliases>
```

2. properties

   设置此项后，此后的核心配置文件中便可以#{jdbc.driver}引用db.properties中的值。

```xml
<properties resource="db.properties"/>
```

 3. environments

    此项下可以设置多个环境，default选项指定具体使用环境。

4. transactionManager

   - JDBC – 这个配置直接使用了 JDBC 的提交和回滚设施，它依赖从数据源获得的连接来管理事务作用域。

   - MANAGED – 这个配置几乎没做什么。它从不提交或回滚一个连接，而是让容器来管理事务的整个生命周期（比如 JEE 应用服务器的上下文）。 默认情况下它会关闭连接。然而一些容器并不希望连接被关闭，因此需要将 closeConnection 属性设置为 false 来阻止默认的关闭行为。(EJB)

```xml
<transactionManager type="JDBC"/>
```

5. 数据源（dataSource）

   dataSource 元素使用标准的 JDBC 数据源接口来配置 JDBC 连接对象的资源。

   UNPOOLED/POOLED/JNDI

   **UNPOOLED**– 这个数据源的实现会每次请求时打开和关闭连接。

   **POOLED**– 这种数据源的实现利用“池”的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间。

   **JNDI** – 这个数据源实现是为了能在如 EJB 或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个 JNDI 上下文的数据源引用。

6. 设置（settings）

   mapUnderscoreToCamelCase：是否开启驼峰命名自动映射，即从经典数据库列名 A_COLUMN 映射到经典 Java 属性名 aColumn。

   logImpl：指定 MyBatis 所用日志的具体实现，未指定时将自动查找。
日志
| logImpl         |
| --------------- |
| SLF4J           |
| LOG4J           |
| LOG4J2          |
| JDK_LOGGING     |
| COMMONS_LOGGING |
| STDOUT_LOGGING  |
| NO_LOGGING      |
```xml
<settings>
	<setting name="logImpl" value="STDOUT_LOGGING"/>
    <setting name="mapUnderscoreToCamelCase" value="true"/>
</settings>
```
## 多对一

__1.建表__

```sql
create table teacher(
    id int(10) not null primary key ,
    name varchar(30) default null
) engine=innodb default charset=utf8

insert into teacher(id, name) value (1, '秦老师');

create table student(
    id int (10) not null primary key ,
    name varchar(30) default null,
    tid int(10) default null,
    key fktid (tid),
    constraint fktid foreign key (tid) references teacher(id)
) engine=innodb default charset =utf8

insert into student (id, name, tid) values (1, '小明', 1),
                                           (2, '小红', 1),
                                           (3, '小张', 1),
                                           (4, '小李', 1),
                                           (5, '小王', 1)
```
__2.bean__
__Student.java__
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;
    private String name;
    private Teacher teacher;
}
```
__Teacher.java__
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private int id;
    private String name;
}
```
__3.mapper__
__方式一：关联子查询__

```xml
<select id="getStudent" resultMap="StudentTeacher">
	select * from student
</select>

<resultMap id="StudentTeacher" type="Student">
	<result property="id" column="id" />
	<result property="name" column="name" />

	<association property="teacher" column="tid" javaType="Teacher" select="getTeacher" />
</resultMap>

<select id="getTeacher" resultType="Teacher">
	select * from teacher where id=#{id}
</select>
```
__方式二：联合查询__
```xml
<select id="getStudent2" resultMap="StudentTeacher2">
    select s.id as sid, s.name as sname, t.name as tname
    from student s, teacher t
    where s.tid=t.id
</select>

<resultMap id="StudentTeacher2" type="Student">
    <result property="id" column="sid"/>
    <result property="name" column="sname"/>

    <association property="teacher" javaType="Teacher">
    	<result property="name" column="tname" />
    </association>
</resultMap>
```

## 一对多
__1.建表__
__2.bean__
__Student.java__
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;
    private String name;
}
```
__Teacher.java__
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private int id;
    private String name;
    List<Student> studentList;
}
```
__3.mapper__
__方式一：关联子查询__

```xml
<select id="getTeacher2" resultMap="TeacherStudent2">
	select * from teacher t where id=#{tid}
</select>
<resultMap id="TeacherStudent2" type="Teacher">
	<collection property="studentList" column="id" javaType="list" ofType="Student" select="getStudentByTid"/>
</resultMap>
<select id="getStudentByTid" resultType="Student">
	select * from student where tid=#{id}
</select>
```
__方式二：联合查询__
```xml
<select id="getTeacher" resultMap="TeacherStudent">
    select t.id tid, t.name tname, s.id sid, s.name sname
    from teacher t,student s
    where t.id=s.tid
    and t.id = #{tid}
</select>
<resultMap id="TeacherStudent" type="Teacher">
    <result property="id" column="tid"/>
    <result property="name" column="tname"/>
    <collection property="studentList" javaType="list" ofType="Student">
        <result property="id" column="sid"/>
        <result property="name" column="sname"/>
    </collection>
</resultMap>
```

## 动态 SQL

- if
- choose (when, otherwise)
- trim (where, set)
- foreach

__if__

```xml
<select id="findActiveBlogWithTitleLike" resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’
  <if test="title != null">
    AND title like #{title}
  </if>
</select>
```

__choose、when、otherwise__

__trim、where、set__

```xml
<select id="findActiveBlogLike" resultType="Blog">
  SELECT * FROM BLOG
  <where>
    <if test="state != null">
         state = #{state}
    </if>
    <if test="title != null">
        AND title like #{title}
    </if>
    <if test="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
  </where>
</select>
```

__foreach__

```xml
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT * FROM POST P WHERE ID in
  <foreach item="item" index="index" collection="list" open="(" separator="," close=")">
      #{item}
  </foreach>
</select>
```

## 问题

__1.解决属性名和字段名不一致的问题__

结果集映射resultMap

```xml
<resultMap id="StudentTeacher" type="Student">
	<!-- property=类属性 column=sql查询列名 -->
    <result property="id" column="sid" />
    <result property="name" column="name" />
</resultMap>
```

__2.关于@Param()注解__

- 基本类型的参数或者String类型，需要加
- 引用类型不需要加
- 只有一个基本类型的话可以忽略

__3.#{  }和${  }之间的区别__

#{}防注入，${}直接拼接

