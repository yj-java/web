package org.yj.web.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/redisTest")
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/test")
    public void redisTest() {
        // 存储验证码
        stringRedisTemplate.opsForValue().set("String类型：", "String类型的value");

        // 实现关注列表
        stringRedisTemplate.opsForList().rightPush("list类型的数据：","第一个值");
        stringRedisTemplate.opsForList().rightPush("list类型的数据：","第二个值");
        stringRedisTemplate.opsForList().rightPush("list类型的数据：","第三个值");
        stringRedisTemplate.opsForList().set("list类型的数据：", 0, "第一个值");
        stringRedisTemplate.opsForList().set("list类型的数据：", 2, "第三个值");
        stringRedisTemplate.opsForList().set("list类型的数据：", 1, "第二个值");
        List<String> range = stringRedisTemplate.opsForList().range("list类型的数据：", 0, -1);

        //
        stringRedisTemplate.opsForSet().add("set类型的数据：", "值1", "值2", "值3");
        Set<String> members = stringRedisTemplate.opsForSet().members("set类型的数据：");

        // 实现积分排行榜
        stringRedisTemplate.opsForZSet().add("2024年双十一活动积分排行榜：", "张三", 4998);
        stringRedisTemplate.opsForZSet().add("2024年双十一活动积分排行榜：", "李四", 5998);
        stringRedisTemplate.opsForZSet().add("2024年双十一活动积分排行榜：", "王二麻子", 3998);
        stringRedisTemplate.opsForZSet().incrementScore("2024年双十一活动积分排行榜：", "张三", 1);
        Set<String> range1 = stringRedisTemplate.opsForZSet().range("2024年双十一活动积分排行榜：", 0, -1);

        // 实现点赞评论分享计数
        stringRedisTemplate.opsForHash().put("文章id","点赞数：","100");
        stringRedisTemplate.opsForHash().put("文章id","评论数：","200");
        stringRedisTemplate.opsForHash().put("文章id","分享数：","300");
        stringRedisTemplate.opsForHash().increment("文章id","点赞数：",899);
        Object o = stringRedisTemplate.opsForHash().get("文章id", "点赞数：");

    }
}
