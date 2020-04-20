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

    public List<FuncEt> getFuncEtByUserId(@Param("user_id") long user_id,@Param("funcEt") FuncEt funcEt);
}
