package com.andy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "用户DTO")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", required = true)
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "状态(0:禁用,1:启用)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "角色ID列表")
    private List<Long> roleIds;
}
