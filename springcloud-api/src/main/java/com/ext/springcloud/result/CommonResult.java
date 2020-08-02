package com.ext.springcloud.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private Integer code;

    private String message;

    private T date;

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }
}
