package com.cc.project.iot.service.impl;

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
    @Override
    public List<FuncEt> getFuncEtByUserId(FuncEt funcEt) {
        return iotHomeMapper.getFuncEtByUserId(funcEt);
    }

    /*
     * 角色的设备管理
     */
    @Override
    public List<FuncEt> getRoleEquipmentList(FuncEt funcEt) {
        return iotHomeMapper.getRoleEquipmentList(funcEt);
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


}
