package com.cc.project.iot.service;

import com.cc.project.iot.domain.FuncEt;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName IotHomeService
 * @description
 * @Author 陈超
 * @Date 2020/3/12 12:55
 * @Version 1.0
 **/
public interface IotHomeService {
    public List<FuncEt> getFuncEtByUserId(long user_id,FuncEt funcEt);
}
