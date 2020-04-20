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
    public List<FuncEt> getFuncEtByUserId(long user_id,FuncEt funcEt) {
        System.out.println(funcEt);

        return iotHomeMapper.getFuncEtByUserId(user_id,funcEt);
    }
}
