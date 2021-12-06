#redis
    基本使用类型(7种)
    string key-value(最大的容量都是521M)
      核心命令:
        set
        get
        incr
        decr
        bitSet
      编码方法:字符串默认是raw,超过44个字节就是emStr,计算之后就是raw ,数字类型是 int
      使用场景
        1,缓存
        2,分布式锁
        3,分布式全局ID
        6,限流
        7,位运算
    
    map
        Hget
        Hset
      存储结构:ziplist ,hashtable
    set
        sadd key :先set中加入原素
        smembers key :是否存在key
        scard key : value 的数量`
        srandmember key : 随机获取一个key
        spop key :弹出一个元素
        srem key , value1,value2 :删除某个元素
        sismember key value : 是否某个元素
        sdiff  :取2个集合的差集    
        sinter :交集
        sunion :并集
      存储结构
         1.intset 2,hashtab 里面的key
      使用
         点赞,打卡,签到,抽奖
    zset
        zadd key 20 value:添加到可以
        zrange key 0 ,-1 :所有的元素
        zrevrange key    :倒叙取出元素
        zrangebyscore :按照分数范围取元素
        zrem 删除
        zcard 个数
        zincrby 增加分数
        zcount key 20 60 :分值范围内的value
        zrank key value :获取value 的排名
        zscore key value:获取value 的得分
      存储结构 ziplist,skipList+字典
    
    list
       存储结构:quicklist
    hyperLoglogs
      用来座=作为基数统计
    geospatial 
      地理位置的经纬度
    streams 
      发布订阅
# reids实物
      1.multi 开启事务
      2.exec 执行事务
      3.discard 取消事务
      4.watch 乐观锁机制:
     rdis事务无法保证原子性:
      1.他只能回滚编译是的异常,无法回滚运行时异常
     Lua脚本(实现了多个命令的原子性):如果脚本执行的时间超时;可以设置超时时间;也可以杀死
 # redis 为什么这么快
    1.简单的数据结构
    2.单线程
    3.Io多路复用
# 内存回收
    1.定时淘汰:设置了TTL
    2.惰性淘汰
    3.定期淘汰
    LRU :最近最少使用:全局时钟的时间(194),所有key的时间和全局时钟作比较
    Lfu :访问频率 :通过递增和递减的基数来实现
# redis持久化的机制
    RDB :数据量小,性能好 
    dump.rdb
    AOF : 最多就是1s的数据
    可以同时开启
# redis 分布式
### 主从备份
    全量复制:基于RDB文件
    增量复制:基于偏移量,主节点和从节点都会保留一份偏移量
    slave of ip 端口 
### 哨兵模式
    不足:数据丢失
    Ratf算法 :先到先得,少数服从多数
    多主多从:
    分区(codies,cluster):通过key找到对应的槽----虚拟槽(16383)---通过槽找到对应的负责的redis实例
### redis 客户端实现
    工作模式:单节点,哨兵,集群,分片 
    请求模式:client ,pipeline,事务
    redisTemplete基于JEDIS,Lettuce
    jedis如何获取哨兵模式下的可操作的实例:通过本地的缓存实例,来映射槽点负责的实例
### 主从一致问题
    1.延迟双删
    2.监听binglog采用mq异步执行redis缓存同步
### 热点数据发现
    1.监控
### 缓存雪崩
    大量的缓存过期
    解决方式:随机的设置过期时间,预热数据,用不过期
### 缓存穿透(解决方式:布隆过滤器)
    现象:数据不存在被持续的访问
### 缓存击穿
   现象就是一个热点数据的缓存失效导致,mysql数据库的压力激增
   解决方式:通过redis分布式锁锁住对应的操作,生成缓存后再释放锁

