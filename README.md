<img src="https://cdn.ramostear.com/unaboot.png"/>

# 尤娜: 一个基于Spring Boot 2.0 构建的极简Java 博客系统

[![License](https://img.shields.io/badge/license-Apache%20License%202.0-green)](https://www.apache.org/licenses/LICENSE-2.0.html) [![author](https://img.shields.io/badge/author-ramostear-brightgreen)](https://www.ramostear.com) [![author](https://img.shields.io/badge/JDK-1.8-brightgreen)]() [![GitHub language count](https://img.shields.io/github/languages/count/ramostear/una-boot)]() ![GitHub last commit](https://img.shields.io/github/last-commit/ramostear/una-boot)



## 简介

Una [‘尤娜’] 只是一个项目代号，没有特殊含义。尤娜是站在巨人的肩膀上开发完成的博客系统，旨在为程序员提供一个极简的内容创作管理平台，尤娜100%开放源代码，如果您对她感兴趣，Fork她，并尽情的与之玩耍。她才刚刚起步，或许存在诸多不足，但极简是她至始至终的性格，如果您需要一个大型的内容管理平台，那她可能不能胜任您的需求，如果您只需要一个短小精炼的内容管理系统，尤娜将是您的另一个选择。



## 协议

Una 使用[![License](https://img.shields.io/badge/license-Apache%20License%202.0-green)](https://www.apache.org/licenses/LICENSE-2.0.html) 协议开源，您在使用的过程中请尽量遵循开源协议，即便您身处一个了不起的国。



## 快速开始

### 获取最新源代码

使用 git工具获取代码：

```tex
git clone https://github.com/ramostear/una-boot.git
```

或者：

```tex
git clone git@github.com:ramostear/una-boot.git
```



### 导入项目

- SpringToolSuite[Eclipse]导入，选择菜单 File ->  Import ->  Maven ->  Existing Maven Project ,点击 Next 按钮，选中检出的 una-boot文件夹，点击 Finish 按钮，导入成功。
- IntelliJ IDEA导入，菜单 File -> new -> project from existing source... -> 选中pom.xml文件，点击 OK按钮即可导入。
- 创建数据库，您可以直接导入工程下的una-boot-db.sql文件到数据库中创建una-boot-db数据库，也可以自行创建una-boot-db数据库（数据库使用utf-8字符编码）。
- 修改src/main/resources/application-druid.yml文件中的数据库用户名和密码参数[您自己的用户名和密码]。
- 启动项目，运行src/main/java/com/ramostear/unaboot/UnaBootApplication.java文件的main方法。
- 访问后台，尤娜默认端口80，后台访问地址：http://[localhost|ip|127.0.0.1]/admin/login,用户名：Administrator,密码：unabootv587,登录验证成功，将进入仪表板页面。



## 演示

### 在线演示

前端：[https://www.ramostear.com](https://www.ramostear.com)

后端：暂无

## 特点

- 完全开源：基于Apache 2.0协议开源
- 标签化建站：尤娜内置了内容标签和内容函数，可以快速的完成模板的制作
- 多主题：支持多个主题自由切换，快速改变站点风格，而不需重新编译后台代码
- Markdown支持: 内置markdown编辑器
- 文件存储：支持本地存储和CDN存储
- 评论支持：内置了Gitalk评论函数，只需设置相关的Gitalk参数即可拥有评论功能
- Spring Boot: 基于Spring Boot 2.0版本进行构建



## 开发环境

建议您使用下面推荐的环境与尤娜玩耍，以避免版本不一致所带来的困扰

- OS: Windows 7/10,Linux
- IDE: Eclipse，IntelliJ IDEA(推荐)
- DB：MySQL 5.6+
- JDK: JDK8+
- Web Server: Apache Tomcat 8+
- Maven: Maven 3.0+ 

## 技术框架

尤娜所使用的开发框架明细：

| 框架             | 说明                           | 官网                                                         |
| ---------------- | ------------------------------ | ------------------------------------------------------------ |
| Spring Framework | 轻量级(相对而言)的Java开发框架 | [https://spring.io/projects/spring-framework](https://spring.io/projects/spring-framework) |
| Spring Boot      | Java Web开发脚手架             | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
| Apache Shiro     | 安全控制框架                   | [https://shiro.apache.org](https://shiro.apache.org)         |
| Hibernate        | 对象关系映射框架               | [http://hibernate.org](http://hibernate.org)                 |
| Freemarker       | 视图模板引擎                   | [https://freemarker.apache.org](https://freemarker.apache.org) |
| Log4J            | 日志记录组件                   | [https://logging.apache.org](https://logging.apache.org)     |
| Druid            | 数据库链接池                   | [https://druid.apache.org](https://druid.apache.org)         |
| FastJSON         | JSON解析库                     | [FastJson](https://github.com/alibaba/fastjson/wiki)         |
| EhCache          | 基于Java的进程内缓存框架       | [http://www.ehcache.org](http://www.ehcache.org)             |
| pinyin4j         | 中文转拼音的Java库             | [https://sourceforge.net/projects/pinyin4j/](https://sourceforge.net/projects/pinyin4j/) |
| Maven            | 项目构建                       | [https://maven.apache.org](https://maven.apache.org)         |
| lombok           | 代码生成器                     | https://projectlombok.org                                    |



## 工程目录

```tex
una-boot          
├─db
│      una_boot_db.sql                            #数据库脚本信息
│      
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─ramostear
│  │  │          └─unaboot
│  │  │              ├─common                    
│  │  │              │  ├─exception              #异常处理类
│  │  │              │  ├─factory                #CDN处理类
│  │  │              │  │  └─support
│  │  │              │  ├─jdbc 					 #数据源相关		
│  │  │              │  │  └─support
│  │  │              │  └─util                   #工具包
│  │  │              ├─domain                    #实体相关
│  │  │              │  ├─dto
│  │  │              │  │  └─support
│  │  │              │  ├─entity
│  │  │              │  ├─param
│  │  │              │  └─vo
│  │  │              ├─freemarker                #freemarker解析器包
│  │  │              │  ├─parser
│  │  │              │  │  └─abs
│  │  │              │  └─shiro                  #freemarker-shiro标签包
│  │  │              │      ├─abs
│  │  │              │      └─config
│  │  │              ├─repository                #JPA持久化包
│  │  │              │  └─support
│  │  │              ├─service                   #业务相关包 
│  │  │              │  ├─impl
│  │  │              │  └─support
│  │  │              ├─task                      #定时任务包
│  │  │              └─web                       
│  │  │                  ├─admin                 #后台控制器包
│  │  │                  ├─config                #Web相关配置包
│  │  │                  └─interceptor           #Intercepter包          
│  │  └─resources
│  │      ├─ehcache      
│  │      ├─static                               #静态资源
│  │      └─templates                            #后台视图模板
│  │          ├─admin 
│  │          │  ├─category                      #栏目模板
│  │          │  ├─common                        #公共视图模板
│  │          │  ├─link                          #链接模板
│  │          │  ├─post                          #内容模板
│  │          │  ├─setting                       #系统设置模板
│  │          │  ├─tag                           #标签模板
│  │          │  └─theme                         #主题管理模板
│  │          └─auth                             #登录模板
```



# 文档

- 标签及内置函数手册：[https://www.ramostear.com](https://www.ramostear.com)

- 二次开发手册：[https://www.ramostear.com](https://www.ramostear.com)

  

## 软件截图

![](https://cdn.ramostear.com/login.png)



![](https://cdn.ramostear.com/dashboard.png)



![](https://cdn.ramostear.com/general.png)



![](https://cdn.ramostear.com/sql.png)



![](https://cdn.ramostear.com/themes.png)



![](https://cdn.ramostear.com/theme-edit.png)



![](https://cdn.ramostear.com/list.png)



![](https://cdn.ramostear.com/write.png)



![](https://cdn.ramostear.com/category.png)



![](https://cdn.ramostear.com/category-new.png)



## 主题渲染截图

![](https://cdn.ramostear.com/index.png)





![](https://cdn.ramostear.com/projects.png)



![](https://cdn.ramostear.com/guide.png)



![](https://cdn.ramostear.com/search.png)

