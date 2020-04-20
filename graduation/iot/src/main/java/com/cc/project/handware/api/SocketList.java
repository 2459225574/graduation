package com.cc.project.handware.api;


import com.cc.project.handware.config.OrderSet;
import com.cc.project.handware.entity.SocketConnection;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

/**
 * @Author chenchao
 * @Date 14:02 2020/2/29
 * @Description
 * @Param
 * @return
 **/
@Component
public class SocketList {

    private static LinkedList<SocketConnection> linkedList;
    private static LinkedList<SocketConnection> garbageList; //未绑定用户的物理设备，保存socket，防止垃圾回收socket导致设备不断重连
//    private static EquipmentMapper equipmentMapper;
    static {
        linkedList = new LinkedList<>();
        garbageList = new LinkedList<>();
    }

//    @Autowired(required = true)
//    public void setEquipmentMapper(EquipmentMapper equipmentMapper) {
//        SocketList.equipmentMapper = equipmentMapper;
//    }

    public SocketList(){
        Thread thread = new Thread(new MessageRunable());
        thread.start();
    }

    public SocketConnection getSocketByEid(String eid){
        if (eid == null) return null;
        for (SocketConnection socketConnection:linkedList){
            if (eid.equals(socketConnection.getEquipment_id())){
                return socketConnection;
            }
        }
        return null;
    }

    public static void addSocket(Socket socket) throws IOException {
        SocketConnection socketConnection = new SocketConnection(socket);
//        EquipmentAndGroup ue = equipmentMapper.getUserByEquipmentId(socketConnection.getEquipment_id());//查询用户信息
//        if (ue == null){
//            garbageList.add(socketConnection);
//            System.out.println("设备"+socketConnection.getEquipment_id()+"连接到服务器,未绑定家庭组。");
//            return;
//        }
//        socketConnection.setGroup_id(ue.getGroup_id());
        linkedList.add(socketConnection);
        System.out.println("设备"+socketConnection.getEquipment_id()+"连接到服务器,绑定家庭组为："+socketConnection.getGroup_id());
    }

    public void removeSocket(SocketConnection socket){
        if (socket == null) return;
        linkedList.remove(socket);
    }

    public void removeSocket(String eid){
        if (eid == null) return;
        for (SocketConnection socketConnection:linkedList){
            if (eid.equals(socketConnection.getEquipment_id())){
                linkedList.remove(socketConnection);
                return ;
            }
        }
    }

    private class MessageRunable implements Runnable{
        @SneakyThrows
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(5000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (SocketConnection conn:linkedList){
                    if (!conn.getSocket().isConnected()){
                        linkedList.remove(conn);
                        continue;
                    }
                    conn.initUserAndEquipment();
                    System.out.println("设备id："+conn.getEquipment_id());
                }
            }
        }
    }

    public void delay(Long second){
        try {
            Thread.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
