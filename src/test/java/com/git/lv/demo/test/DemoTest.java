package com.git.lv.demo.test;

import com.git.lv.demo.main.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/***
 * @Title
 * @author lv
 * @Date 2020/3/25 19:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class DemoTest {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void hello() {
        System.out.println("hello world");
        stringRedisTemplate.opsForValue().set("aa","bb");
        String aa = stringRedisTemplate.opsForValue().get("aa");
        System.out.println(aa);
    }
}
