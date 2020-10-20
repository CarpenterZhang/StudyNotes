# Thymeleaf语法

[官方文档](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#standard-expression-syntax)

## 快速开始

1. 引入提示

   > In fact, we are even adding an `xmlns:th` attribute to our `<html>` tag, something absolutely non-HTML5-ish:
   >
   > …which has no influence at all in template processing, but works as an *incantation* that prevents our IDE from complaining about the lack of a namespace definition for all those `th:*` attributes.

   ```html
   <html xmlns:th="http://www.thymeleaf.org">
   ```

2. Standard Expression Syntax

   > - Simple expressions:
   >   - Variable Expressions: `${...}`
   >   - Selection Variable Expressions: `*{...}`
   >   - Message Expressions: `#{...}`
   >   - Link URL Expressions: `@{...}`
   >   - Fragment Expressions: `~{...}`
   > - Literals
   >   - Text literals: `'one text'`, `'Another one!'`,…
   >   - Number literals: `0`, `34`, `3.0`, `12.3`,…
   >   - Boolean literals: `true`, `false`
   >   - Null literal: `null`
   >   - Literal tokens: `one`, `sometext`, `main`,…
   > - Text operations:
   >   - String concatenation: `+`
   >   - Literal substitutions: `|The name is ${name}|`
   > - Arithmetic operations:
   >   - Binary operators: `+`, `-`, `*`, `/`, `%`
   >   - Minus sign (unary operator): `-`
   > - Boolean operations:
   >   - Binary operators: `and`, `or`
   >   - Boolean negation (unary operator): `!`, `not`
   > - Comparisons and equality:
   >   - Comparators: `>`, `<`, `>=`, `<=` (`gt`, `lt`, `ge`, `le`)
   >   - Equality operators: `==`, `!=` (`eq`, `ne`)
   > - Conditional operators:
   >   - If-then: `(if) ? (then)`
   >   - If-then-else: `(if) ? (then) : (else)`
   >   - Default: `(value) ?: (defaultvalue)`
   > - Special tokens:
   >   - No-Operation: `_`
   >
   > All these features can be combined and nested:

3. 举例

   1. Link URL Expressions

      ```html
      <link th:href="@{/css/bootstrap.min.css}" rel="stylesheet">
      
      <img th:src="@{/imgs/bootstrap-solid.svg}" width="72" height="72">
      
      <script th:src="@{https://cdn.bootcdn.net/ajax/libs/jquery/1.10.0/jquery.min.js}"></script>
      ```

   2. Fragment Expressions

      __结构__

      ![image-20200925093642044](E:\GitHub\StudyNotes\Thymeleaf语法.assets\image-20200925093642044.png)

      __common.html__

      ```html
      <!DOCTYPE html>
      <html lang="en" xmlns:th="http://www.thymeleaf.org">
      <nav th:fragment="topbar">
          ...
      </nav>
      
      <nav th:fragment="sidebar">
          ...
      </nav>
      </html>
      ```

      __employeeList.html__

      ```html
      <!DOCTYPE html>
      <html lang="en" xmlns:th="http://www.thymeleaf.org">
      ...
      <div th:replace="~{common/common::topbar}"></div>
      <div th:insert="~{common/common::sidebar}"></div>
      ...
      </html>
      ```

