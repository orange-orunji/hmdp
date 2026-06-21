package com.hmdp.service.mq;

import com.hmdp.entity.VoucherOrder;
import com.hmdp.service.impl.VoucherOrderServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Component
public class OrderConsumer {

    @Resource
    private VoucherOrderServiceImpl voucherOrderService;

    /**
     * 订单队列消费者
     * @param voucherOrder
     * @param message
     * @param channel
     * @throws IOException
     */
    @Transactional
    @RabbitListener(
            bindings = @QueueBinding(
                    key= "order.generate",
                    value= @Queue(value = "order.queue",durable = "true"),
                    exchange = @Exchange(value = "order.exchange",type = ExchangeTypes.DIRECT)
            )
    )
    public void orderConsumer(VoucherOrder voucherOrder,
                              Message message,
                              Channel channel) throws IOException {
        long consumerTag = message.getMessageProperties().getDeliveryTag();
        try{
            voucherOrderService.getVoucherOrder(voucherOrder);
        }
        catch (Exception e){
            log.error("订单处理失败，订单ID：{}", voucherOrder.getId(), e);
            channel.basicNack(consumerTag,false,false);
            throw e;
        }
        channel.basicAck(consumerTag,false);
    }

}
