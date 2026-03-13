package com.example.demo.common.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "通用返回结果")
public class CommonResult<T> {

    @Schema(description = "响应码：200成功，其他失败")
    private Integer code;

    @Schema(description = "响应消息")
    private String message;

    @Schema(description = "响应数据")
    private T data;

    public static final int SUCCESS_CODE = 200;
    public static final int ERROR_CODE = 500;

    public static <T> CommonResult<T> success() {
        return success(null);
    }

    public static <T> CommonResult<T> success(T data) {
        return success("操作成功", data);
    }

    public static <T> CommonResult<T> success(String message, T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(SUCCESS_CODE);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> CommonResult<T> error() {
        return error("操作失败");
    }

    public static <T> CommonResult<T> error(String message) {
        return error(ERROR_CODE, message);
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
