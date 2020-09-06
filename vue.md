# Vue

## Hello world!

```html
<body>
    <div id="app">
        {{counter}}
        <ul>
            <li v-for="item in movies">{{item}}</li>
        </ul>
        <button v-on:click="add">+</button>
        <button v-on:click="sub">-</button>
    </div>

    <script src="../js/vue.min.js"></script>
    <script>
        let app = new Vue({
            el: "#app",
            data: {
                counter: 0,
                movies: ["aaa", "bbb", "ccc"]
            },
            methods: {
                add: function() {
                    this.counter++;
                    console.log("+");
                },
                sub: function () {
                    this.counter--;
                    console.log("-");
                }
            }
        });
    </script>
</body>
```

注意：此异常可能是由引入的vue.js为开发版本引起，改为生产版本即可！

![image-20200904140242682](vue.assets/image-20200904140242682.png)

## MVVM

## 生命周期

钩子函数：

![Vue 实例生命周期](E:\GitHub\StudyNotes\vue.assets\lifecycle.png)

## 选项

el: string | HTMLElement

data: Object | Function(组件当中必须是一个函数)

methods: [key: string]: Function

## 指令

- Mustache语法：{{variable}}只作用于标签内容，不能用于属性值的绑定。
- v-once：仅在初始化时渲染一次数据，后续变动无影响。
- v-html：将渲染为html标签。
- v-text：等效于Mustache语法
- v-pre：等效于\<pre\>标签
- v-cloak：斗篷，用于隐藏在加载延迟时的未渲染模板。

```html
<head>
    <meta charset="utf-8">
    <title>title</title>
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>
<div id="app" v-cloak>
    <h2>{{firstName + " " + lastName}}</h2>
    <h2>{{counter * 2}}</h2>
    <h2 v-once>{{message}}</h2>
    <h2 v-html="url"></h2>
    <h2 v-text="message"></h2>
    <h2 v-pre>{{origin text}}</h2>

</div>

<script src="../js/vue.min.js"></script>
<script>
    let app = new Vue({
        el: "#app",
        data: {
            message: "只初始化绑定",
            counter: 4,
            firstName: "kobe",
            lastName: "bryant",
            url: "<a href='https://www.baidu.com'>百度一下</a>"
        }
    });
</script>
</body>
```

- v-bind：用于绑定标签属性
  - `v-bind:href`可以简写为`:href`

```html
<body>
<div id="app">
    <img v-bind:src="imageURL" alt="">
    <a :href="mHref">百度一下</a>
</div>

<script src="../js/vue.min.js"></script>
<script>
    let app = new Vue({
        el: "#app",
        data: {
            imageURL: "https://img.jpg",
            mHref: "https://www.baidu.com"
        }
    });
</script>
</body>
```

​		绑定class

```html
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        .active {
            color: #ff0000;
        }
    </style>
</head>
<body>
<div id="app">
    <h2 class="title" :class="{active: isActive, line: isLine}">{{message}}</h2>
    <button v-on:click="btnClick">点我</button>
</div>
<script src="../js/vue.min.js"></script>
<script>
    let app = new Vue({
        el: "#app",
        data: {
            message: "hello world!",
            isActive: true,
            isLine: true
        },
        methods: {
            btnClick: function () {
                this.isActive = !this.isActive;
            }
        }
    });
</script>
</body>
```

## 计算属性

```html
<body>
<div id="app">
    <h2>{{totalPrice}}</h2>
</div>

<script src="../js/vue.min.js"></script>
<script>
    let app = new Vue({
        el: "#app",
        data: {
            books: [
                {id: "001", name: "Unix", price: 100},
                {id: "002", name: "Linux", price: 100},
                {id: "003", name: "Java", price: 100},
                {id: "004", name: "Python", price: 100}
            ]
        },
        computed: {
            totalPrice: function() {
                let price = 0;
                for (let i = 0; i < this.books.length; i++) {
                    price += this.books[i].price;
                }
                return price;
            }
        }
    });
</script>
</body>
```

- set和get属性

  ​		由于几乎所有情况下只用到get方法，因此直接声明function成为get方法的默认写法。

```js
computed: {
  // 计算属性一般没有set属性，即为只读属性
  fullName: {
    set: function (newValue) {
      let name = newValue.split(" ");
      this.firstName = name[0];
      this.lastName = name[1];
    },
    get: function () {
      return this.firstName + " " + this.lastName;
    }
  }
},
```

- 计算属性的缓存

  相比于methods，computed方法，如果变量没有变化，只会调用一次。

## 事件监听

- v-on，缩写：@

  ```html
  <body>
  <div id="app">
      <h2>{{counter}}</h2>
      <button v-on:click="inc">+</button>
      <button @click="dec">-</button>
  </div>
  <script src="../js/vue.min.js"></script>
  <script>
      let app = new Vue({
          el: "#app",
          data: {
              counter: 0
          },
          methods: {
              inc() {
                  this.counter++;
              },
              dec() {
                  this.counter--;
              }
          }
      });
  </script>
  </body>
  ```

- v-on参数问题

  如果在调用有参函数时，不传参数，会默认传递mouseEvent对象。若想显式传递，用$event

- v-on修饰符

  .stop：调用event.stopPropagation()

  .prevent：调用event.preventDefault()

  .{keyCode | keyAlias}：监听指定键事件

  .native：监听组件根元素的原生事件

  .once：只触发一次回调

  ```html
  <body>
  <div id="app">
      <div @click="divClick">
          aaaaaa
          <button @click.stop="btnClick">阻止冒泡事件</button>
      </div>
      <hr/>
  
      <form action="baidu">
          <input type="submit" value="阻止默认事件" @click.prevent="formClick"/>
      </form>
      <hr/>
      <input type="input" @keyUp.enter="keyUp">
  </div>
  <script src="../js/vue.min.js"></script>
  <script>
      let app = new Vue({
          el: "#app",
          data: {
              counter: 0
          },
          methods: {
              btnClick() {
                  console.log("btnClick")
              },
              divClick() {
                  console.log("divClick")
              },
              formClick() {
                  console.log("formClick")
              },
              keyUp() {
                  console.log("keyUp")
              }
          }
      });
  </script>
  </body>
  ```

## 条件判断

```html
<body>
<div id="app">
    <h2 v-if="score >= 90">优</h2>
    <h2 v-else-if="score >= 80">良</h2>
    <h2 v-else-if="score >= 70">中</h2>
    <h2 v-else>差</h2>
</div>
<script src="../js/vue.min.js"></script>
<script>
    let app = new Vue({
        el: "#app",
        data: {
            score: 80
        }
    });
</script>
</body>
```

## 虚拟dom复用问题

​		加入key属性，使其不复用。

```html
<body>
<div id="app">
    <span v-if="isUser">
        <label for="username">用户名</label>
        <input type="text" id="username" key="username"/>
    </span>
    <span v-else>
        <label for="email">邮箱</label>
        <input type="email" id="email" key="email"/>
    </span>
    <button @click="isUser = !isUser">切换</button>
</div>
<script src="../js/vue.min.js"></script>
<script>
    let app = new Vue({
        el: "#app",
        data: {
            isUser: true
        }
    });
</script>
</body>
```

## v-show

​		与v-if间区别，v-if为false时，从dom中删除元素，v-show仅隐藏。

## 循环

​		建议在v-for中绑定key，以优化diff算法效率。

```html
<body>
<div id="app">
    <ul>
        <li v-for="(value, key, index) in person" :key="value">
            {{index}}.{{key}}: {{value}}
        </li>
    </ul>
</div>
<script src="../js/vue.min.js"></script>
<script>
    let app = new Vue({
        el: "#app",
        data: {
            person: {
                name: "why",
                height: 1.88,
                age: 18
            }
        }
    });
</script>
</body>
```

​		数组中可以做到响应式的方法：索引修改数组元素不可以响应！

- push()/pop()
- shift()/unshift()
- splice()
- sort()
- reverse()
- Vue.set()

## 过滤器

![image-20200906084619741](E:\GitHub\StudyNotes\vue.assets\image-20200906084619741.png)

```html
<body>
<div id="app">
    <td>{{price | priceFilter}}</td>
</div>
<script src="../js/vue.min.js"></script>
<script>
    let app = new Vue({
        el: "#app",
        data: {
            price: 150
        },
        filters: {
            priceFilter(value) {
                return "$" + value.toFixed(2);
            }
        }
    });
</script>
</body>
```

## v-model

```html
<input type="text" v-model="message"/>{{message}}
等效于
<input type="text" :value="message" @input="message = $event.target.value"/>
```

- 与控件间配合

  ```html
  <body>
  <div id="app">
      <input type="text" v-model.lazy="message"/>
      {{message}}
  
      <hr/>
  
      <label>
          <input type="radio" value="男" v-model="gender">男
          <input type="radio" value="女" v-model="gender">女
          {{gender}}
      </label>
  
      <hr/>
  
      <label for="protocol">
          <input type="checkbox" name="protocol" id="protocol" v-model="isAgree">同意协议
          <button :disabled="!isAgree">下一步</button>
          {{isAgree}}
      </label>
  
      <hr/>
  
      <div>
          <input type="checkbox" value="唱" v-model="hobbies"/>唱
          <input type="checkbox" value="跳" v-model="hobbies"/>跳
          <input type="checkbox" value="rap" v-model="hobbies"/>rap
          <input type="checkbox" value="篮球" v-model="hobbies"/>篮球
          {{hobbies}}
      </div>
  
      <hr/>
  
      <label for="cardType">
          <select id="cardType" name="cardType" v-model="cardType">
              <option value="ID card">ID card</option>
              <option value="passport">passport</option>
              <option value="credit card">credit card</option>
              <option value="driver license">driver license</option>
          </select>
          {{cardType}}
      </label>
  
      <hr/>
  
      <label for="multi">
          <select id="multi" name="cardType" v-model="cardTypes" multiple>
              <option value="ID card">ID card</option>
              <option value="passport">passport</option>
              <option value="credit card">credit card</option>
              <option value="driver license">driver license</option>
          </select>
          {{cardTypes}}
      </label>
  
  </div>
  <script src="../js/vue.min.js"></script>
  <script>
      let app = new Vue({
          el: "#app",
          data: {
              message: "hello",
              gender: "男",
              isAgree: true,
              hobbies: [],
              cardType: "ID card",
              cardTypes: []
          }
      });
  </script>
  </body>
  ```

- 修饰符.lazy .number .trim

## 组件

- 创建组件构造器对象
- 注册组件
- 使用组件

1. 全局组件，可以在多个Vue实例下使用

```html
<body>
<div id="app">
    <comp-name></comp-name>
</div>

<script src="../js/vue.min.js"></script>
<script>
    // 创建组件构造器对象
    const compC = Vue.extend({
        template: `
            <div>
                <h2>标题</h2>
                <p>内容p1</p>
                <p>内容p2</p>
            </div>`
    });

    // 注册组件
    Vue.component("comp-name", compC);
    let app = new Vue({
        el: "#app",
        data: {}
    });
</script>
</body>
```

2. 局部注册

```html
<body>
<div id="app">
    <mycomp></mycomp>
</div>

<script src="../js/vue.min.js"></script>
<script>
    // 创建组件构造器对象
    const compC = Vue.extend({
        template: `
            <div>
                <h2>标题</h2>
                <p>内容p1</p>
                <p>内容p2</p>
            </div>`
    });

    // 注册组件
    let app = new Vue({
        el: "#app",
        components: {
            mycomp: compC
        }
    });
</script>
</body>
```

3. 注意

   由于标签不区分大小写，而vue渲染时大小写敏感，因此命名组件标签名时，必须全小写！如中间包含连字符，需用双引号引起，否则失效。

4. 父子组件

   注意：声明顺序，必须在前面声明之后才可以使用！

```html
<body>
<div id="app">
    <compp></compp>
</div>

<script src="../js/vue.min.js"></script>
<script>
    const compC = Vue.extend({
        template: `
            <div>
                <h2>子组件</h2>
                <p>子内容p1</p>
                <p>子内容p2</p>
            </div>`
    });

    const compP = Vue.extend({
        template: `
            <div>
                <h2>父组件</h2>
                <p>父内容p1</p>
                <compc></compc>
                <p>父内容p2</p>
            </div>`,
        components: {
            compc: compC
        }
    });
    let app = new Vue({
        el: "#app",
        components: {
            "compp": compP
        }
    });
</script>
</body>
```

4. 组件的语法糖写法

   即用对象代替Vue.extend方法

```html
<body>
<div id="app">
    <mycomp></mycomp>
</div>

<script src="../js/vue.min.js"></script>
<script>
    let compObj = {
        template: `
            <div>
                <h2>标题</h2>
                <p>内容p1</p>
                <p>内容p2</p>
            </div>`
    };
    // 注册组件
    let app = new Vue({
        el: "#app",
        components: {
            mycomp: compObj
        }
    });
</script>
</body>
```

5. 组件的简单分离式写法

```html
<body>
<div id="app">
    <mycomp></mycomp>
</div>
<!-- 写法1 -->
<!--<script type="text/x-template" id="my-comp">-->
<!--    <div>-->
<!--        <h2>标题</h2>-->
<!--        <p>内容p1</p>-->
<!--        <p>内容p2</p>-->
<!--    </div>-->
<!--</script>-->
<!-- 写法2 -->
<template id="my-comp">
    <div>
        <h2>标题</h2>
        <p>内容p1</p>
        <p>内容p2</p>
    </div>
</template>
<script src="../js/vue.min.js"></script>
<script>
    // 注册组件
    let app = new Vue({
        el: "#app",
        components: {
            mycomp: {
                template: "#my-comp"
            }
        }
    });
</script>
</body>
```

6. 组件模板传参

```html
<body>
<div id="app">
    <mycomp></mycomp>
</div>

<template id="my-comp">
    <div>
        <h2>{{counter}}</h2>
        <button @click="dec">-</button>
        <button @click="inc">+</button>
    </div>
</template>
<script src="../js/vue.min.js"></script>
<script>
    // 注册组件
    Vue.component("mycomp", {
        template: "#my-comp",
        data() {
            return {
                counter: 0
            };
        },
        methods: {
            dec() {
                this.counter--;
            },
            inc() {
                this.counter++;
            }
        }
    });
    let app = new Vue({
        el: "#app"
    });
</script>
</body>
```

7. 组件通信

   - 组件必须有一个明确的根节点
   - 驼峰标识会拆分为连字符形式

   __父向子通信__

   ```html
   <body>
   <div id="app">
       <mycomp :cmovies="movies"></mycomp>
   </div>
   
   <template id="mycomp">
       <ul>
           <li v-for="cmovie in cmovies">{{cmovie}}</li>
       </ul>
   </template>
   <script src="../js/vue.min.js"></script>
   <script>
       const mycomp = {
           template: "#mycomp",
           props: ["cmovies"]
       };
       const app = new Vue({
           el: "#app",
           data: {
               info: {
                   name: "zhangsan",
                   age: 18,
                   height: 1.88
               },
               movies: ["aaa", "bbb", "ccc"]
           },
           components: {
               mycomp
           }
       });
   </script>
   </body>
   ```

   - 支持的props写法

     关于default默认值：如果是Object类型，default类型需为函数，其余类型可以为基础类型。

   ```js
   props: ["cmovies"]
   
   props: {
       cmovies: "Array"
   }
   
   props: {
       cmovies: {
           type: "Array",
           default() {
               return [];
           },
           required: true,
           validator(value) {
               return value.length > 1;
           }
       }
   }
   ```

   - 验证支持数据类型
     - String
     - Number
     - Boolean
     - Array
     - Object
     - Date
     - Function
     - Symbol

   - 自定义验证方法

   ```js
   props: {
       cmovies: {
           type: "Array",
           default() {
               return [];
           },
           required: true,
           validator(value) {
               return value.length > 1;
           }
       }
   }
   ```

   __子向父通信__

   ```html
   <body>
   <div id="app">
       <mycomp @child-click="childClick"></mycomp>
   </div>
   
   <template id="mycomp">
       <ul>
           <li v-for="item in catalogs" @click="btnclick(item)">{{item.name}}</li>
       </ul>
   </template>
   
   <script src="../js/vue.min.js"></script>
   <script>
       const mycomp = {
           template: "#mycomp",
           data() {
               return {
                   catalogs: [
                       {id: "aaa", name: "aaa"},
                       {id: "bbb", name: "bbb"},
                       {id: "ccc", name: "ccc"},
                       {id: "ddd", name: "ddd"},
                   ]
               }
           },
           methods: {
               btnclick(item) {
                   this.$emit("child-click", item);
               }
           }
       };
   
       let app = new Vue({
           el: "#app",
           components: {
               mycomp
           },
           methods: {
               childClick(item) {
                   console.log(JSON.stringify(item));
               }
           }
       });
   </script>
   </body>
   ```

8. 



1. 