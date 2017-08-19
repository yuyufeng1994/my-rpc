# my-rpc  
>自己学习后并且尝试编写的rpc，rpc主要功能：异构分布式项目之间的通信，使消费者只需要知道接口，远程调用方法就像调用本地方法一样。
  
模块说明    
------
#### rpc-core
>这个模块是rpc的核心部分包括网络通信，客户端动态代理，通信数据结构  
#### rpc-service  
>这个模块rpc提供层暴露的接口
####  rpc-provider  
>这个模块是rpc的提供层 
####  rpc-consumer
>这个模块是rpc的消费层 

依赖关系
---
>rpc-consumer、rpc-provider为rpc使用方，所以他们两个都依赖 rpc-core 模块。
因为提供层要为消费层暴露接口，所以提取了公共接口，因此，它们两个都依赖rpc-service


启动调试*HelloWorld*
--
>使用HelloService接口进行调试。首先启动提供层：在rpc-provider模块中运行top.yuyufeng.rpc.ServerApp.Main
然后启动消费层，在rpc-consumer模块中运行top.yuyufeng.rpc.test.ClientApp.Main