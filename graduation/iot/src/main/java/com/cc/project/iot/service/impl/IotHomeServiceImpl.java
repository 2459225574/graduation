package com.cc.project.iot.service.impl;

import com.cc.project.handware.api.SocketList;
import com.cc.project.iot.domain.FuncEt;
import com.cc.project.iot.mapper.IotHomeMapper;
import com.cc.project.iot.service.IotHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author chenchao
 * @Date 12:55 2020/3/12
 * @Description
 * @Param
 * @return
 **/
@Service
public class IotHomeServiceImpl implements IotHomeService {
    @Autowired
    private IotHomeMapper iotHomeMapper;

    @Autowired
    private SocketList socketList;

    @Override
    public List<FuncEt> getFuncEtByUserId(FuncEt funcEt) {
        List<FuncEt> list = iotHomeMapper.getFuncEtByUserId(funcEt);
        for (FuncEt fe : list){
            fe.setOnline(socketList.EquipmentOnline(fe.getEId()));
        }
        return list;
    }

    /*
     * 角色的设备管理
     */
    @Override
    public List<FuncEt> getRoleEquipmentList(FuncEt funcEt) {
        List<FuncEt> list = iotHomeMapper.getRoleEquipmentList(funcEt);
        for (FuncEt fe : list){
            fe.setOnline(socketList.EquipmentOnline(fe.getEId()));
        }
        return list;
    }

    /*
     * 添加设备到设备组
     */
    @Override
    public int AddRoleEquipment(FuncEt funcEt) {
        return iotHomeMapper.AddRoleEquipment(funcEt);
    }

    /*
     * 把设备移除设备组
     */
    @Override
    public int RemoveRoleEquipment(FuncEt funcEt) {
        return iotHomeMapper.RemoveRoleEquipment(funcEt);
    }
    /*
     * 添加设备
     */
    @Override
    public int addEquipment(FuncEt funcEt) {
        return iotHomeMapper.addEquipment(funcEt);
    }
    /*
     * 修改设备
     */
    @Override
    public int modifyEquipment(FuncEt funcEt) {
        return iotHomeMapper.modifyEquipment(funcEt);
    }
    /*
     * 删除设备
     */
    @Override
    public int deleteEquipment(FuncEt funcEt) {
        return iotHomeMapper.deleteEquipment(funcEt);
    }
    /*
     * 用户所有可用设备管理详情
     */
    @Override
    public List<FuncEt> listAllEquipment(FuncEt funcEt) {
        List<FuncEt> list = iotHomeMapper.listAllEquipment(funcEt);
        for (FuncEt fe : list){
            fe.setOnline(socketList.EquipmentOnline(fe.getEId()));
        }
        return list;
    }
    /*
     * 户所有可用设备不重复
     */
    @Override
    public List<FuncEt> listAllEquipmentNoRepeat(FuncEt funcEt) {
        List<FuncEt> list = iotHomeMapper.listAllEquipmentNoRepeat(funcEt);
        for (FuncEt fe : list){
            fe.setOnline(socketList.EquipmentOnline(fe.getEId()));
        }
        return list;
    }


}
