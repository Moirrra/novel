package com.moirrra.novel.core.common.constant;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-07
 * @Description: 通用常量
 * @Version: 1.0
 */

public class CommonConsts {

    public static final Integer YES = 1;

    public static final Integer NO = 0;

    public static final int NICKNAME_MIN_LENGTH = 2;

    public static final int NICKNAME_MAX_LENGTH = 10;

    public static final int COMMENT_MIN_LENGTH = 2;

    public static final int COMMENT_MAX_LENGTH = 2048;

    public static final int SESSION_ID_LENGTH = 32;

    public enum SexEnum {
        MALE(0, "男"),
        FEMALE(1, "女");

        private Integer code;
        private String desc;

        SexEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
}
