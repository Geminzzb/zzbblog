package com.zzb.exception;

import com.zzb.enums.AppHttpCodeEnum;

/**
 * @author: zahngzongbin
 * @date: 2023/3/16 - 16:35
 * @mail: 2218722664@qq.com
 * @info:
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}