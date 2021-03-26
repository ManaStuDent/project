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