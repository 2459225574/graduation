package com.cc.project.handware.api;

import com.cc.project.handware.config.HardwareConfig;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author chenchao
 * @Date 11:48 2020/2/29
 * @Description
 * @Param
 * @return
 **/
@Component
public class SocketAccept {
    public static ServerSocket SERVER_SOCKET = null;
    static {
        try {
            SERVER_SOCKET = new ServerSocket(HardwareConfig.SOCKET_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SocketAccept() throws IOException {
        Thread thread = new Thread(new acceptRunable());
        thread.start();
        System.out.println("TCP服务器启动成功,端口："+HardwareConfig.SOCKET_PORT);
    }

    private class acceptRunable implements Runnable{
        @SneakyThrows
        @Override
        public void run() {
            while (true){
                Socket socket = SERVER_SOCKET.accept();//等待设备连接
                System.out.println("****************************************************"+socket+"连接到服务器****************************************************");
                SocketList.addSocket(socket);//保存到设备列表中
            }
        }
    }
}
