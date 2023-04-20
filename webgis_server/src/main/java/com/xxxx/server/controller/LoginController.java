package com.xxxx.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.server.mapper.AdminMapper;
import com.xxxx.server.pojo.*;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * 登录
 */
@Api(tags="LoginController")
@RestController
@Service
public class LoginController {
    @Autowired
    private IAdminService iAdminService;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return iAdminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
    }

    @ApiOperation(value = "获取当前登陆用户信息")
    @GetMapping("/admin/info")

    public Admin getUserInfo(Principal principal)
    {
        if(null==principal)
        {
            return null;
        }
        String username=principal.getName();
        Admin admin=iAdminService.getAdminByUserName(username);
        admin.setPassword(null);
        return admin;
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout()
    {
        return RespBean.success("注销成功！");
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public RespBean register(@RequestBody AdminRegistParam adminRegistParam, HttpServletRequest request){
        Admin admin=new Admin();
        List<Map<String, Object>> maps = adminMapper.selectMaps(new QueryWrapper<Admin>().select("max(id)"));
        int id = Integer.parseInt(maps.get(0).get("max(id)").toString()) + 1;
        admin.setId(id);
        admin.setName(adminRegistParam.getUsername());
        admin.setPassword(passwordEncoder.encode(adminRegistParam.getPassword()));
        admin.setAge(adminRegistParam.getAge());
        //admin.setUserEmail(adminRegistParam.getUserEmail());
        return iAdminService.register(admin,request);
    }
}
