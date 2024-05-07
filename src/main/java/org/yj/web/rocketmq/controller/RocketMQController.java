package org.yj.web.rocketmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yj.web.common.Result;
import org.yj.web.rocketmq.RocketMQTopic;

/**
 * RocketMQController
 */
@RestController
@RequestMapping("/rocketmq")
@Slf4j
public class RocketMQController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/sendMessage")
    public Result sendMessage(String topic, String message) {
        log.info("生产者创建消息，topic = {}，message = {}。", topic, message);
        rocketMQTemplate.convertAndSend(topic, message);
        return Result.success();
    }
}
