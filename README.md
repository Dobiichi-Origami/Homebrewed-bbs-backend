# 自制的乞丐版论坛后端

## 项目结构

​	该项目由Dao层，Entity层，Service层和Controller层组成；Dao，Entity与Service层功能不再赘述。Controller层封装了供外部访问的API。目前唯一实现的控制器，事件控制器AdvisorController内实现了对象的Crud API，用以实现论坛的基本功能如登录验证，发帖，修改信息等。

​	只要配置好Application.yml就可以使用

## 更新计划

​	本项目其实是自己Native IOS App项目的配套工程，会增加什么功能取决于前端的实现进度，后期会完善注释和代码规范等方面，不定期更新。



# 更新日志

## 2020年11月25日更新

修复了发帖和回复无法关联到对应实体的严重Bug，增加了对发帖回复请求的Json格式支持