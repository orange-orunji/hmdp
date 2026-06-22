### 生产者消息确认回调验证
![确认回调失败](screenshots/producer-confirm-fail.png)
*故意填写错误的交换机名后，控制台打印消息未到达交换机日志，证明 ConfirmCallback 生效。*