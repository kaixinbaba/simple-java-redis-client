package io.github.kaixinbaba.client;

import java.util.Map;

public interface RedisClient {

    String get(String key);

    Object set(String key, String value);

    Integer incr(String key);

    Map<String, String> mget(String... key);

    String mset(Map<String, String> map);

    void close();
}
