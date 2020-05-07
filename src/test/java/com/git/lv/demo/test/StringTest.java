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
 * @Date 2020/3/30 19:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class StringTest {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void nxTest() {

    }

}
