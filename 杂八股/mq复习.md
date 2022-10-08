# Content

* [Content](#content)
* [structure](#structure)
* [简单模式](#简单模式)
* [work queues模式](#work-queues模式)
* [Pub/Sub订阅模式](#pubsub订阅模式)
* [路由direct模式](#路由direct模式)
* [消息可靠性](#消息可靠性)

# structure

![](/static/2022-10-08-11-45-01.png)

# 简单模式

简单模式下不需要走switch（即，走默认交换机`“”`, 直接创建队列发送消息给Q

![](/static/2022-10-08-12-08-46.png)

# work queues模式

![](/static/2022-10-08-12-09-47.png)
![](/static/2022-10-08-12-11-49.png)

* 提高任务处理速度

# Pub/Sub订阅模式

消息消费1toN

走switch

![](/static/2022-10-08-12-12-48.png)

# 路由direct模式

![](/static/2022-10-08-12-24-49.png)

# 消息可靠性

