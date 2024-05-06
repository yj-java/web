package org.yj.web.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yj.web.common.Result;
import org.yj.web.common.StringRedisUtil;

import java.util.ArrayList;

@RequestMapping("/redis")
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisUtil stringRedisUtil;

    /**
     * 添加String类型 key,value
     */
    @RequestMapping("/addStringAndTimeout")
    public Result addStringAndTimeout(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
        return Result.success();
    }

    /**
     * 添加String类型 key,value
     */
    @RequestMapping("/addString")
    public Result addString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return Result.success();
    }


    /**
     * 添加list类型
     */
    @RequestMapping("/addList")
    public Result addRedis(String key, int index, String value) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(value);
        redisTemplate.opsForList().set(key, index, value);
        return Result.success();
    }

    /*
     删除redis key
     */
    @RequestMapping("/delete")
    public Result delete(String key) {
        redisTemplate.delete(key);
        return Result.success();
    }
}
