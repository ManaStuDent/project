# project

## 框架
- SpringBoot
- MyBatis、pagehelper
- Redis
- RabbitMQ
- SpringSecurity
- Caffeine
- Jackson
- Hutool
- EasyExcel


若控制台提示：An illegal reflective access operation has occurred
则在 JVM options 添加下面的参数： --illegal-access=deny --add-opens java.base/java.lang=ALL-UNNAMED


## 部署
项目使用插件对 SpringBoot 项目瘦身，依赖的 jar 都存放在 lib 文件夹下，部署项目如果没有新的依赖则可以只部署项目中的几个 jar 包即可

注意：
- 1.项目有新的依赖添加时，需要上传新的 jar 包到服务器 
- 2.core、dao 的 jar 也在 lib 中，发布时也需要替换
- 3.项目启动方式 java -jar -Dloader.path=lib project-admin-api-0.0.1-SNAPSHOT.jar


## 配置
集成了 nacos 配置文件配置于 nacos 中，配置的值可以看 application-dev.properties.bak 文件中的内容