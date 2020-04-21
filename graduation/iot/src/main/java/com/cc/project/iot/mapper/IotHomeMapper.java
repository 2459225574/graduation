package com.cc.project.iot.mapper;

import com.cc.project.iot.domain.FuncEt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName IotHomeMapper
 * @description
 * @Author 陈超
 * @Date 2020/3/12 10:35
 * @Version 1.0
 **/
public interface IotHomeMapper {

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
    /*
     * 用户所有可用设备管理详情
     */
    public List<FuncEt> listAllEquipment(FuncEt funcEt);
    /*
     * 户所有可用设备不重复
     */
    public List<FuncEt> listAllEquipmentNoRepeat(FuncEt funcEt);
}
