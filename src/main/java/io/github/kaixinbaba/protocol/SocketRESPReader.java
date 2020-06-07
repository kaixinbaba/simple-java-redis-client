package io.github.kaixinbaba.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static io.github.kaixinbaba.Constants.*;

public class SocketRESPReader implements RESPReader {

    private Socket socket;

    public SocketRESPReader(Socket socket) {
        this.socket = socket;
    }

    public Object read() {
        InputStream inputStream;
        try {
            inputStream = socket.getInputStream();
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            String resp = new String(b);
            if (resp.startsWith(ARRAY_PRE)) {
                return readArray(resp.substring(1));
            } else if (resp.startsWith(BLOBSTRING_PRE)) {
                return readBlobString(resp.substring(1));
            } else if (resp.startsWith(SIMPLESTRING_PRE)) {
                return readSimpleString(resp.substring(1));
            } else if (resp.startsWith(INTERGER_PRE)) {
                return readInteger(resp.substring(1));
            } else if (resp.startsWith(ERROR_PRE)) {
                return readError(resp.substring(1));
            } else {
                throw new RuntimeException("非法的协议前缀");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> readArray(String excludePrefix) {
        String[] split = excludePrefix.split(END);
        List<String> result = new ArrayList<>(Integer.valueOf(split[0]));
        for (int i = 1; i < split.length; i++) {
            if (!split[i].startsWith(BLOBSTRING_PRE)) {
                result.add(split[i]);
            }
        }
        return result;
    }

    private String readBlobString(String excludePrefix) {
        return excludePrefix.split(END)[1];
    }

    private String readSimpleString(String excludePrefix) {
        return excludePrefix.split(END)[0];
    }

    private Integer readInteger(String excludePrefix) {
        return Integer.valueOf(excludePrefix.split(END)[0]);
    }

    private String readError(String excludePrefix) {
        throw new RuntimeException(excludePrefix.split(END)[0]);
    }
}
