### 现阶段工作
在com.augus.jdbc目录中，使用jdbc和sqlyog对mysql优化进行了性能测试。

1. covering：添加覆盖索引的优化，避免回表，基本在10倍左右
2. orderby：添加索引优化，避免排序，优化1000倍左右
3. pages：添加索引优化，对sql语句进行优化，分页性能优化几十倍
4. 减少锁的持有时间：分批删除、事务中先查询后更新，并行性能会提升。

当前只是单线程测试，后续会进行多线程的接口测试。
### MySQL优化问题
#### 主要分为

1. 添加覆盖索引
2. 优化order by
3. 优化分页 offset x limit y
4. 减少锁持有时间

#### 我们要注意SQL优化的目的：

1. 减少磁盘IO：尽量避免全表扫描、尽量使用索引、尽量使用覆盖索引避免回表。   
2. 减少内存、CPU消耗：尽可能减少排序、分组、去重之类的操作、尽量减少事务持有锁的时间。

### 修改慢查询配置的过程
#### 先要查看my.ini的路径存放的位置
```java
SHOW VARIABLES LIKE 'pid_file';
```
![image.png](https://cdn.nlark.com/yuque/0/2024/png/28334026/1722765312770-1f016b54-db2e-40f0-a0f5-ec85c3cb6c80.png#averageHue=%23b3be97&clientId=u57275da1-d7d2-4&from=paste&height=38&id=u31f001e0&originHeight=38&originWidth=518&originalType=binary&ratio=1&rotation=0&showTitle=false&size=3923&status=done&style=none&taskId=u5420b299-0e66-4666-b2b6-3238c45f34e&title=&width=518)
![image.png](https://cdn.nlark.com/yuque/0/2024/png/28334026/1722765383285-72e1ca08-d711-4ab9-912d-4e390356b825.png#averageHue=%23f8f8f7&clientId=u57275da1-d7d2-4&from=paste&height=244&id=u751de440&originHeight=244&originWidth=661&originalType=binary&ratio=1&rotation=0&showTitle=false&size=19598&status=done&style=none&taskId=uf4f60bbf-d380-4ba4-bbc6-b77cf865d73&title=&width=661)
#### 在my.ini的文件中修改慢查询日志配置
并且在C:\ProgramData\MySQL\MySQL Server 8.1\Data\目录下创建my_slow_query.log
```java
# 开启慢查询日志的开关
slow_query_log=ON 
# 设置慢查询日志文件路径
slow_query_log_file=C:\ProgramData\MySQL\MySQL Server 8.1\Data\my_slow_query.log
# 设置慢查询时间阈值为2秒
long_query_time=2
```

#### 重启mysql
```java
net stop mysql
net start mysql
```

#### 查询慢查询配置是否修改成功
```java
SHOW VARIABLES LIKE '%low_query_log%';
```
![image.png](https://cdn.nlark.com/yuque/0/2024/png/28334026/1722765615549-008460e6-d4f8-4899-968b-f53ed8b60512.png#averageHue=%23dedfaf&clientId=u57275da1-d7d2-4&from=paste&height=54&id=u5a8e08e6&originHeight=54&originWidth=618&originalType=binary&ratio=1&rotation=0&showTitle=false&size=5381&status=done&style=none&taskId=u28e6908f-1b8a-4e73-a683-ef03c7b29c7&title=&width=618)
```java
SHOW VARIABLES LIKE '%long_query_time%';
```
![image.png](https://cdn.nlark.com/yuque/0/2024/png/28334026/1722765640525-b446f042-50a6-47e9-b40b-ce0967d66add.png#averageHue=%23bce5da&clientId=u57275da1-d7d2-4&from=paste&height=36&id=u68573a9d&originHeight=36&originWidth=226&originalType=binary&ratio=1&rotation=0&showTitle=false&size=2762&status=done&style=none&taskId=u507a1a1b-c8f2-47f2-8edb-a4097fbef57&title=&width=226)
 
### 如何发现慢SQL语句
#### 查看慢查询日志
通过SHOW VARIABLES LIKE '%low_query_log%';查询到慢查询日志文件位置。

#### explain分析慢SQL的原因
```java
explain sql语句;
```


### explain后各个参数的作用
##### type
const：结果只有一条的主键或唯一索引扫描
eq_ref：唯一索引扫描
ref：非唯一索引扫描
range：索引范围扫描，一般在where中采取范围查询
index：全索引扫描
All：全表扫描

##### key
key表示实际使用的索引

##### rows
表明SQL返回请求数据的行数

##### extra
using filesort：表示使用了排序
using temporary：表示使用了临时表



