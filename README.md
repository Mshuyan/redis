# redis
## 初识

### redis是什么

+ 开源

+ 基于键值对的存储服务系统

  > redis类似于数据库，以key-value形式存储数据

+ 支持多种数据结构

  > 字符串、hash table、linked list、sets、sorted sets等等

+ 性能高、功能丰富

### 特性

+ 速度快

  + 数据存在内存中
  + C语言实现
  + 单线程

+ 持久化

  > Redis 把所有数据保存在内存中，对数据的更新异步的保存在磁盘中

+ 多种数据结构

  <img src="assets/image-20190108170050175-6938050.png" width="500px" />  

  基于以上数据结构，衍生支持了以下数据结构：

  + BitMaps：位图

    > 基于字符串

  + HyperLogLog：超小内存唯一值计数

    > 基于字符串

  + GEO：地理信息定位

    > 基于sorted sets

+ 支持多种编程语言

+ 功能丰富

  + 发布订阅

  + lua脚本

    > 实现自定义命令

  + 事务

  + pipeline

    > 提高客户端并发效率

+ 简单

+ 主从复制

  <img src="assets/image-20190108171225620-6938745.png" width="300px" />  

+ 高可用、分布式

  + Redis-Sentinel(v2.8)支持高可用
  + Redis-Cluster(v3.0)支持分布式

### 典型使用场景

+ 缓存系统
+ 计数器
+ 消息队列系统
+ 排行榜
+ 社交网络
+ 实时系统

## 安装配置

### 安装

#### linux、Mac

+ 下载

  https://redis.io/download

+ 解压编译

  ```shell
  $ tar xzf redis-5.0.3.tar.gz
  $ cd redis-5.0.3
  $ make
  ```

  > 编译后并未将结果文件拷贝到其他位置，还在这个目录下，所以将这个目录拷贝到合适的位置，并配置环境变量，安装即可完成

+ 启动测试

  + 启动服务

    ```shell
    $ src/redis-server
    ```

  + 启动客户端

    ```shell
    $ redis-cli 
    127.0.0.1:6379> set hello world
    OK
    127.0.0.1:6379> get hello
    "world"
    ```

### 可执行文件说明

+ redis-server：redis服务器
+ redis-cli：redis命令行客户端
+ redis-benchmark：redis性能测试工具
+ redis-check-aof：aof文件修复工具
+ redis-check-rdb：rdb文件检查工具
+ redis-sentinel：sentinel服务器

### 启动方式

+ 简单启动

  直接使用`redis-server`启动

+ 带参数启动

  redis默认端口为：6379

  使用6380启动：redis-server --port 6380

+ 配置文件启动

  + 编写配置文件`*.config`

  + 将配置文件作为第1个启动参数启动

    ```shell
    redis-server config/6380.conf
    ```

  > **redis启动参数及配置文件中的相对路径均是相对于执行启动命令的位置，所以使用相对路径时，需要到指定的目录下执行启动命令** 

### 配置

#### 查看与修改

+ 查看

  在客户端中

  使用`config get *`获取所有配置项

  使用`config get NAME`获取某个配置项，如：`CONFIG GET loglevel`

+ 修改配置

  在客户端中

  使用`config NAME VALUE`修改配置，如`config daemonize yes`

#### 配置项

+ port

  对外端口，默认6379

+ dir

  工作目录

+ logfile

  日志文件名

+ daemonize

  是否以守护进程方式启动，默认no，建议使用yes

  以守护进程启动时：

  + 执行启动命令后，服务将自动后台运行

  + 启动日志将打印到日志文件

## API理解与使用

### 命令

#### 通用命令

+ keys

  功能：查看所有符合条件的key

  语法：

  `keys *`：查看所有key

  `keys 正则`：查看所有符合正则表达式的key

  > 因为keys命令会对所有的key进行扫描，所以会影响性能，不建议在生产环境使用

+ dbsize

  语法：dbsize

  功能：查看一共有多少个key

+ exists

  语法：exists key

  功能：查看key是否存在

+ del

  语法：del key1 key2 ...

  功能：删除多个key

+ expire

  语法：expire key seconds

  功能：设置某个key在多少秒后过期

+ ttl

  语法：ttl key

  功能：查看某个key剩余过期时间

+ persist

  语法：persist key

  功能：取消某个key的过期时间

+ type

  语法：type key

  功能：查看key的类型

#### 字符串类型命令

+ get

  语法：get key

  功能：获取字符串类型的key对应的value

+ incr

  语法：incr key

  功能：key自增1，如果key不存在，自增后key=1

+ decr

  语法：decr key

  功能：key自减1，如果key不存在，自减后key=1

+ incrby

  语法：incrby key n

  功能：key自增n，如果key不存在，自增后key=n

+ decrby 

  语法：decrby key n

  功能：key自减n，如果key不存在，自减后key=-n

+ set

  语法：set key value

  功能：不管key是否存在，都进行设置

+ setnx

  语法：setnx key value

  功能：key不存在时，才进行设置

+ set xx

  语法：set key value xx

  功能：key存在时，才进行设置

+ setex

  语法：setex key seconds value

  功能：相当于如下两条命令

  ```shell
  SET key value
  EXPIRE key seconds
  ```

  设置1个key同时设置过期时间

+ mset

  语法：mset key1 value1 key2 value2 ...

  功能：创建多个键值对

  ```shell
  127.0.0.1:6380> mset key1 value1 key2 value2
  OK
  127.0.0.1:6380> keys *
  1) "key2"
  2) "key1"
  ```

+ mget

  语法：mget key1 key2 ...

  功能：获取多个key

  ```shell
  127.0.0.1:6380> mget key1 key2
  1) "value1"
  2) "value2"
  ```

+ getset key value

  将新值设置到key，并返回旧值

+ append

  语法：append key value

  功能：将值追加到原有字符串之后

  ```shell
  127.0.0.1:6380> get key1
  "haha"
  127.0.0.1:6380> append key1 haah
  (integer) 8
  127.0.0.1:6380> get key1
  "hahahaah"
  ```

+ strlen

  语法：strlen key

  功能：返回字符串长度

  注意：中文占用2个字节

+ incrbyfloat

  语法：incrbyfloat key n

  功能：浮点数key自增n

  ```shell
  127.0.0.1:6380> incrbyfloat key5 3.5
  "3.5"
  127.0.0.1:6380> get key5
  "3.5"
  ```

+ getrange

  语法：getrange key start end

  功能：获取指定下标的值

  第1个字母的下标为0

  ```shell
  127.0.0.1:6380> get key2
  "value2"
  127.0.0.1:6380> getrange key2 2 3
  "lu"
  ```

+ setrange

  语法：setrange key index value

  功能：将key对应的值的某个下标的值替换为新值

  ```shell
  127.0.0.1:6380> get key2
  "value2"
  127.0.0.1:6380> setrange key2 2 L
  (integer) 6
  127.0.0.1:6380> get key2
  "vaLue2"
  ```

#### hash类型命令

+ hset

  语法：hset key field value

  功能：设置key中的field属性对应的value

+ hget

  语法：hget key field

  功能：获取key中的field属性对应的value

+ hdel

  语法：hdel key field

  功能：删除key中的field属性

+ hgetall

  语法：hgetall key

  功能：获取key中所有属性和值

+ hexists

  语法：hexists key field

  功能：查看key或key中的属性是否存在

+ hlen

  语法：hlen key

  功能：查看key中field属性的数量

+ hmget

  语法：hmget key field1 field2 ...

  功能：获取key中多个field属性的值

+ hmset

  语法：hmset key field1 value1 field2 value2 ...

  功能：批量设置key中的属性



+ sadd

  语法：sadd key m1 m2 ...

  功能：将元素添加到名为key的set集合，如果没有则创建，如果集合已存在则将新元素追加到原有集合，如果新元素与旧元素存在重复，则忽略重复的新元素

  ```shell
  127.0.0.1:6380> sadd myset a b c
  (integer) 3
  127.0.0.1:6380> smembers myset
  1) "a"
  2) "c"
  3) "b"
  127.0.0.1:6380> sadd myset c d e
  (integer) 2
  127.0.0.1:6380> smembers myset
  1) "a"
  2) "c"
  3) "d"
  4) "b"
  5) "e"
  ```

+ smembers

  查看set集合的值

  ```shell
  smembers key
  ```

### 数据结构与内部编码

<img src="assets/image-20190109160314122-7020994.png" width="400px" /> 

### 类型

#### 字符串

+ 可以保存哪些值
  + 字符串
  + 数字
  + 二进制
  + json、xml等字符串
+ 使用场景
  + 缓存
  + 计数器
  + 分布式锁
  + ...
+ 相关命令参见[字符串类型命令](#字符串类型命令) 

#### hash

+ 键值结构

  <img src="assets/image-20190109165718308-7024238.png" width="400px" /> 

  hash类型的结构相对于字符串多了1个属性`field`，该结构类似于`javabean`对象，key相当于对象，field相当于对象中属性，value相当于属性值