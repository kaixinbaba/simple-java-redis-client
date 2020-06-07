package io.github.kaixinbaba;

import java.io.*;
import java.net.Socket;

public class MainTest {

    public static void main(String[] args) {

        String serverName = "127.0.0.1";
        int port = 6379;
        try {
            Socket client = new Socket(serverName, port);
            client.setReuseAddress(true);
            client.setKeepAlive(true);
            client.setTcpNoDelay(true);
            client.setSoLinger(true, 0);
            StringBuilder sb = new StringBuilder();
            sb.append("*1").append("\r\n");
            sb.append("$8").append("\r\n");
            sb.append("flushall").append("\r\n");


            client.getOutputStream().write(sb.toString().getBytes());

            InputStream inputStream = client.getInputStream();
            byte b[] = new byte[inputStream.available()];
            inputStream.read(b);
            String response = new String(b);
            System.out.println(response);

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
