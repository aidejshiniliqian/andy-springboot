package com.andy.common.enums;

import lombok.Getter;

@Getter
public enum SubsystemType {

    PC("pc", "PC端"),
    APP("app", "APP端");

    private final String code;
    private final String desc;

    SubsystemType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SubsystemType getByCode(String code) {
        for (SubsystemType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
