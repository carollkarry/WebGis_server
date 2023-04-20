package com.xxxx.server.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.http.HttpUtil;
@RestController
@RequestMapping("/yq")
public class MapShowController {
    @ApiOperation(value = "获取中国地图")
    @GetMapping("/epidemic")
    public String epidemic() {
        System.out.println("systemLiiiii"+System.currentTimeMillis());
        return HttpUtil.get("https://c.m.163.com/ug/api/wuhan/app/data/list-total?t=" + System.currentTimeMillis());
    }
}
