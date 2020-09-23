## 组件化

- 父访问子组件：\$children-\$refs
- 子访问父组件：\$parent-\$root

```html
<body>
<div id="app">
    <cpn ref="firstCpn"></cpn>
    <button @click="btnClick">click</button>
</div>

<template id="cpn">
    <div>
        {{id}}
    </div>
</template>

<script src="../js/vue.min.js"></script>
<script>
    const cpn = {
        template: "#cpn",
        data() {
            return {
                id: "111"
            }
        },
        methods: {
            showMessage() {
                console.log("show message: " + this.id);
            }
        }
    };
    const app = new Vue({
        el: "#app",
        methods: {
            btnClick() {
                this.$children[0].showMessage();
                this.$refs.firstCpn.showMessage();
                console.log(this.$parent.name);
                console.log(this.$root.name);
            }
        },
        data: {
            name: "parent-name"
        },
        components: {
            cpn
        }
    });
</script>
</body>
```

## 插槽slot

```html
<body>
<div id="app">
    <cpn>
        <div slot="slot1">
            what the hell?
        </div>
        <div slot="slot2">
            <a href="https://www.baidu.com">百度</a>
        </div>
    </cpn>
    <hr/>
    <cpn></cpn>
</div>

<template id="cpn">
    <div>
        {{massage}}
        <slot name="slot1"><span>default left slot content</span></slot>
        <slot name="slot2"><button>default right slot content</button></slot>
    </div>
</template>

<script src="../js/vue.min.js"></script>
<script>
    const app = new Vue({
        el: "#app",
        components: {
            "cpn": {
                template: "#cpn",
                data() {
                    return {
                        massage: "cpn massage"
                    }
                }
            }
        }
    });
</script>
</body>
```

__作用域插槽__

```html
<body>
<div id="app">
    <cpn>
        <template slot-scope="qwerty">
            <span>{{qwerty.abc.join(" - ")}}</span>
            <span>{{qwerty.defghijk}}</span>
        </template>
    </cpn>
    <hr/>
    <cpn></cpn>
</div>

<template id="cpn">
    <div>
        <slot :abc="pLanguage" :defghijk="message">
            <ul>
                <li v-for="item in pLanguage">{{item}}</li>
            </ul>
        </slot>
    </div>
</template>

<script src="../js/vue.min.js"></script>
<script>
    const app = new Vue({
        el: "#app",
        components: {
            "cpn": {
                template: "#cpn",
                data() {
                    return {
                        pLanguage: ["java", "js", "python"],
                        message: "what?"
                    }
                }
            }
        }
    });
</script>
</body>
```

## 前端模块化

__ES6语法导入导出__

container.html

```html
<body>
<script src="aaa.js" type="module"></script>
<script src="bb.js" type="module"></script>
</body>
```

aaa.js

```javascript
let flag = true;
let massage = "why??";

export {
    flag,
    massage
};

export var name = "cpt";
```

bbb.js

```javascript
import {flag, massage, name} from "./aaa.js";

console.log(flag);
console.log(massage);
console.log(name);
```

__export default__

__import * as t from "./aaa.js";__

## webpack

1. 使用webpack将源代码编译

   1. 安装node环境 version>8.9

   2. 目录结构

      >project
      >	--dist
      >			bundle.js
      >	--src
      >			main.js
      >		index.html // 引用./dist/bundle.js
      >		webpack.config.js
      >		package.json

   3. 安装

      ```shell
      # 选择version3.6.0以支持vue cli2.0，-g全局安装，
      npm install webpack@3.6.0 -g
      
      # 验证是否安装成功
      webpack --version
      
      # 初始化，生成package.json
      # package name: meetwebpack
      # version:
      # description:
      # entry point: index.js
      # ...
      # Is this OK?
      npm init
      
      # 根据生成的package.json中定义依赖，安装（此处无依赖，无需安装）
      npm install
      ```

   4. 配置webpack.config.js

      ```js
      // 依赖path包
      import path = require('path');
      module.exports = {
        entry: './src/main.js',
        output: {
            path: path.resolve(__dirname, 'dist'), // 动态生成绝对路径
            filename: 'bundle.js',
        }
      };
      ```

   5. 运行

      ```shell
      cd ${project_path}
      # 打包时，将自动导出js间依赖
      # 配置package.json后，直接运行webpack命令即可
      webpack ./src/main.js ./dist/bundle.js
      ```

2. 将命令统一化为npm run build

   1. 安装局部环境

      ```shell
      # 局部安装
      npm install webpack@3.6.0 --save-dev
      ```

   2. 配置package.json

      配置scripts后执行命令，将优先运行本地命令。

      ```json
      {
          "name": "meetwebpack",
          ...
          "scripts": {
              "test": "echo sth.",
              "build": "webpack"
          }
      }
      ```

   3. 运行

      ```shell
      # 编译
      npm run build
      ```

3. loader[官网](https://webpack.js.org)

   __css-loader__

   1. 安装

      ```shell
      # 安装style-loader  css loader支持样式导出
      npm install --save-dev style-loader 
      npm install --save-dev css-loader
      ```

   2. 配置webpack.config.js

      ```js
      output: {...},
      modules: {
        rules: [
          {
            test: /\.css$/,
            // 使用多个loader时，从右向左加载
            use: ['style-loader', 'css-loader']
          }
        ]
      }
      ```

   3. js中引用

      >project
      >--src
      >	--js
      >		main.js
      >			require('./css/normal.css');
      >	--css
      >		normal.css

      main.js

      ```js
      require('./css/normal.css');
      ```

   __less-loader__

   1. 安装

      ```shell
      npm install --save-dev less-loader less
      ```

   2. 配置webpack.config.js

      ```js
      modules: {
        rules: [
          {
            test: /\.less/,
            ...
          }
        ]
      }
      ```

   __url-loader图片__

   1. 安装

      ```shell
      npm install --save-dev ...
      ```

   2. 配置webpack.config.js

      1. 当图片尺寸小于limit，页面查看图片源时，是以base64字符串编码；大于limit时，需要安装file-loader，但无需此处显式写出。
      2. 配置publicPath，使得在开发时的引用路径，在发布时均在前面添加此路径前缀，而后一旦将index.html一同发布至dist文件夹，则此无需此配置。
      3. 配置name，即文件命名规则。

      ```shell
      output: {
        publicPath: 'dist'
      },
      modules: {
        rules: [
          {
            test: /\.(png|jpg|gif|jpeg)/,
            use: [
              {
                loader: 'url-loader',
                options: {
                  limit: 4096,
                  name: 'img/[name].[hash:8].[ext]'
                }
              }
            ]
          }
        ]
      }
      ```

   __ES6转ES5的babel__

   1. 安装

      ```shell
      npm install --save-dev babel-loader@7 babel-core babel-preset-es2015
      ```

   2. 配置webpack.config.js

      ```js
      modules: {
        rules: [
          {
            babel...
          }
        ]
      }
      ```

   __配置vue__

   1. 安装

      ```shell
      # --save-dev为开发时，--save为运行时
      npm install --save vue
      ```

   2. 配置webpack.config.js

      - runtime-only：使用此版本vue，代码中不允许包含任何template。
      - runtime-compiler：此版本为编译版，可以含有template。

      为此，此处指定使用runtime-compiler版本构建。

      ```js
      modules: {...},
      resolve: {
        alias: {
          "vue$": "vue/dist/vue.esm.js"
        }
      }
      ```

   3. 引用

      ```js
      // 直接引用，无需路径，打包时webpack会自动在node_modules的repo中查找并引入
      import vue from vue;
      const app = new Vue({
          el: '#app',
          ...
      });
      ```

6. 插件

   __添加文件头信息插件__

   1. 配置webpack.config.js

      ```js
   plugins: [
        new webpack.BannerPlugin('最终版权归xxx所有');
      ]
      ```
   
   
__打包html插件（将index.html一同打包进dist目录）__
   
1. 安装
   
   ```shell
      npm install html-webpack-plugin --save-dev
      ```
   
2. 配置模板
   
   index.html
   
3. 配置webpack.config.js
   
   ```js
      const HtmlWebpackPlugin = require('html-webpack-plugin');
   ...
      plugins: [
          new HtmlWebpackPlugin({
              template: 'index.html'
          });
      ]
      ```
   
   __js压缩插件__
   
1. 安装
   
   ```shell
      npm install uglifyjs-webpack-plugin@1.1.1 --save-dev
   ```
   
   2. 配置webpack.config.js
   
   ```js
      const UglifyjsPlugin = require('uglifyjs-webpack-plugin');
   ...
      plugins: [
          new UglifyjsPlugin();
      ]
      ```
   
5. 搭建本地服务器

   1. 安装

      ```shell
      npm install --save-dev webpack-dev-server@2.9.1
      ```

   2. 配置webpack.config.js

      ```js
      plugins: [...],
      devServer: {
          contentBase: './dist',
          inline: true // 实时监听
      }
      ```

   3. 配置package.json

      --open：运行后自动浏览器打开
      
      ```json
      {
          "scripts": {
              "dev": "webpack-dev-server --open"   
          }
      }
      ```

8. 针对开发时/生产时配置分离

   1. 安装

      ```shell
      npm install webpack-merge --save-dev
      ```

   2. 拆分配置文件

      共有配置文件：base.js

      生产配置文件：prod.js

      开发配置文件：dev.js

   3. 配置prod.js、配置dev.js

      ```js
      const WebpackMerge = require('webpack-merge');
      const baseConfig = require('./base');
      
      module.exports = WebpackMerge(baseConfig, {
          // prod文件原有特有配置
      });
      ```

   4. 配置package.json编译选项

      ```json
      {
          "scripts": {
              "build": "webpack --config ./build/prod.js",
              "dev": "webpack-dev-server --open --config ./build/dev.js"   
          }
      }
      ```

## Vue Cli（脚手架Command-Line Interface）

1. 安装

   ```shell
   # 3.x版本
   npm install -g @vue/cli
   
   # 拉取2.x版本模板
   npm install @vue/cli-init -g
   ```

2. 初始化

   ```shell
   # 2.x版本初始化，
   vue init webpack ./MyProjectName
   
   
   ```


## ES-lint

