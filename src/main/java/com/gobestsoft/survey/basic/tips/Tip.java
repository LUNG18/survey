package com.gobestsoft.survey.basic.tips;

import lombok.Data;

/**
 * 返回给前台的提示（最终转化为json形式）
 */
@Data
public class Tip {

    public Tip() {
    }

    public Tip(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private int code;
    private String message;
    private Object data;

}
