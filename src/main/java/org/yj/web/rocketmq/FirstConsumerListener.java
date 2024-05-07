package org.yj.web.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "consumer_group", topic = RocketMQTopic.FIRST)
@Slf4j
public class FirstConsumerListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("监听到{}消息了，参数为{}", RocketMQTopic.FIRST, message);
        log.info("{}消费完成！！！", RocketMQTopic.FIRST);
    }
}