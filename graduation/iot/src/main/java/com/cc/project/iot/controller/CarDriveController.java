package com.cc.project.iot.controller;

import com.cc.framework.web.domain.AjaxResult;
import com.cc.project.handware.api.ActionUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author chenchao
 * @Date 19:42 2020/3/16
 * @Description
 * @Param
 * @return
 **/
@RestController
@RequestMapping("/carDrive")
public class CarDriveController {

    @RequestMapping("/operation")
    public AjaxResult move(@RequestParam("action") Integer action,@RequestParam String eId){
        System.out.println(action+"             "+eId);
        String result = ActionUtil.order(eId,action);
        return AjaxResult.success(result);
    }
}
