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

    public static String order(String eid,Integer action){
        SocketConnection socketConnection = socketList.getSocketByEid(eid);
        if (socketConnection == null){
            return "操作的设备未连接或者未注册";
        }
        try {
            if (action == OrderSet.MOVE_FORWARD)
                socketConnection.writeByte(OrderSet.MOVE_FORWARD);
            else if(action == OrderSet.MOVE_BACK){
                socketConnection.writeByte(OrderSet.MOVE_BACK);
            } else if (action == OrderSet.MOVE_LEFT) {
                socketConnection.writeByte(OrderSet.MOVE_LEFT);
            }else if (action == OrderSet.MOVE_RIGHT){
                socketConnection.writeByte(OrderSet.MOVE_RIGHT);
            }else if (action == OrderSet.OPEN){
                socketConnection.writeByte(OrderSet.OPEN);
            }else if (action == OrderSet.CLOSE){
                socketConnection.writeByte(OrderSet.CLOSE);
            }
            else {
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
