package com.andy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "用户创建请求")
public class UserCreateDTO {

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别：0-未知，1-男，2-女")
    private Integer gender = 0;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "状态：0-禁用，1-启用")
    private Integer status = 1;

    @Schema(description = "备注")
    private String remark;
}
