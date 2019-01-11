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

#### 服务端启动

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

#### 客户端启动

+ 以默认ip`localhost`默认端口`6379`启动

  ```shell
  $ redis-cli
  ```

+ 指定ip和端口启动

  ```shell
  $ redis-cli -h 127.0.0.1 -p 6380
  ```

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

  功能：无论key或field是否存在，都设置key中的field属性对应的value

+ hsetnx

  语法：hsetnx key field value

  功能：只有key或field不存在时，才设置key中的field属性对应的value

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

+ hgetall

  语法：hgetall key

  功能：获取key对应的所有的key和value

+ hvals

  语法：hvals key

  功能：获取key对应的所有value

+ hkeys

  语法：hkeys key

  功能：获取key对应的所有的field

+ hincrby

  语法：hincrby key field n

  功能：key中的field属性自增n

+ hincrbyfloat

  语法：hincrbyfloat key field f

  功能：key中的field属性自增浮点数f

#### list类型命令

+ rpush

  语法：rpush key v1 v2 ...

  功能：从列表右侧依次添加多个元素

  例：

  ```shell
  127.0.0.1:6380> rpush list2 a b c
  (integer) 3
  127.0.0.1:6380> lrange list2 0 -1
  1) "a"
  2) "b"
  3) "c"
  ```

+ lpush

  语法：lpush key v1 v2 ...

  功能：从列表左侧依次添加多个元素

  例：

  ```shell
  127.0.0.1:6380> lpush list1 a b c
  (integer) 3
  127.0.0.1:6380> lrange list1 0 -1
  1) "c"
  2) "b"
  3) "a"
  ```

+ linsert

  语法：linsert key before/after value newValue

  功能：	在名为key的list集合的指定值value前/后插入新值newValue；

  ​		如果存在多个相同的value，则以从左至右第1个为准；

  ​		如果key或value不存在，则插入失败

  例

  ```shell
  127.0.0.1:6380> lrange list1 0 -1
  1) "a"
  2) "b"
  3) "c"
  4) "c"
  127.0.0.1:6380> linsert list1 after c d
  (integer) 5
  127.0.0.1:6380> lrange list1 0 -1
  1) "a"
  2) "b"
  3) "c"
  4) "d"
  5) "c"
  ```

+ lpop

  语法：lpop key

  功能：从左边弹出(删除并返回)1个元素

  例

  ```shell
  127.0.0.1:6380> lrange list1 0 -1
  1) "a"
  2) "b"
  3) "c"
  4) "d"
  5) "c"
  127.0.0.1:6380> lpop list1
  "a"
  127.0.0.1:6380> lrange list1 0 -1
  1) "b"
  2) "c"
  3) "d"
  4) "c"
  ```

+ blpop

  语法：blpop key1 key2 ... timeout

  功能：	lpop的阻塞版本

  ​		当key1、key2等集合中有不是空集合的集合时，立即返回该集合中的元素

  ​		   当key1、key2等均是空集合时，等待timeout秒，超时返回`nil`

  ​		等待过程中，当key1、key2等集合中任一集合有新元素时，立即返回该集合中的元素

  ​		当timeout=0时，表示一直阻塞

  例：

  ```shell
  127.0.0.1:6380> blpop list2 list3 10
  (nil)
  (10.07s)
  127.0.0.1:6380> blpop list2 list3 20
  1) "list2"
  2) "a"
  (12.90s)
  ```

  说明：当有新元素返回时，返回的第1个元素为集合名称，第2个元素才是真正要返回的元素

+ rpop

  语法：rpop key

  功能：从右边弹出(删除并返回)1个元素

  例

  ```shell
  127.0.0.1:6380> lrange list1 0 -1
  1) "b"
  2) "c"
  3) "d"
  4) "c"
  127.0.0.1:6380> rpop list1
  "c"
  127.0.0.1:6380> lrange list1 0 -1
  1) "b"
  2) "c"
  3) "d"
  ```

+ brpop

  跟`blpop`一个道理

+ lrem

  语法：lrem key count value

  功能：	在名为key的list集合中删除值为value的元素

  ​		count>0时，从左到右，最多删除count个

  ​		count<0时，从右至左，最多删除count个

  ​		count=0时，删除全部

  例：

  ```shell
  127.0.0.1:6380> lrange list1 0 -1
  1) "a"
  2) "b"
  3) "c"
  4) "a"
  5) "b"
  6) "c"
  127.0.0.1:6380> lrem list1 2 a
  (integer) 2
  127.0.0.1:6380> lrange list1 0 -1
  1) "b"
  2) "c"
  3) "b"
  4) "c"
  ```

+ ltrim

  语法：ltrim list1 start end

  功能：截取指定范围内的元素

  例：

  ```shell
  127.0.0.1:6380> lrange list2 0 -1
  1) "a"
  2) "b"
  3) "c"
  4) "d"
  5) "e"
  127.0.0.1:6380> ltrim list2 1 3
  OK
  127.0.0.1:6380> lrange list2 0 -1
  1) "b"
  2) "c"
  3) "d"
  ```

+ lrange

  语法：lrange key start end

  功能：	获取指定范围内元素

  ​		结果包含下标为start和end的元素

  ​		  start代表的元素必须在end代表元素的左侧，否则报`list为空`

  例：

  ```shell
  127.0.0.1:6380> lrange list1 0 -1
  1) "a"
  2) "b"
  3) "c"
  4) "d"
  5) "e"
  127.0.0.1:6380> lrange list1 1 2
  1) "b"
  2) "c"
  127.0.0.1:6380> lrange list1 1 0
  (empty list or set)
  ```

+ lindex

  语法：lindex key index

  功能：获取key集合中下标为index的元素

+ llen

  语法：llen key

  功能：获取list集合长度

+ lset

  语法：lset key index newValue

  功能：设置key集合中下标为index的值为newValue

  例：

  ```shell
  127.0.0.1:6380> lrange list1 0 -1
  1) "a"
  2) "b"
  3) "c"
  4) "d"
  5) "e"
  127.0.0.1:6380> lset list1 0 f
  OK
  127.0.0.1:6380> lrange list1 0 -1
  1) "f"
  2) "b"
  3) "c"
  4) "d"
  5) "e"
  ```

#### set类型命令

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

  语法：smembers key

  功能：查看set集合的值

+ sinter

  语法：sinter key1 key2 ...

  功能：获取多个集合中的交集元素

+ sdiff

  语法：sdiff key1 key2 ...

  功能：获取key1集合中有，其他集合中没有的元素

  例：

  ```shell
  127.0.0.1:6380> smembers set1
  1) "a"
  2) "c"
  3) "b"
  127.0.0.1:6380> smembers set2
  1) "e"
  2) "c"
  3) "d"
  127.0.0.1:6380> sdiff set1 set2
  1) "a"
  2) "b"
  127.0.0.1:6380> sdiff set2 set1
  1) "e"
  2) "d"
  ```

+ sunion

  语法：sunion key1 key2 ...

  功能：获取key1、key2等集合的并集

+ srem

  语法：srem key value

  功能：删除集合中某元素

+ 

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

#### list

+ `key:list`结构

+ list集合中的元素分别有正负2个下标

  长度为n的list，第1个元素的下标为0，同时也是-n；最后1个元素下标为n-1，同时也是-1

  ![image-20190111171551754](assets/image-20190111171551754-7198151.png) 

#### set

+ Redis的Set是string类型的无序集合。集合成员是唯一的，这就意味着集合中不能出现重复的数据。

+ Redis 中 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。