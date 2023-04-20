package com.xxxx.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AdminRegist对象",description = "")
public class AdminRegistParam {
    @ApiModelProperty(value="用户名",required = true)
    private String username;
    @ApiModelProperty(value="密码",required = true)
    private String password;
    @ApiModelProperty(value="年龄",required = true)
    private Integer age;
}
