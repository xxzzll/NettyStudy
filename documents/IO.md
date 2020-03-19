# IO 分析
## BIO（Blocking IO）
-   模型
    ![](./images/BIO原理.png)
    
## NIO(New Non-Blocking IO)
-   single-Thread Model
   ![](./images/NIO-single-Thread模型.png) 
   -    代码实现
   ![](./images/NIO-single-Thread编码实现.png)
-    reactor Mode
   ![](./images/NIO-reactor模型.png)   
   -    代码实现
   ![](./images/NIO-reactor编码实现.png)  
   
-   三大核心组件
    -   Selector
    -   Channel   
    -   Buffer
    
-   参考地址：https://www.cnblogs.com/snailclimb/p/9086334.html   
   
## AIO(Asynchrous Non-Blocking IO)    
-   模型
    ![](./images/AIO模型.png)  
-   代码实现
    ![](./images/AIO异步非阻塞编码实现.png)     
    
    
## 区分：同步/异步、阻塞/非阻塞
![](./images/同步异步阻塞非阻塞的区分.png)           
