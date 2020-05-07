package com.git.lv.demo.test;

import com.git.lv.demo.main.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/***
 * @Title
 * @author lv
 * @Date 2020/3/25 19:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class DemoTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void hello() {
        System.out.println("hello world");
        redisTemplate.opsForValue().set("aa","bb");
//        String aa = redisTemplate.opsForValue().get("aa");
//        System.out.println(aa);
    }


    @Test
    public void PipelineTest() {
        //用户id
        String userId = "zhangsan";
        //行为key
        String actionKey = "good";
        //时间范围
        Integer period = 60;
        //次数上限
        Integer maxCount = 5;

        for (int i = 0; i < 20; i++) {
            List<Object> list = redisTemplate.executePipelined(new RedisCallback<Long>() {

                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.openPipeline();
                    String key = String.format("hist:%s:%s", userId, actionKey);
                    long nowTs = System.currentTimeMillis();
                    //添加行为
                    connection.zAdd(key.getBytes(), nowTs, (nowTs + "").getBytes());
                    //清理无用的数据
                    connection.zRemRangeByScore(key.getBytes(), 0, nowTs - period * 1000);
                    //设置key有效时间 对冷数据自动清理
                    connection.expire(key.getBytes(), period + 1);
                    //获取次数
                    Long card = connection.zCard(key.getBytes());
//                List<Object> objects = connection.closePipeline();
                    return card;
                }
            });

            System.out.println(list);
            if((Long)list.get(list.size() - 1) >= maxCount) {
                System.out.println("已经超出最大点赞量");
                break;
            }
        }
    }

}
