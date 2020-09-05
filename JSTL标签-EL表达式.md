

# JSTL标签-EL表达式

## 快速开始

__注意：JSTL标签和Servlet及JSP页面有着比较严格的版本对应关系！！__

1. 导入依赖

```xml
<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>jsp-api</artifactId>
    <version>2.2.1-b03</version>
</dependency>
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
</dependency>
```

2. jsp页面导入标签头

```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
```

3. 输出

```jsp
<c:out value="${user.name}"></c:out>
<c:out value="${user["first-name"]}"></c:out>
<c:out value="${user[param]}"></c:out>
```

4. \<c:if\>条件标签

   __举例__

```jsp
<c:if test="${!empty user}">
    <li>${user.name}</li>
</c:if>
```
​				__语法__

```jsp
<c:if test="xxx" [var="xxx"] [scope="{page|request|session|application}"]>
    执行体
</c:if>
```

​		var属性：用于指定逻辑表达式中变量的名字

5. \<c:forEach\>标签

   __举例__

```jsp
<c:forEach items="${PList}" var="p" varStatus="s">
	<tr>
        <td>${s.count}</td>
        <td>${p.name}</td>
        <td>${p.price}</td>
    </tr>
</c:forEach>

<c:forEach begin="0" end="5" var="i">
	<li>${i}</li>
</c:forEach>
```

varStatus属性用于指定当前迭代状态信息的对象保存到page域中的名称

varStatus属性可以获取以下信息：

- count：表示元素在集合中的序号，从1开始

- index：表示当前元素在集合中的索引，从0开始

- first：表示当前是否为集合中的第一个元素

- last：表示当前元素是否为集合中最后一个元素

​				__语法__

```jsp
<c:forEach items="xxx" [var="xxx"] [varStatus="xxx"] [begin="xxx"] [end="xxx"] [step="xxx"]>
	循环体
</c:forEach>

<c:forEach begin="xxx" end="xxx" [var="xxx"] [varStatus="xxx"] [step="xxx"]>
	循环体
</c:forEach>
```

6. set标签

   __举例__

```jsp
<c:set var="age" scope="application">eleven</c:set>
```

```jsp
<jsp:useBean id="person" class="com.cptz.Person"</jsp:useBean>

<c:set target="${person}" property="name" value="zhangsan"></c:set>
```

​				__语法__



## 深入学习

__EL(Expression Language)__

1. 作用域

![img](E:\GitHub\StudyNotes\JSTL标签-EL表达式.assets\690102-20160322224345620-404689335.jpg)

如：${temp}

​		如果没有做范围的限定，会依据默认从小到大开始搜索，如果还是找不到，则输出空字符串。

![img](E:\GitHub\StudyNotes\JSTL标签-EL表达式.assets\690102-20160322224627136-109349515.jpg)

当然也可写成：${pageScope.temp}

2. EL自动类型转换
3. 隐式对象

![img](E:\GitHub\StudyNotes\JSTL标签-EL表达式.assets\690102-20160325214811261-978671786.png)

4. 运算符

![img](E:\GitHub\StudyNotes\JSTL标签-EL表达式.assets\690102-20160325215303683-1809648491.png)

el表达式中empty运算符对字符串为Null、""都返回 <kb>TRUE</kb>！！

__JSTL（JSP Standard Tag Library)__

1. 常见库

- Core （核心库）：http://java.sun.com/jsp/jstl/core  常用前缀：c
- I18N（国际化）：http://java.sun.com/jsp/jstl/fmt 常用前缀：fmt
- SQL：http://java.sun.com/jsp/jstl/sql  常用前缀：sql
- XML：http://java.sun.com/jsp/jstl/xml 常用前缀：x
- Functions：http://java.sun.com/jsp/jstl/functions  常用前缀：fn 

2. 常用函数

- contains函数、startsWith、endsWith

```jsp
<!-- JSTL函数使用 -->
<c:out value="${fn:contains('Hello World','Hello')}"></c:out>
<hr>
<c:out value="${fn:contains('Hello World','ABCD')}"></c:out>
<hr>
<c:out value="${fn:containsIgnoreCase('Hello World','hello')}"></c:out>
<hr>
<c:out value="${fn:startsWith('Hello World','Hello')}"></c:out>
<hr>
<c:out value="${fn:endsWith('Hello World','world')}"></c:out>
```

3. 各库标签

__c库__

| 标签                                                         | 描述                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [<c:out>](http://www.runoob.com/jsp/jstl-core-out-tag.html)  | 用于在JSP中显示数据，就像<%= ... >                           |
| [<c:set>](http://www.runoob.com/jsp/jstl-core-set-tag.html)  | 用于保存数据                                                 |
| [<c:remove>](http://www.runoob.com/jsp/jstl-core-remove-tag.html) | 用于删除数据                                                 |
| [<c:catch>](http://www.runoob.com/jsp/jstl-core-catch-tag.html) | 用来处理产生错误的异常状况，并且将错误信息储存起来           |
| [<c:if>](http://www.runoob.com/jsp/jstl-core-if-tag.html)    | 与我们在一般程序中用的if一样                                 |
| [<c:choose>](http://www.runoob.com/jsp/jstl-core-choose-tag.html) | 本身只当做\<c:when\>和\<c:otherwise\>的父标签                |
| [<c:when>](http://www.runoob.com/jsp/jstl-core-choose-tag.html) | \<c:choose\>的子标签，用来判断条件是否成立                   |
| [<c:otherwise>](http://www.runoob.com/jsp/jstl-core-choose-tag.html) | \<c:choose\>的子标签，接在\<c:when\>标签后，当\<c:when\>标签判断为false时被执行 |
| [<c:import>](http://www.runoob.com/jsp/jstl-core-import-tag.html) | 检索一个绝对或相对 URL，然后将其内容暴露给页面               |
| [<c:foreach>](http://www.runoob.com/jsp/jstl-core-foreach-tag.html) | 基础迭代标签，接受多种集合类型                               |
| [<c:forTokens>](http://www.runoob.com/jsp/jstl-core-foreach-tag.html) | 根据指定的分隔符来分隔内容并迭代输出                         |
| [<c:param>](http://www.runoob.com/jsp/jstl-core-param-tag.html) | 用来给包含或重定向的页面传递参数                             |
| [<c:redirect>](http://www.runoob.com/jsp/jstl-core-redirect-tag.html) | 重定向至一个新的URL.                                         |
| [<c:url>](http://www.runoob.com/jsp/jstl-core-url-tag.html)  | 使用可选的查询参数来创造一个URL                              |

__fmt库__

| 标签                                                         | 描述                                     |
| ------------------------------------------------------------ | ---------------------------------------- |
| [<fmt:formatNumber>](http://www.runoob.com/jsp/jstl-format-formatnumber-tag.html) | 使用指定的格式或精度格式化数字           |
| [<fmt:parseNumber>](http://www.runoob.com/jsp/jstl-format-parsenumber-tag.html) | 解析一个代表着数字，货币或百分比的字符串 |
| [<fmt:formatDate>](http://www.runoob.com/jsp/jstl-format-formatdate-tag.html) | 使用指定的风格或模式格式化日期和时间     |
| [<fmt:parseDate>](http://www.runoob.com/jsp/jstl-format-parsedate-tag.html) | 解析一个代表着日期或时间的字符串         |
| [<fmt:bundle>](http://www.runoob.com/jsp/jstl-format-bundle-tag.html) | 绑定资源                                 |
| [<fmt:setLocale>](http://www.runoob.com/jsp/jstl-format-setlocale-tag.html) | 指定地区                                 |
| [<fmt:setBundle>](http://www.runoob.com/jsp/jstl-format-setbundle-tag.html) | 绑定资源                                 |
| [<fmt:timeZone>](http://www.runoob.com/jsp/jstl-format-timezone-tag.html) | 指定时区                                 |
| [<fmt:setTimeZone>](http://www.runoob.com/jsp/jstl-format-settimezone-tag.html) | 指定时区                                 |
| [<fmt:message>](http://www.runoob.com/jsp/jstl-format-message-tag.html) | 显示资源配置文件信息                     |
| [<fmt:requestEncoding>](http://www.runoob.com/jsp/jstl-format-requestencoding-tag.html) | 设置request的字符编码                    |

__sql库__

| 标签                                                         | 描述                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [<sql:setDataSource>](http://www.runoob.com/jsp/jstl-sql-setdatasource-tag.html) | 指定数据源                                                   |
| [<sql:query>](http://www.runoob.com/jsp/jstl-sql-query-tag.html) | 运行SQL查询语句                                              |
| [<sql:update>](http://www.runoob.com/jsp/jstl-sql-update-tag.html) | 运行SQL更新语句                                              |
| [<sql:param>](http://www.runoob.com/jsp/jstl-sql-param-tag.html) | 将SQL语句中的参数设为指定值                                  |
| [<sql:dateParam>](http://www.runoob.com/jsp/jstl-sql-dateparam-tag.html) | 将SQL语句中的日期参数设为指定的java.util.Date 对象值         |
| [<sql:transaction>](http://www.runoob.com/jsp/jstl-sql-transaction-tag.html) | 在共享数据库连接中提供嵌套的数据库行为元素，将所有语句以一个事务的形式来运行 |

__XML库__

| 标签                                                         | 描述                                                      |
| ------------------------------------------------------------ | --------------------------------------------------------- |
| [<x:out>](http://www.runoob.com/jsp/jstl-xml-out-tag.html)   | 与<%= ... >,类似，不过只用于XPath表达式                   |
| [<x:parse>](http://www.runoob.com/jsp/jstl-xml-parse-tag.html) | 解析 XML 数据                                             |
| [<x:set>](http://www.runoob.com/jsp/jstl-xml-set-tag.html)   | 设置XPath表达式                                           |
| [<x:if>](http://www.runoob.com/jsp/jstl-xml-if-tag.html)     | 判断XPath表达式，若为真，则执行本体中的内容，否则跳过本体 |
| [<x:forEach>](http://www.runoob.com/jsp/jstl-xml-foreach-tag.html) | 迭代XML文档中的节点                                       |
| [<x:choose>](http://www.runoob.com/jsp/jstl-xml-choose-tag.html) | \<x:when\>和\<x:otherwise\>的父标签                       |
| [<x:when>](http://www.runoob.com/jsp/jstl-xml-choose-tag.html) | \<x:choose\>的子标签，用来进行条件判断                    |
| [<x:otherwise>](http://www.runoob.com/jsp/jstl-xml-choose-tag.html) | \<x:choose\>的子标签，当\<x:when\>判断为false时被执行     |
| [<x:transform>](http://www.runoob.com/jsp/jstl-xml-transform-tag.html) | 将XSL转换应用在XML文档中                                  |
| [<x:param>](http://www.runoob.com/jsp/jstl-xml-param-tag.html) | 与\<x:transform\>共同使用，用于设置XSL样式表              |

__fn库__

| 函数                                                         | 描述                                                     |
| ------------------------------------------------------------ | -------------------------------------------------------- |
| [fn:contains()](http://www.runoob.com/jsp/jstl-function-contains.html) | 测试输入的字符串是否包含指定的子串                       |
| [fn:containsIgnoreCase()](http://www.runoob.com/jsp/jstl-function-containsignoreCase.html) | 测试输入的字符串是否包含指定的子串，大小写不敏感         |
| [fn:endsWith()](http://www.runoob.com/jsp/jstl-function-endswith.html) | 测试输入的字符串是否以指定的后缀结尾                     |
| [fn:escapeXml()](http://www.runoob.com/jsp/jstl-function-escapexml.html) | 跳过可以作为XML标记的字符                                |
| [fn:indexOf()](http://www.runoob.com/jsp/jstl-function-indexof.html) | 返回指定字符串在输入字符串中出现的位置                   |
| [fn:join()](http://www.runoob.com/jsp/jstl-function-join.html) | 将数组中的元素合成一个字符串然后输出                     |
| [fn:length()](http://www.runoob.com/jsp/jstl-function-length.html) | 返回字符串长度                                           |
| [fn:replace()](http://www.runoob.com/jsp/jstl-function-replace.html) | 将输入字符串中指定的位置替换为指定的字符串然后返回       |
| [fn:split()](http://www.runoob.com/jsp/jstl-function-split.html) | 将字符串用指定的分隔符分隔然后组成一个子字符串数组并返回 |
| [fn:startsWith()](http://www.runoob.com/jsp/jstl-function-startswith.html) | 测试输入字符串是否以指定的前缀开始                       |
| [fn:substring()](http://www.runoob.com/jsp/jstl-function-substring.html) | 返回字符串的子集                                         |
| [fn:substringAfter()](http://www.runoob.com/jsp/jstl-function-substringafter.html) | 返回字符串在指定子串之后的子集                           |
| [fn:substringBefore()](http://www.runoob.com/jsp/jstl-function-substringbefore.html) | 返回字符串在指定子串之前的子集                           |
| [fn:toLowerCase()](http://www.runoob.com/jsp/jstl-function-tolowercase.html) | 将字符串中的字符转为小写                                 |
| [fn:toUpperCase()](http://www.runoob.com/jsp/jstl-function-touppercase.html) | 将字符串中的字符转为大写                                 |
| [fn:trim()](http://www.runoob.com/jsp/jstl-function-trim.html) | 移除首位的空白符                                         |