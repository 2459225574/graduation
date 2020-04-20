package com.cc.project.handware.api;

import com.cc.project.handware.config.OrderSet;
import com.cc.project.handware.entity.SocketConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author chenchao
 * @Date 20:15 2020/3/16
 * @Description
 * @Param
 * @return
 **/
@Component
public class ActionUtil {
    private static SocketList socketList;

    @Autowired
    public void setSocketList(SocketList socketList) {
        this.socketList = socketList;
    }

    public static String move(String eid,Integer action){
        SocketConnection socketConnection = socketList.getSocketByEid(eid);
        if (socketConnection == null){
            return "操作的设备未连接或者未注册";
        }
        try {
            if (action == 2)
                socketConnection.writeByte(OrderSet.MOVE_FORWARD);
            else if(action == 3){
                socketConnection.writeByte(OrderSet.MOVE_BACK);
            } else if (action == 4) {
                socketConnection.writeByte(OrderSet.MOVE_LEFT);
            }else if (action == 5){
                socketConnection.writeByte(OrderSet.MOVE_RIGHT);
            }else {
                return "操作不存在";
            }
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            socketList.removeSocket(socketConnection);
            return "发送指令失败，已断开连接";
        }
    }
}
