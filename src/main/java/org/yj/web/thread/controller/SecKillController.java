package org.yj.web.thread.controller;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secKill")
public class SecKillController {
    @Autowired
    private RedissonClient redissonClient;

    public void test() {
    }
}
