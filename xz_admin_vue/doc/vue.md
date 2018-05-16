项目环境搭建
---

 - 安装 nodeJs

    
        从nodejs中文网（http://nodejs.cn/download/）下载msi格式的安装包直接运行，

        如果是win10系统，则需要以管理员身份运行cmd 
        
        执行msiexec /package +‘msi文件路径’（输入的时候注意半角字符且路径不能为中文名）

        安装完成之后，在命令行输入 node -v 回车，输入 npm -v 回车，如果出现对应nodejs版本和npm版本则安装成功

        由于npm网速太慢，可以安装npm淘宝镜像

        临时使用：cmd执行npm --registry https://registry.npm.taobao.org install express

        持久使用：cmd执行npm config set registry https://registry.npm.taobao.org

 - 安装项目依赖
    
        在cmd中进入到项目目录，运行npm i进行安装依赖。

        出现added xxx packages in xxx.xxx s 则表示依赖安装成功，如果安装失败可以重复运行npm i

 - 运行项目

        在cmd中进入到项目目录，运行npm run dev 等待若干秒，出现Your application is running here: http://localhost:8585 则表示项目成功运行，在浏览器打开http://localhost:8585即可浏览项目

 - 部署项目

        在cmd中进入到项目目录，运行npm run build 等待若干秒，出现
        Build complete.Tip: 
        built files are meant to be served over an HTTP server.
        Opening index.html over file:// won't work.
        则表示项目打包完成，打包完成之后在项目文件夹会多出一个dist目录，将目录下所有文件上传到服务器即可