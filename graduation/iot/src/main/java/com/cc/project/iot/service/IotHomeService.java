package com.cc.project.iot.service;

import com.cc.project.iot.domain.FuncEt;

import java.util.List;

/**
 * @ClassName IotHomeService
 * @description
 * @Author 陈超
 * @Date 2020/3/12 12:55
 * @Version 1.0
 **/
public interface IotHomeService {
    /*
     *查询用户的设备列表
     */
    public List<FuncEt> getFuncEtByUserId(FuncEt funcEt);

    /*
     * 角色的设备管理
     */
    public List<FuncEt> getRoleEquipmentList(FuncEt funcEt);

    /*
     * 添加设备到设备组
     */
    public int AddRoleEquipment(FuncEt funcEt);

    /*
     * 把设备移除设备组
     */
    public int RemoveRoleEquipment(FuncEt funcEt);
    /*
     * 添加设备
     */
    public int addEquipment(FuncEt funcEt);
    /*
     * 修改设备
     */
    public int modifyEquipment(FuncEt funcEt);
    /*
     * 删除设备
     */
    public int deleteEquipment(FuncEt funcEt);
}
