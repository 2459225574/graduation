package com.cc.project.iot.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cc.common.constant.HttpStatus;
import com.cc.common.utils.ServletUtils;
import com.cc.common.utils.StringUtils;
import com.cc.common.utils.sql.SqlUtil;
import com.cc.framework.security.LoginUser;
import com.cc.framework.security.service.TokenService;
import com.cc.framework.web.domain.AjaxResult;
import com.cc.framework.web.page.PageDomain;
import com.cc.framework.web.page.TableDataInfo;
import com.cc.framework.web.page.TableSupport;
import com.cc.project.iot.domain.FuncEt;
import com.cc.project.iot.service.IotHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author chenchao
 * @Date 12:58 2020/3/12
 * @Description
 * @Param
 * @return
 **/
@RestController
@RequestMapping("/iot/home")
public class IotHomeController {

    @Autowired
    private IotHomeService iotHomeService;

    @Autowired
    private TokenService tokenService;


    /*
     *查询用户的设备列表
     */
    @RequestMapping("/equipmentList")
    public TableDataInfo getEquipment(FuncEt funcEt){
        startPage();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        long user_id = loginUser.getUser().getUserId();
        funcEt.setUserId(user_id);
        List<FuncEt> list = iotHomeService.getFuncEtByUserId(funcEt);
        return getDataTable(list);
    }

    /*
     * 角色的设备管理
     */
    @RequestMapping("/listRoleEquipment")
    public TableDataInfo listRoleEquipment(FuncEt funcEt){
        startPage();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        long user_id = loginUser.getUser().getUserId();
        funcEt.setUserId(user_id);
        List<FuncEt> list = iotHomeService.getRoleEquipmentList(funcEt);
        return getDataTable(list);
    }

    /*
     * 添加设备到设备组
     */
    @RequestMapping("/AddRoleEquipment")
    public AjaxResult AddRoleEquipment(FuncEt funcEt){

        return toAjax(iotHomeService.AddRoleEquipment(funcEt));
    }

    /*
     * 添加设备
     */
    @RequestMapping("/addEquipment")
    public AjaxResult addEquipment(FuncEt funcEt){
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        long user_id = loginUser.getUser().getUserId();
        funcEt.setUserId(user_id);
        return toAjax(iotHomeService.addEquipment(funcEt));
    }

    /*
     * 修改设备
     */
    @RequestMapping("/modifyEquipment")
    public AjaxResult modifyEquipment(FuncEt funcEt){
        return toAjax(iotHomeService.modifyEquipment(funcEt));
    }

    /*
     * 删除设备
     */
    @RequestMapping("/deleteEquipment")
    public AjaxResult deleteEquipment(FuncEt funcEt){
        return toAjax(iotHomeService.deleteEquipment(funcEt));
    }

    /*
     * 把设备移除设备组
     */
    @RequestMapping("/RemoveRoleEquipment")
    public AjaxResult RemoveRoleEquipment(FuncEt funcEt){
        return toAjax(iotHomeService.RemoveRoleEquipment(funcEt));
    }




    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
