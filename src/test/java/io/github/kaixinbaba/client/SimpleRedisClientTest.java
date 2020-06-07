package io.github.kaixinbaba.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SimpleRedisClientTest {

    private SimpleRedisClient simpleRedisClient;

    @Before
    public void before() {
        simpleRedisClient = new SimpleRedisClient();
    }

    @Test
    public void test() {
        System.out.println("hello world");
    }

    @Test
    public void set() {
        simpleRedisClient.set("xjj", "abc");
    }

    @Test
    public void get() {
        String value = simpleRedisClient.get("xjj");
        System.out.println("获取的值为 " + value);
    }

    @Test
    public void incr() {
        simpleRedisClient.set("xjj", "7");
        Integer xjj = simpleRedisClient.incr("xjj");
        System.out.println(xjj);
    }

    @Test
    public void mget() {
        Map<String, String> mget = simpleRedisClient.mget("a", "b", "c");
        System.out.println(mget);
    }

    @Test
    public void mset() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        String mset = simpleRedisClient.mset(map);
        System.out.println(mset);
    }

    @After
    public void after() {
        if (simpleRedisClient != null) {
            simpleRedisClient.close();
            simpleRedisClient = null;
        }
    }
}
