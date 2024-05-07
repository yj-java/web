package org.yj.web.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yj.web.common.Result;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisController
 */
@RequestMapping("/redis")
@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加String类型 key,value
     */
    @RequestMapping("/addString")
    public Result addString(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        return Result.success();
    }

    /**
     * 添加带过期时间的String类型 key,value
     */
    @RequestMapping("/addStringAndTimeout")
    public Result addStringAndTimeout(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        return Result.success();
    }

    /**
     * 添加map类型
     */
    @RequestMapping("/addMap")
    public Result addMap(String key, String hashKey, String value) {
        stringRedisTemplate.opsForHash().put(key, hashKey, value);
        return Result.success();
    }

    /**
     * 删除map类型
     */
    @RequestMapping("/deleteMap")
    public Result deleteMap(String key, String hashKey) {
        stringRedisTemplate.opsForHash().delete(key, hashKey);
        return Result.success();
    }

    /**
     * 查询map类型
     */
    @RequestMapping("/selectMap")
    public Result selectMap(String key, String hashKey) {
        String value = (String) stringRedisTemplate.opsForHash().get(key, hashKey);
        return Result.success(value);
    }

    /**
     * 查询map类型
     */
    @RequestMapping("/selectAllMap")
    public Result selectAllMap(String key) {
        Set<String> hashKeys = (Set) stringRedisTemplate.opsForHash().keys(key);
        return Result.success(hashKeys);
    }

    /**
     * 添加list类型
     */
    @RequestMapping("/addList")
    public Result addList(String key, String value) {
        stringRedisTemplate.opsForList().rightPush(key, value);
        return Result.success();
    }

    /**
     * 删除list类型
     */
    @RequestMapping("/deleteList")
    public Result deleteList(String key, String value) {
        stringRedisTemplate.opsForList().remove(key, 1, value);
        return Result.success();
    }

    /**
     * 修改list类型
     */
    @RequestMapping("/updateList")
    public Result updateList(String key, int index, String value) {
        stringRedisTemplate.opsForList().set(key, index, value);
        return Result.success();
    }

    /**
     * 查询list类型
     */
    @RequestMapping("/selectList")
    public Result selectList(String key, long start, long end) {
        List<String> range = stringRedisTemplate.opsForList().range(key, start, end);
        return Result.success(range);
    }

    /**
     * 添加set类型
     */
    @RequestMapping("/addSet")
    public Result addSet(String key, String value) {
        stringRedisTemplate.opsForSet().add(key, value);
        return Result.success();
    }

    /**
     * 删除set类型
     */
    @RequestMapping("/deleteSet")
    public Result updateSet(String key, String value) {
        stringRedisTemplate.opsForSet().remove(key, value);
        return Result.success();
    }

    /**
     * 查询set类型
     */
    @RequestMapping("/selectSet")
    public Result selectSet(String key) {
        Set<String> range = stringRedisTemplate.opsForSet().members(key);
        return Result.success(range);
    }


    /*
     删除redis key
     */
    @RequestMapping("/delete")
    public Result delete(String key) {
        stringRedisTemplate.delete(key);
        return Result.success();
    }
}
