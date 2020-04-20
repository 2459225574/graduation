package com.cc.project.handware.entity;

import com.cc.project.handware.config.HardwareConfig;
import com.cc.project.handware.config.OrderSet;
import lombok.Data;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * @Author chenchao
 * @Date 11:47 2020/2/29
 * @Description
 * @Param
 * @return
 **/
@Data
public class SocketConnection {
    private String equipment_id;
    private String group_id;
    private Socket socket;
    private Date ConnectionDate;
    private InputStream is;
    private OutputStream os;
    private BufferedReader br;

    public SocketConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.is = socket.getInputStream();
        this.os = socket.getOutputStream();
        this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.ConnectionDate = new Date(System.currentTimeMillis());
        initUserAndEquipment();
    }

    public void initUserAndEquipment() throws IOException {
        writeByte(OrderSet.GET_ID);
        this.equipment_id = readString();
    }

    public String readLen(Integer len) throws IOException {
        if (this.is == null) return null;
        delay(HardwareConfig.DELAY_TIME);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++){
            sb.append(this.is.read());
        }
        return sb.toString();
    }

    public String readString() throws IOException {
        if (this.is == null) return null;
        delay(HardwareConfig.DELAY_TIME);
        return this.br.readLine();
    }


    public byte readByte() throws IOException {
        if (this.is == null) return 0;
        return (byte) this.is.read();
    }

    public boolean writeByte(byte b) throws IOException {
        if (this.os == null) return false;
        this.os.write(b);
        this.os.flush();
        return true;
    }

    public void delay(Long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
