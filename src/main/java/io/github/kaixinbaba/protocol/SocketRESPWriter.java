package io.github.kaixinbaba.protocol;

import java.io.IOException;
import java.net.Socket;

import static io.github.kaixinbaba.Constants.*;

public class SocketRESPWriter implements RESPWriter {


    private Socket socket;

    public SocketRESPWriter(Socket socket) {
        this.socket = socket;
    }

    public void writeArray(String... array) {
        StringBuilder sb = new StringBuilder();
        sb.append(ARRAY_PRE).append(array.length).append(END);
        for (String s : array) {
            sb.append(BLOBSTRING_PRE).append(s.length()).append(END);
            sb.append(s).append(END);
        }
        sb.append(END);
        try {
            socket.getOutputStream().write(sb.toString().getBytes());
            socket.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
