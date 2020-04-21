package com.cc.project.system.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cc.common.constant.UserConstants;
import com.cc.common.utils.SecurityUtils;
import com.cc.common.utils.ServletUtils;
import com.cc.common.utils.StringUtils;
import com.cc.common.utils.poi.ExcelUtil;
import com.cc.framework.aspectj.lang.annotation.Log;
import com.cc.framework.aspectj.lang.enums.BusinessType;
import com.cc.framework.security.LoginUser;
import com.cc.framework.security.service.TokenService;
import com.cc.framework.web.controller.BaseController;
import com.cc.framework.web.domain.AjaxResult;
import com.cc.framework.web.page.TableDataInfo;
import com.cc.project.system.domain.SysUser;
import com.cc.project.system.service.ISysRoleService;
import com.cc.project.system.service.ISysUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息
 * 
 * @author cc
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private TokenService tokenService;

    /**
     * 新增用户
     */
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user)
    {
        System.out.println("**********************");
        System.out.println(user);
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName())))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        return toAjax(userService.insertUser(user));
    }
    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysUser user)
    {
        startPage();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        user.setUserId(loginUser.getUser().getUserId());
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 获取设备组用户列表
     */
    @GetMapping("/listRoleUser")
    public TableDataInfo listRoleUser(SysUser user,long roleId)
    {
        startPage();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        user.setUserId(loginUser.getUser().getUserId());
        List<SysUser> list = userService.listRoleUser(user,roleId);
        return getDataTable(list);
    }

    /**
     * 获取陌生好友列表
     */
    @GetMapping("/listFriends")
    public TableDataInfo listFriends(SysUser user)
    {
        startPage();
        List<SysUser> list = userService.selectlistFriends(user);
        return getDataTable(list);
    }

    /**
     * 添加陌生好友列表
     */
    @RequestMapping("/addFriends")
    public AjaxResult addFriends(Long[] userIds)
    {
        if (userIds.length <= 0){
            return AjaxResult.error("添加好友数量为0");
        }

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return toAjax((int) userService.addFriends(loginUser.getUser().getUserId(),userIds));
    }

    /**
     * 删除好友
     */
    @RequestMapping("/delFriends")
    public AjaxResult delFriends(Long[] ids)
    {
        if (ids == null){
            return AjaxResult.error("参数错误");
        }
        if (ids.length <= 0){
            return AjaxResult.error("删除好友数量为0");
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return toAjax(userService.delFriends(loginUser.getUser().getUserId(),ids));
    }



















































    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/export")
    public AjaxResult export(SysUser user)
    {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "用户数据");
    }

//    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
//    @PreAuthorize("@ss.hasPermi('system:user:import')")
//    @PostMapping("/importData")
//    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
//    {
//        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
//        List<SysUser> userList = util.importExcel(file.getInputStream());
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        String operName = loginUser.getUsername();
//        String message = userService.importUser(userList, updateSupport, operName);
//        return AjaxResult.success(message);
//    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = { "/", "/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId)
    {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("roles", roleService.selectRoleAll());
        if (StringUtils.isNotNull(userId))
        {
            ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return ajax;
    }



    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user)
    {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
//    @PreAuthorize("@ss.hasPermi('system:user:remove')")
//    @Log(title = "用户管理", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{userIds}")
//    public AjaxResult remove(@PathVariable Long[] userIds)
//    {
//        return toAjax(userService.deleteUserByIds(userIds));
//    }

    /**
     * 重置密码
     */
//    @PreAuthorize("@ss.hasPermi('system:user:edit')")
//    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
//    @PutMapping("/resetPwd")
//    public AjaxResult resetPwd(@RequestBody SysUser user)
//    {
//        userService.checkUserAllowed(user);
//        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
//        user.setUpdateBy(SecurityUtils.getUsername());
//        return toAjax(userService.resetPwd(user));
//    }

    /**
     * 状态修改
     */
//    @PreAuthorize("@ss.hasPermi('system:user:edit')")
//    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
//    @PutMapping("/changeStatus")
//    public AjaxResult changeStatus(@RequestBody SysUser user)
//    {
//        userService.checkUserAllowed(user);
//        user.setUpdateBy(SecurityUtils.getUsername());
//        return toAjax(userService.updateUserStatus(user));
//    }
}