package io.github.kaixinbaba.client;

import io.github.kaixinbaba.protocol.RESPReader;
import io.github.kaixinbaba.protocol.RESPWriter;
import io.github.kaixinbaba.protocol.SocketRESPReader;
import io.github.kaixinbaba.protocol.SocketRESPWriter;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class SimpleRedisClient implements RedisClient {

    private Socket socket;

    private RESPReader reader;

    private RESPWriter writer;

    public SimpleRedisClient() {
        try {
            this.socket = new Socket("127.0.0.1", 6379);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reader = new SocketRESPReader(socket);
        writer = new SocketRESPWriter(socket);
    }

    public String get(String key) {
        writer.writeArray("get", key);
        String read = (String) reader.read();
        return read;
    }

    public String set(String key, String value) {
        writer.writeArray("set", key, value);
        return (String) reader.read();
    }

    public Integer incr(String key) {
        writer.writeArray("incr", key);
        return (Integer) reader.read();
    }

    public Map<String, String> mget(String... key) {
        List<String> list = new ArrayList<>();
        list.add("mget");
        list.addAll(Arrays.asList(key));
        writer.writeArray(list.toArray(new String[]{}));
        List<String> read = (List<String>) reader.read();
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < key.length; i++) {
            String k = key[i];
            String v = read.get(i);
            result.put(k, v);
        }
        return result;
    }

    public String mset(Map<String, String> map) {
        List<String> list = new ArrayList<>();
        list.add("mset");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            list.add(entry.getKey());
            list.add(entry.getValue());
        }
        writer.writeArray(list.toArray(new String[]{}));
        return (String) reader.read();
    }

    public void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
        }
    }
}
